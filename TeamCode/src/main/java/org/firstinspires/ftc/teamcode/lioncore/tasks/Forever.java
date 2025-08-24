package org.firstinspires.ftc.teamcode.lioncore.tasks;

public class Forever extends TaskBase {
    private TaskBase task;

    public Forever(TaskBase task) {
        this.task = task;
    }

    public Forever(Runnable runnable) {
        this.task = new Run(runnable);
    }

    @Override
    public void init() {
        this.task.init();
    }

    @Override
    public void run() {
        this.task.run();
        if (this.task.finished()) {
            this.task.end(false);
            this.task.init();
        }
    }

    @Override
    public void end(boolean interrupted) {
        this.task.end(true);
    }
}
