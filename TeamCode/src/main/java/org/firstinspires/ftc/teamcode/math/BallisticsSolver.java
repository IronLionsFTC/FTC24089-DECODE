package org.firstinspires.ftc.teamcode.math;

// BallisticsSolver.java
public class BallisticsSolver {

    // Physical constants (inches, slugs, seconds)
    private static final double G = 386.0; // in/s^2 downward
    private static final double RHO = 0.00000237; // slugs/in^3
    private static final double CD = 0.3; // drag coefficient (hollow ball)
    private static final double BALL_DIAM_IN = 5.0; // inches
    private static final double EFFECTIVE_AREA = 0.7 * Math.PI * Math.pow(BALL_DIAM_IN/2.0, 2.0); // in^2
    private static final double MASS_KG = 0.075; // 75 g
    private static final double KG_PER_SLUG = 14.5939029372;
    private static final double MASS_SLUGS = MASS_KG / KG_PER_SLUG;

    // Simulation params (tuned for speed)
    private static final double DT = 0.01; // integration timestep (s)
    private static final double MAX_SIM_TIME = 10.0; // seconds per shot simulation
    private static final double ACCEPT_RADIUS = 5.0; // inches — user-specified "good enough"
    private static final long TIME_BUDGET_MS_DEFAULT = 5; // milliseconds maximum CPU time to spend
    private static final int AZ_SAMPLES_COARSE = 5;   // few samples to be fast
    private static final int ELEV_SAMPLES_COARSE = 16;
    private static final int REFINEMENT_SAMPLES = 7; // small local refinement
    private static final int MAX_REFINEMENTS = 4;

    // returned solution
    public static class Solution {
        public double azimuthDeg;    // CCW from +X
        public double elevationUpDeg;   // degrees up from horizontal (NaN if none)
        public double elevationDownDeg; // degrees up from horizontal (NaN if none)
        public double impactTimeUp = Double.NaN;
        public double impactTimeDown = Double.NaN;
        public double chosenImpactTime = Double.NaN;
        public boolean foundUp = false;
        public boolean foundDown = false;
        public boolean timedOut = false;
        public boolean unreachableByNoDrag = false;
        public double bestMissInches = Double.POSITIVE_INFINITY;
        public Vector3 bestInitialVelocity = null; // v0 = sourceVel + launchSpeed * dir for best candidate
        public String toString() {
            return String.format("az=%.3f, elevUp=%.3f (t=%.3f) elevDown=%.3f (t=%.3f) timedOut=%b unreachableNoDrag=%b bestMiss=%.2f",
                    azimuthDeg, elevationUpDeg, impactTimeUp, elevationDownDeg, impactTimeDown, timedOut, unreachableByNoDrag, bestMissInches);
        }
    }

    /**
     * Fast solver with time budget (5ms default). Returns best solution found within budget.
     */
    public static Solution solveIntercept(Vector3 source, Vector3 target, Vector3 sourceVel, double launchSpeed) {
        return solveIntercept(source, target, sourceVel, launchSpeed, TIME_BUDGET_MS_DEFAULT);
    }

