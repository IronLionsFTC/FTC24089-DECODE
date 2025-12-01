package org.firstinspires.ftc.teamcode.math;

import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.teamcode.systems.Turret;

public class ProjectileMotion {

    /**
     * Solution class detailing necessary values to hit target
     */
    public static class Solution {
        public double targetRPM;
        public double hoodAngle;
        public double azimuthHeading;
        public double timeOfFlight;

        public Solution(
                double targetRPM,
                double hoodAngle,
                double azimuthHeading,
                double timeOfFlight
        ) {
            this.targetRPM = targetRPM;
            this.hoodAngle = hoodAngle;
            this.azimuthHeading = azimuthHeading;
            this.timeOfFlight = timeOfFlight;
        }
    }

    /**
     * Calculate all aiming calculations.
     * @param robotPosition The robots position vector (xy plane is ground) in inches.
     * @param robotVelocity The robots velocity vector (xy plane is ground) in inches / second.
     * @param targetPosition The target position vector (xy plane is ground, +z is up.
     * @return Solution containing RPM, hood angle [0, 1], 1 is horizontal, azimuth (degrees counterclockwise from x axis) on ground pland and expected time of flight.
     */
    public static Solution calculate(Vector3 robotPosition, Vector3 robotVelocity, Vector3 targetPosition, TelemetryManager telemetry, double angularVelocity) {

        // Radial vectors
        Vector3 radial = robotPosition.sub(targetPosition);
        Vector3 normalised_radial = radial.normalize();
        double distance = radial.length();

        // Calculate velocity compenents in tangential space
        double normalVelocity = robotVelocity.dot(normalised_radial);
        Vector3 tangentialDirectionalVelocity = robotVelocity.sub(normalised_radial.multiply(normalVelocity));
        double tangentialVelocity = tangentialDirectionalVelocity.length();

        telemetry.addData("NORMAL VELOCITY", normalVelocity);
        telemetry.addData("TANGENTIAL VELOCITY", tangentialVelocity);

        // Linear approximation of shooting power
        double RPM = distance * Turret.RobotConstants.shooterMultiplier + Turret.RobotConstants.shooterOffset;
        double hood = Math.max(Math.min(1.1, 1.1 - distance / Turret.RobotConstants.hoodDivisor + Turret.RobotConstants.hoodOffset), 0);

        // Heading calculation
        Vector3 displacement = targetPosition.sub(robotPosition);
        double azimuthRaw = Math.toDegrees(Math.atan2(displacement.y, displacement.x));

        // Cross-product test to determine direction of motion
        Vector3 normalisedAngularMomentum = radial.cross(tangentialDirectionalVelocity);
        telemetry.addData("ANGULAR MOMENTUM", normalisedAngularMomentum);
        double clampedTangentialVelocity = 20 * (1 - Math.pow(Math.E, -(0.03 * tangentialVelocity)));
        double counterclockwiseTangentialVelocity = clampedTangentialVelocity * Math.signum(normalisedAngularMomentum.z);
        telemetry.addData("LEAD", counterclockwiseTangentialVelocity);
        double azimuthVelocityLeading = azimuthRaw + counterclockwiseTangentialVelocity;

        // Time-of-flight (not including velocities)
        double initialVelocity = RPM / 60 * 11.84 * 1/4;
        double launchAngle = 30 + hood * 30;
        double xy_distance = Math.hypot(radial.x, radial.y);
        double timeOfFlight = xy_distance / (initialVelocity * Math.cos(Math.toRadians(launchAngle)));

        // Adjust RPM to account for normal velocity
        double RPMleading = RPM + normalVelocity * 13;

        return new Solution(
                RPMleading,
                hood,
                azimuthRaw,
                timeOfFlight
        );
    }
}
