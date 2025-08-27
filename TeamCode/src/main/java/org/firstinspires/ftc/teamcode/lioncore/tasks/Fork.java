package org.firstinspires.ftc.teamcode.lioncore.tasks;

import java.util.function.BooleanSupplier;

public class Fork extends TaskBase {
    private BooleanSupplier condition;
    private TaskBase optionA;
    private TaskBase optionB;
    private boolean usingA;

    /**
     * Create a task that will execute A if condition yields true on init, and B if condition yields false on init.
     * @param optionA
     * @param optionB
     * @param condition
     */
    public Fork(TaskBase optionA, TaskBase optionB, BooleanSupplier condition) {
        this.optionA = optionA;
        this.optionB = optionB;
        this.condition = condition;
    }

    private TaskBase getActive() {
        if (this.usingA) {
            return this.optionA;
        } else {
            return this.optionB;
        }
    }

    public void init() {
        this.usingA = condition.getAsBoolean();
        this.getActive().init();
    }

    public void run() {
        this.getActive().run();
    }

    public boolean finished() {
        return this.getActive().finished();
    }

    public void end(boolean interrupted) {
        this.getActive().end(interrupted);
    }
}