    public static Solution solveIntercept(Vector3 source, Vector3 target, Vector3 sourceVel, double launchSpeed, long timeBudgetMs) {
        long startNano = System.nanoTime();
        long budgetNano = timeBudgetMs * 1_000_000L;

        Solution out = new Solution();

        // trivial checks
        if (launchSpeed <= 0) {
            out.timedOut = false;
            out.unreachableByNoDrag = true;
            return out;
        }

        Vector3 delta = target.sub(source);
        double horizDist = Math.hypot(delta.x, delta.y);
        double nominalAz = Math.atan2(delta.y, delta.x);
        out.azimuthDeg = Math.toDegrees(nominalAz);

        // quick no-drag feasibility: standard ballistic discriminant test (conservative)
        // if no-drag cannot reach, with drag it won't either -> fast exit
        {
            double v = launchSpeed;
            double z = delta.z;
            double g = G;
            // using formula: v^4 - g*(g*R^2 + 2*z*v^2) >= 0 for real elevation angles
            double disc = v*v*v*v - g * (g*horizDist*horizDist + 2.0*z*v*v);
            if (disc < 0) {
                out.unreachableByNoDrag = true;
                out.timedOut = false;
                return out; // quick early-out
            }
        }

        // Adaptive azimuth window to compensate for moving launcher
        double tGuess = Math.max(0.01, horizDist / Math.max(0.01, launchSpeed));
        double leadX = delta.x - sourceVel.x * tGuess;
        double leadY = delta.y - sourceVel.y * tGuess;
        double leadAz = Math.atan2(leadY, leadX);
        double azShiftDeg = Math.toDegrees(Math.abs(leadAz - nominalAz));
        double azWindowDeg = Math.max(8.0, azShiftDeg + 8.0); // at least ±8°, expanded if sourceVel large
        double azStart = nominalAz - Math.toRadians(azWindowDeg);
        double azEnd   = nominalAz + Math.toRadians(azWindowDeg);

        // sampling strategy: coarse az × coarse elev, keep best candidate (min miss distance),
        // accept immediately if within ACCEPT_RADIUS.
        Candidate bestUp = null;
        Candidate bestDown = null;
        Candidate bestOverall = null;

        // coarse samples
        for (int ia = 0; ia < AZ_SAMPLES_COARSE; ia++) {
            // time budget check
            if (System.nanoTime() - startNano > budgetNano) { out.timedOut = true; break; }

            double at = (AZ_SAMPLES_COARSE == 1) ? 0.5 : (double)ia / (AZ_SAMPLES_COARSE - 1);
            double az = azStart + at * (azEnd - azStart);

            // elevation sampling range: from -10 to +80 degrees (radians)
            double elevMin = Math.toRadians(-10.0);
            double elevMax = Math.toRadians(80.0);

            for (int ie = 0; ie < ELEV_SAMPLES_COARSE; ie++) {
                if (System.nanoTime() - startNano > budgetNano) { out.timedOut = true; break; }

                double ut = (ELEV_SAMPLES_COARSE == 1) ? 0.5 : (double)ie / (ELEV_SAMPLES_COARSE - 1);
                double elev = elevMin + ut * (elevMax - elevMin);
                Candidate c = simulateShotLimited(source, target, sourceVel, launchSpeed, az, elev, startNano, budgetNano);
                // update bestOverall and categorize
                if (c != null) {
                    // immediate accept if within ACCEPT_RADIUS
                    if (c.hitDistance <= ACCEPT_RADIUS) {
                        // accept immediately, fill outputs and return
                        populateSolutionFromCandidate(out, c);
                        out.timedOut = false;
                        return out;
                    }
                    // otherwise track bests by sign of vz
                    if (bestOverall == null || c.hitDistance < bestOverall.hitDistance) bestOverall = c;
                    if (c.vzAtImpact > 0) {
                        if (bestUp == null || c.hitDistance < bestUp.hitDistance) bestUp = c;
                    } else {
                        if (bestDown == null || c.hitDistance < bestDown.hitDistance) bestDown = c;
                    }
                }
            } // elev samples
            if (out.timedOut) break;
        } // az samples

        // If timed out during coarse sampling, return best we have (if any)
        if (out.timedOut) {
            fillFromBestIfAny(out, bestOverall, bestUp, bestDown, startNano, budgetNano);
            return out;
        }

        // Refinement: around best overall candidate(s) do a few local refinements while respecting time budget
        Candidate[] seeds = { bestUp, bestDown, bestOverall };
        for (Candidate seed : seeds) {
            if (seed == null) continue;
            Candidate localBest = localRefineAround(seed, source, target, sourceVel, launchSpeed, startNano, budgetNano);
            if (localBest != null) {
                if (localBest.hitDistance <= ACCEPT_RADIUS) {
                    populateSolutionFromCandidate(out, localBest);
                    out.timedOut = false;
                    return out;
                }
                // update best trackers
                if (bestOverall == null || localBest.hitDistance < bestOverall.hitDistance) bestOverall = localBest;
                if (localBest.vzAtImpact > 0) {
                    if (bestUp == null || localBest.hitDistance < bestUp.hitDistance) bestUp = localBest;
                } else {
                    if (bestDown == null || localBest.hitDistance < bestDown.hitDistance) bestDown = localBest;
                }
            }
            if (System.nanoTime() - startNano > budgetNano) { out.timedOut = true; break; }
        }

        // done sampling / refining or timed out. Fill solution from best candidate(s) we saw.
        fillFromBestIfAny(out, bestOverall, bestUp, bestDown, startNano, budgetNano);
        return out;
    }

