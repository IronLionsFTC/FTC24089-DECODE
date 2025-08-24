package org.firstinspires.ftc.teamcode.lioncore.tasks;

public class Run extends TaskBase {
    private Runnable runnable;

    public Run(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        this.runnable.run();
    }

    @Override
    public boolean finished() {
        return true;
    }
}
