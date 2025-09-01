package org.firstinspires.ftc.teamcode.lioncore.tasks;

import com.pedropathing.util.Timer;

public class Sleep extends Task {

    private final double seconds;
    private Timer timer;

    public Sleep(double seconds) {
        this.seconds = seconds;
    }

    public void init() {
        this.timer = new Timer();
        this.timer.resetTimer();
    }

    public boolean finished() {
        return this.timer.getElapsedTimeSeconds() > this.seconds;
    }
}