    // helper: populate Solution fields from candidate
    private static void populateSolutionFromCandidate(Solution out, Candidate c) {
        out.azimuthDeg = Math.toDegrees(c.azimuth);
        if (c.vzAtImpact > 0) {
            out.elevationUpDeg = Math.toDegrees(c.elevation);
            out.impactTimeUp = c.timeToImpact;
            out.foundUp = true;
        } else {
            out.elevationDownDeg = Math.toDegrees(c.elevation);
            out.impactTimeDown = c.timeToImpact;
            out.foundDown = true;
        }
        out.bestMissInches = c.hitDistance;
        out.bestInitialVelocity = c.initialVelocity;
        out.chosenImpactTime = c.timeToImpact;
    }

    private static void fillFromBestIfAny(Solution out, Candidate overall, Candidate up, Candidate down, long startNano, long budgetNano) {
        out.timedOut = (System.nanoTime() - startNano > budgetNano);
        Candidate chosen = null;
        // prefer any candidate within twice ACCEPT_RADIUS; else pick smallest miss
        if (up != null && up.hitDistance <= 2*ACCEPT_RADIUS) chosen = up;
        if (down != null && down.hitDistance <= 2*ACCEPT_RADIUS) {
            if (chosen == null || down.hitDistance < chosen.hitDistance) chosen = down;
        }
        if (chosen == null) chosen = overall;

        if (chosen != null) {
            populateSolutionFromCandidate(out, chosen);
            if (chosen.vzAtImpact > 0) out.foundUp = true;
            else out.foundDown = true;
        } else {
            // nothing found -> leave NaNs and report bestMissInches = +inf
        }
    }

    // local refinement around a seed candidate: small window refinement with time checks
    private static Candidate localRefineAround(Candidate seed, Vector3 src, Vector3 tgt, Vector3 srcVel, double launchSpeed, long startNano, long budgetNano) {
        if (seed == null) return null;
        Candidate best = seed;
        double az = seed.azimuth;
        double elevCenter = seed.elevation;
        double elevRange = Math.toRadians(6.0); // +/- 6 deg
        for (int r = 0; r < MAX_REFINEMENTS; r++) {
            if (System.nanoTime() - startNano > budgetNano) break;
            double low = elevCenter - elevRange;
            double high = elevCenter + elevRange;
            Candidate localBest = null;
            for (int i = 0; i < REFINEMENT_SAMPLES; i++) {
                if (System.nanoTime() - startNano > budgetNano) break;
                double t = (REFINEMENT_SAMPLES==1) ? 0.5 : (double)i / (REFINEMENT_SAMPLES - 1);
                double elev = low + t*(high - low);
                Candidate c = simulateShotLimited(src, tgt, srcVel, launchSpeed, az, elev, startNano, budgetNano);
                if (c == null) continue;
                if (localBest == null || c.hitDistance < localBest.hitDistance) localBest = c;
                if (c.hitDistance <= ACCEPT_RADIUS) return c;
            }
            if (localBest == null) break;
            best = localBest;
            elevCenter = best.elevation;
            elevRange *= 0.5; // narrow window
        }
        return best;
    }

    // Candidate struct (internal)
    private static class Candidate {
        double azimuth;
        double elevation;
        double timeToImpact;
        double hitDistance; // inches; 0 = perfect hit
        double vzAtImpact;
        Vector3 initialVelocity;
    }

