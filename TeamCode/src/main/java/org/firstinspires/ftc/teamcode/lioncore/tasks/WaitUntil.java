package org.firstinspires.ftc.teamcode.lioncore.tasks;

import java.util.function.BooleanSupplier;

public class WaitUntil extends TaskBase {
    private BooleanSupplier condition;

    public WaitUntil(BooleanSupplier condition) {
        this.condition = condition;
    }

    @Override
    public boolean finished() {
        return this.condition.getAsBoolean();
    }
}
