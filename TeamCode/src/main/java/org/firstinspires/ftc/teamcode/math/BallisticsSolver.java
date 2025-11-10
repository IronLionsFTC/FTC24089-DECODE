package org.firstinspires.ftc.teamcode.math;

/**
 * Ballistics solver — closed-form approximation with quadratic-drag correction and
 * robot-velocity compensation (single-step, non-iterative).
 *
 * Usage:
 *   LaunchSolution sol = BallisticsSolver.solveLaunch(robotPosition, robotVelocity, targetPosition, launchSpeedInInchesPerSec);
 *
 * All returned angles are in degrees. Azimuth is the horizontal bearing to aim (0 = +X, 90 = +Y).
 */
public class BallisticsSolver {

    public static class LaunchSolution {
        public double azimuthDeg;
        public double lowElevationDeg;
        public boolean lowValid;
        public double highElevationDeg;
        public boolean highValid;

        @Override
        public String toString() {
            return String.format("azimuth=%.3f°, low=%.3f° (valid=%b), high=%.3f° (valid=%b)",
                    azimuthDeg, lowElevationDeg, lowValid, highElevationDeg, highValid);
        }
    }

    // ---- Tunable physical constants (SI units internally) ----
    private static final double INCH_TO_M = 0.0254;
    private static final double M_TO_INCH = 1.0 / INCH_TO_M;
    private static final double G_IN_SI = 9.80665; // m/s^2
    private static final double G_IN_INCH_S2 = 386.09; // in/s^2 (for reference only)

    // Ball properties (approx for a 5" holed plastic ball)
    private static final double BALL_DIAMETER_M = 5.0 * INCH_TO_M; // 5 in -> m
    private static final double BALL_RADIUS_M = BALL_DIAMETER_M / 2.0;
    private static final double BALL_AREA_M2 = Math.PI * BALL_RADIUS_M * BALL_RADIUS_M;
    private static final double BALL_MASS_KG = 0.075; // 75 grams

    // Aerodynamic properties (reasonable defaults; tune empirically if needed)
    private static final double AIR_DENSITY = 1.225; // kg/m^3 at sea level
    private static final double DRAG_COEFF = 0.8; // approximate for holed plastic ball

    // Derived drag constant: k = 0.5 * rho * Cd * A / m  (units 1 / m)
    private static final double DRAG_K = 0.5 * AIR_DENSITY * DRAG_COEFF * BALL_AREA_M2 / BALL_MASS_KG;

