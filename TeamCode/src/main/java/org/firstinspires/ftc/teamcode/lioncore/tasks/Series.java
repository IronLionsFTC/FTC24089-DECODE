package org.firstinspires.ftc.teamcode.lioncore.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Series extends TaskBase {
    private List<TaskBase> tasks;
    private int index;

    public Series(TaskBase... tasks) {
        this.tasks = new ArrayList<>();
        this.tasks.addAll(Arrays.asList(tasks));
        this.index = 0;
    }

    @Override
    public void init() {
        TaskBase task = this.tasks.get(this.index);
        if (task != null) {
            task.init();
        }
    }

    @Override
    public void run() {
        TaskBase task = this.tasks.get(this.index);
        if (task != null) {
            task.run();
            if (task.finished()) {
                task.end(false);
                this.index += 1;
            }
        }
    }

    @Override
    public boolean finished() {
        return this.index >= this.tasks.size();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            TaskBase task = this.tasks.get(this.index);
            if (task != null) {
                task.end(true);
            }
        }
    }
}
