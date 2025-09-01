package org.firstinspires.ftc.teamcode.lioncore.tasks;

public class Sleep extends Task {

    private final double seconds;
    private long startTime;

    public Sleep(double seconds) {
        this.seconds = seconds;
    }

    public void init() {
        startTime = System.nanoTime();
    }

    public boolean finished() {
        long deltaNs = System.nanoTime() - startTime;
        double deltaS = deltaNs / 1e9;
        return deltaS > seconds;
    }
}
