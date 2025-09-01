package org.firstinspires.ftc.teamcode.lioncore.tasks;

public class Forever extends Task {
    private Task task;
    private boolean running;

    /**
     * Execute a task over and over again, forever. (Until ENDED)
     * @param task
     */
    public Forever(Task task) {
        this.task = task;
        this.running = false;
    }

    public Forever(Runnable runnable) {
        this.task = new Run(runnable);
        this.running = false;
    }

    @Override
    public void init() {
        this.running = true;
        this.task.init();
    }

    @Override
    public void run() {
        if (!this.running) return;
        this.task.run();
        if (this.task.finished()) {
            this.task.end(false);
            this.task.init();
        }
    }

    @Override
    public void end(boolean interrupted) {
        this.task.end(true);
        this.running = false;
    }
}
