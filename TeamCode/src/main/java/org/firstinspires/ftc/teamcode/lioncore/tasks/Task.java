package org.firstinspires.ftc.teamcode.lioncore.tasks;

public abstract class Task {
    public void init() {}
    public void run() {}
    public boolean finished() { return false; }
    public void end(boolean interrupted) { }

    public Task then(Task... tasks) {
        Task[] allTasks = new Task[tasks.length + 1];
        allTasks[0] = this;
        System.arraycopy(tasks, 0, allTasks, 1, tasks.length);
        return new Series(allTasks);
    }

    public Task with(Task... tasks) {
        Task[] allTasks = new Task[tasks.length + 1];
        allTasks[0] = this;
        System.arraycopy(tasks, 0, allTasks, 1, tasks.length);
        return new Parallel(allTasks);
    }

    public Task race(Task... tasks) {
        Task[] allTasks = new Task[tasks.length + 1];
        allTasks[0] = this;
        System.arraycopy(tasks, 0, allTasks, 1, tasks.length);
        return new Race(allTasks);
    }

    public Task master(Task... slaves) {
        return new Master(this, slaves);
    }
}