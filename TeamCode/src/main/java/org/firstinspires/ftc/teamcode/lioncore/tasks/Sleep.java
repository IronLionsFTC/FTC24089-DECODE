package org.firstinspires.ftc.teamcode.lioncore.tasks;

public class Sleep extends TaskBase {

    private double seconds;
    private long startTime;

    public Sleep(float seconds) {
        this.seconds = seconds;
    }

    public void init() {
        startTime = System.nanoTime();
    }

    public boolean finished() {
        long deltaNs = System.nanoTime() - startTime;
        double deltaS = deltaNs / 10e9;
        return deltaS > seconds;
    }
}
