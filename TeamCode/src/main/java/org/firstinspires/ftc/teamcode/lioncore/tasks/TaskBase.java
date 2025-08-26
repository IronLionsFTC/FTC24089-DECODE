package org.firstinspires.ftc.teamcode.lioncore.tasks;

public abstract class TaskBase {
    public void init() {}
    public void run() {}
    public boolean finished() { return false; }
    public void end(boolean interrupted) { }

    public TaskBase then(TaskBase... tasks) {
        return new Series(tasks);
    }

    public TaskBase with(TaskBase... tasks) {
        return new Parallel(tasks);
    }

    public TaskBase race(TaskBase... tasks) {
        return new Race(tasks);
    }
}