    // Simulate one shot but abort early if time budget is exceeded (using nano times passed in)
    // Returns Candidate (even if hitDistance > ACCEPT_RADIUS) or null if nothing or timed out
    private static Candidate simulateShotLimited(Vector3 source, Vector3 target, Vector3 sourceVel, double launchSpeed,
                                                 double az, double elev, long startNano, long budgetNano) {
        // direction
        double cosE = Math.cos(elev);
        Vector3 dir = new Vector3(cosE*Math.cos(az), cosE*Math.sin(az), Math.sin(elev));
        Vector3 v0 = sourceVel.add(dir.scale(launchSpeed));
        Vector3 pos = new Vector3(source.x, source.y, source.z);
        double vx = v0.x, vy = v0.y, vz = v0.z;
        double t = 0.0;

        Candidate bestSeen = null;
        double prevX = pos.x, prevY = pos.y, prevZ = pos.z;
        double prevVx = vx, prevVy = vy, prevVz = vz;

        while (t < MAX_SIM_TIME) {
            // time budget check
            if (System.nanoTime() - startNano > budgetNano) {
                // return bestSeen if we have one, else null to indicate no progress
                return bestSeen;
            }

            // compute drag acceleration
            double vmag = Math.sqrt(vx*vx + vy*vy + vz*vz);
            double ax, ay, azc;
            if (vmag > 1e-8) {
                double Fd = 0.5 * CD * RHO * EFFECTIVE_AREA * vmag * vmag;
                double aDragMag = Fd / MASS_SLUGS; // in/s^2
                ax = -aDragMag * (vx / vmag);
                ay = -aDragMag * (vy / vmag);
                azc = -aDragMag * (vz / vmag) - G;
            } else {
                ax = 0; ay = 0; azc = -G;
            }

            // semi-implicit Euler
            vx += ax * DT;
            vy += ay * DT;
            vz += azc * DT;
            pos.x += vx * DT;
            pos.y += vy * DT;
            pos.z += vz * DT;
            t += DT;

            // distance to target
            double dx = pos.x - target.x;
            double dy = pos.y - target.y;
            double dz = pos.z - target.z;
            double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);

            // track best seen candidate (min distance)
            if (bestSeen == null || dist < bestSeen.hitDistance) {
                bestSeen = new Candidate();
                bestSeen.azimuth = az;
                bestSeen.elevation = elev;
                bestSeen.timeToImpact = t;
                bestSeen.hitDistance = dist;
                bestSeen.vzAtImpact = vz;
                bestSeen.initialVelocity = new Vector3(v0.x, v0.y, v0.z);
            }

            // quick accept if within ACCEPT_RADIUS
            if (dist <= ACCEPT_RADIUS) {
                return bestSeen;
            }

            // interpolation across target.z plane to catch fast passes
            if ((prevZ - target.z) * (pos.z - target.z) <= 0.0) { // sign change or touch
                double denom = (pos.z - prevZ);
                double alpha = Math.abs(denom) > 1e-9 ? (target.z - prevZ) / denom : 0.5;
                double ix = prevX + alpha*(pos.x - prevX);
                double iy = prevY + alpha*(pos.y - prevY);
                double horiz = Math.hypot(ix - target.x, iy - target.y);
                double combinedDist = Math.sqrt(horiz*horiz + 0.0); // dz is zero (we matched z)
                if (combinedDist <= ACCEPT_RADIUS) {
                    // accept interpolated hit
                    bestSeen.timeToImpact = t - DT + alpha*DT;
                    bestSeen.vzAtImpact = prevVz + alpha*(vz - prevVz);
                    bestSeen.hitDistance = combinedDist;
                    return bestSeen;
                }
            }

            // bounds: stop if very low or extremely far away
            if (pos.z < -100.0) break;
            if (Math.abs(pos.x - source.x) > 2000 || Math.abs(pos.y - source.y) > 2000) break;

            prevX = pos.x; prevY = pos.y; prevZ = pos.z;
            prevVx = vx; prevVy = vy; prevVz = vz;
        } // while

        // return the best seen (may be far) or null if nothing
        return bestSeen;
    }
}
