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

    public double calculate(double current, double target) {
        long currentTime = System.nanoTime();

        // Convert from NS -> S
        double deltaTime = (currentTime - lastTime) / 1e9;
        lastTime = currentTime;
        double error = target - current;

        // Track the sum of error before this point
        integral += error * deltaTime;

        // Change in error over time (approx)
        double derivative = (error - previousError) / deltaTime;
        previousError = error;

        // Output
        return P * error + I * integral + D * derivative;
    }
}