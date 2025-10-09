package org.firstinspires.ftc.teamcode.lioncore.math.pid;

public class PID {
    private double P;
    private double I;
    private double D;

    private long lastTime;
    private double previousError;
    private double integral;

    public PID(double P, double I, double D) {
        this.P = P;
        this.I = I;
        this.D = D;
    }

    /**
     * Calculate the power response used to move current toward target.
     * @param current The current value (e.g. motor position)
     * @param target The target value (e.g. target motor position)
     * @return The power bound by [min, max], which are -1 and 1 by default.
     */
    public double calculate(double current, double target) {
        long currentTime = System.nanoTime();

        // Convert from NS -> S
        double deltaTime = (currentTime - lastTime) / 1e9;
        lastTime = currentTime;
        double error = target - current;

        // Track the sum of error before this point
        integral += error * deltaTime;

        // Change in error over time (approx)
        Double derivative = (error - previousError) / deltaTime;
        if (derivative.isNaN() || derivative.isInfinite()) return P * error + I * integral;
        previousError = error;

        // Output
        return P * error + I * integral + D * derivative;
    }

    /**
     * Update the PID constants.
     * @param P kP, scaling the error directly into power.
     * @param I kI, scaling the total amount of error-time into power.
     * @param D kD, scaling the predicted future error into power.
     */
    public void setConstants(double P, double I, double D) {
        this.P = P;
        this.I = I;
        this.D = D;
    }
}