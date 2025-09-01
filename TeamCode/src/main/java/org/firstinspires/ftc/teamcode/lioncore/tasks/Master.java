package org.firstinspires.ftc.teamcode.lioncore.tasks;

import java.util.ArrayList;
import java.util.List;

public class Master extends Task {

    private Task master;
    private List<Task> slaves;
    private List<Boolean> slaveStatus;
    private boolean running;

    /**
     * Run master & slaves together, until master finishes.
     * @param master
     * @param slaves
     */
    public Master(Task master, Task... slaves) {
        this.master = master;
        this.slaves = new ArrayList<>();
        for (Task slave : slaves) {
            this.slaves.add(slave);
            this.slaveStatus.add(false);
        }
        this.running = false;
    }

    @Override
    public void init() {
        this.master.init();
        this.running = true;

        for (int i = 0; i < this.slaves.size(); i++) {
            this.slaves.get(i).init();
            this.slaveStatus.set(i, true);
        }
    }

    @Override
    public void run() {
        if (!this.running) return;

        this.master.run();

        for (int i = 0; i < this.slaves.size(); i++) {
            if (this.slaveStatus.get(i)) this.slaves.get(i).init();
        }
    }

    @Override
    public boolean finished() {
        if (this.master.finished()) {
            running = false;
            return true;
        }
        for (int i = 0; i < this.slaves.size(); i++) {
            if (this.slaves.get(i).finished()) {
                this.slaveStatus.set(i, false);
                this.slaves.get(i).end(false);
            }
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.master.end(interrupted);
        for (int i = 0; i < this.slaves.size(); i++) {
            if (this.slaveStatus.get(i)) {
                this.slaves.get(i).end(true);
            }
        }
    }
}
