package org.firstinspires.ftc.teamcode.lioncore.tasks;

import java.util.function.BooleanSupplier;

public class Optional extends Task {

    private Task task;
    private BooleanSupplier condition;
    private boolean evaluated;

    public Optional(Task task, BooleanSupplier condition) {
        this.task = task;
        this.condition = condition;
        this.evaluated = false;
    }

    @Override
    public void init() {
        this.evaluated = this.condition.getAsBoolean();
        if (this.evaluated) this.task.init();
    }

    @Override
    public void run() {
        if (this.evaluated) this.task.run();
    }

    @Override
    public boolean finished() {
        if (!this.evaluated) return true;
        return this.task.finished();
    }

    @Override
    public void end(boolean interrupted) {
        if (this.evaluated && interrupted) {
            this.task.end(true);
        }
    }
}
