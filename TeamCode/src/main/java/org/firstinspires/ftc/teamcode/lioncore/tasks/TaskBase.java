package org.firstinspires.ftc.teamcode.lioncore.tasks;

public abstract class TaskBase {
    public void init() {}
    public void run() {}
    public boolean finished() { return false; }
    public void end(boolean interrupted) { }

    public TaskBase then(TaskBase... tasks) {
        TaskBase[] allTasks = new TaskBase[tasks.length + 1];
        allTasks[0] = this;
        System.arraycopy(tasks, 0, allTasks, 1, tasks.length);
        return new Series(allTasks);
    }

    public TaskBase with(TaskBase... tasks) {
        TaskBase[] allTasks = new TaskBase[tasks.length + 1];
        allTasks[0] = this;
        System.arraycopy(tasks, 0, allTasks, 1, tasks.length);
        return new Parallel(allTasks);
    }

    public TaskBase race(TaskBase... tasks) {
        TaskBase[] allTasks = new TaskBase[tasks.length + 1];
        allTasks[0] = this;
        System.arraycopy(tasks, 0, allTasks, 1, tasks.length);
        return new Race(allTasks);
    }
}