    /**
     * Solve for azimuth and both elevation angles (low and high) in degrees.
     *
     * @param robotPosition inches
     * @param robotVelocity inches/sec
     * @param targetPosition inches
     * @param launchSpeedInchesPerSec inches/sec (magnitude relative to robot)
     * @return LaunchSolution with azimuthDeg, lowElevationDeg, highElevationDeg and validity flags
     */
    public static LaunchSolution solveLaunch(Vector3 robotPosition,
                                             Vector3 robotVelocity,
                                             Vector3 targetPosition,
                                             double launchSpeedInchesPerSec) {

        LaunchSolution out = new LaunchSolution();

        double rx = robotPosition.x * INCH_TO_M;
        double ry = robotPosition.y * INCH_TO_M;
        double rz = robotPosition.z * INCH_TO_M;
        double tx = targetPosition.x * INCH_TO_M;
        double ty = targetPosition.y * INCH_TO_M;
        double tz = targetPosition.z * INCH_TO_M;
        double vrx = robotVelocity.x * INCH_TO_M;
        double vry = robotVelocity.y * INCH_TO_M;
        double vrz = robotVelocity.z * INCH_TO_M;
        double vLaunch = launchSpeedInchesPerSec * INCH_TO_M;

        // Vector from robot to target
        double dx = tx - rx;
        double dy = ty - ry;
        double dz = tz - rz;

        double dHoriz = Math.hypot(dx, dy);
        double h = dz;

        double azimuthRad = Math.atan2(dy, dx);
        out.azimuthDeg = Math.toDegrees(azimuthRad);

        double dirX = (dHoriz > 1e-9) ? (dx / dHoriz) : Math.cos(azimuthRad);
        double dirY = (dHoriz > 1e-9) ? (dy / dHoriz) : Math.sin(azimuthRad);
        double robotVelTowardTarget = vrx * dirX + vry * dirY; // m/s
        double vEff = vLaunch - robotVelTowardTarget;
        if (vEff <= 1e-6) {
            out.lowValid = false;
            out.highValid = false;
            out.lowElevationDeg = 0.0;
            out.highElevationDeg = 0.0;
            return out;
        }
        double v = vEff;
        double g = G_IN_SI;

        if (dHoriz < 1e-6) {
            if (h > 0) {
                out.lowElevationDeg = 90.0;
                out.highElevationDeg = 90.0;
                out.lowValid = true;
                out.highValid = true;
            } else {
                // no solutions (I hate Java null so mark with boolean)
                out.lowValid = false;
                out.highValid = false;
            }
            return out;
        }

        // Discriminant for vacuum quadratic
        double v4 = v * v * v * v;
        double disc = v4 - g * (g * dHoriz * dHoriz + 2.0 * h * v * v);

        if (disc < 0) {
            // No solution even ignoring drag
            out.lowValid = false;
            out.highValid = false;
            out.lowElevationDeg = 0.0;
            out.highElevationDeg = 0.0;
            return out;
        }

        double sqrtDisc = Math.sqrt(disc);
        double tanThetaLow = (v * v - sqrtDisc) / (g * dHoriz);
        double tanThetaHigh = (v * v + sqrtDisc) / (g * dHoriz);
        double thetaLowRad_vac = Math.atan(tanThetaLow);
        double thetaHighRad_vac = Math.atan(tanThetaHigh);
        double v0zLow = vrz + v * Math.sin(thetaLowRad_vac);
        double v0zHigh = vrz + v * Math.sin(thetaHighRad_vac);
        double tLow = solvePositiveQuadraticTime(0.5 * g, -v0zLow, h);
        double tHigh = solvePositiveQuadraticTime(0.5 * g, -v0zHigh, h);
        boolean vacLowValid = (tLow > 0 && !Double.isNaN(tLow));
        boolean vacHighValid = (tHigh > 0 && !Double.isNaN(tHigh));
        double fLow = 1.0, fHigh = 1.0;

        if (vacLowValid) {
            double kvt = DRAG_K * v * tLow; // dimensionless
            fLow = 1.0 / (1.0 + kvt);
        }
        if (vacHighValid) {
            double kvt = DRAG_K * v * tHigh;
            fHigh = 1.0 / (1.0 + kvt);
        }

        double dEffLow = dHoriz * fLow;
        double dEffHigh = dHoriz * fHigh;

        // ---------- Recompute angles using drag-corrected horizontal range (single pass) ----------
        // Recompute discriminants with dEffLow/dEffHigh
        double discLow = v * v * v * v - g * (g * dEffLow * dEffLow + 2.0 * h * v * v);
        double discHigh = v * v * v * v - g * (g * dEffHigh * dEffHigh + 2.0 * h * v * v);

        boolean finalLowValid = discLow >= 0.0;
        boolean finalHighValid = discHigh >= 0.0;

        double thetaLowRad = 0.0;
        double thetaHighRad = 0.0;

        if (finalLowValid) {
            double sd = Math.sqrt(Math.max(0.0, discLow));
            double tanTheta = (v * v - sd) / (g * dEffLow);
            thetaLowRad = Math.atan(tanTheta);
        }

        if (finalHighValid) {
            double sd = Math.sqrt(Math.max(0.0, discHigh));
            double tanTheta = (v * v + sd) / (g * dEffHigh);
            thetaHighRad = Math.atan(tanTheta);
        }

        // Final validity: also ensure v_eff>0 and times are positive when using world vertical speed
        boolean lowValid = finalLowValid;
        boolean highValid = finalHighValid;

        if (lowValid) {
            double v0z = vrz + v * Math.sin(thetaLowRad);
            double t = solvePositiveQuadraticTime(0.5 * g, -v0z, h);
            if (!(t > 0)) lowValid = false;
        }
        if (highValid) {
            double v0z = vrz + v * Math.sin(thetaHighRad);
            double t = solvePositiveQuadraticTime(0.5 * g, -v0z, h);
            if (!(t > 0)) highValid = false;
        }

        // Convert to degrees for output. Azimuth already set above.
        out.lowElevationDeg = Math.toDegrees(thetaLowRad);
        out.highElevationDeg = Math.toDegrees(thetaHighRad);
        out.lowValid = lowValid;
        out.highValid = highValid;

        return out;
    }

    /**
     * Solve quadratic a t^2 + b t + c = 0 and return the positive root (if any).
     * Returns -1 if no positive root exists.
     */
    private static double solvePositiveQuadraticTime(double a, double b, double c) {
        // a t^2 + b t + c = 0
        // a should normally be > 0 (0.5*g)
        if (Math.abs(a) < 1e-12) {
            // linear case: b t + c = 0 -> t = -c/b
            if (Math.abs(b) < 1e-12) return -1.0;
            double t = -c / b;
            return (t > 0) ? t : -1.0;
        }
        double disc = b * b - 4.0 * a * c;
        if (disc < 0.0) return -1.0;
        double sqrtD = Math.sqrt(disc);
        double t1 = (-b + sqrtD) / (2.0 * a);
        double t2 = (-b - sqrtD) / (2.0 * a);
        double t = -1.0;
        if (t1 > 0 && t2 > 0) t = Math.min(t1, t2);
        else if (t1 > 0) t = t1;
        else if (t2 > 0) t = t2;
        return t;
    }
}
