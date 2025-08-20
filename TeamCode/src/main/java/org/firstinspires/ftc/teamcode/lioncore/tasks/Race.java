package com.core.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Race extends TaskBase {

    private List<TaskBase> tasks;
    private List<Boolean> finished;

    public Race(TaskBase... tasks) {
        this.tasks = new ArrayList<>();
        this.finished = new ArrayList<>();
        this.tasks.addAll(Arrays.asList(tasks));

        for (int i = 0; i < this.tasks.size(); i++) {
            this.finished.add(false);
        }
    }

    @Override
    public void init() { for (TaskBase task : this.tasks) task.init(); }

    @Override
    public void run() {
        // For each task (and its finished status)
        for (int i = 0; i < this.tasks.size(); i++) {

            // If the task has finished once before, never execute again
            if (this.finished.get(i)) {
                continue;
            }

            // Run the task
            this.tasks.get(i).run();

            // If the task has finished, mark it as such (and end it)
            if (this.tasks.get(i).finished()) {
                this.tasks.get(i).end(false);
                this.finished.set(i, true);
            }
        }
    }

    @Override
    public boolean finished() {
        // Return true if any task has finished
        for (Boolean finished : this.finished) {
            if (finished) return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // End all remaining tasks
        for (int i = 0; i <this.tasks.size(); i++) {
            if (!this.finished.get(i)) {
                this.tasks.get(i).end(interrupted);
            }
        }
    }
}
