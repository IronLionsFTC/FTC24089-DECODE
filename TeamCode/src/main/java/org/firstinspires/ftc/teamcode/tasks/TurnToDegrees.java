package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;

import java.util.function.DoubleSupplier;

public class TurnToDegrees extends Task {
    private FollowerWrapper follower;
    private DoubleSupplier degrees;

    public TurnToDegrees(FollowerWrapper follower, double degrees) {
        this.follower = follower;
        this.degrees = () -> degrees;
    }

    public TurnToDegrees(FollowerWrapper follower, DoubleSupplier degrees) {
        this.follower = follower;
        this.degrees = degrees;
    }

    @Override
    public void init() {
        this.follower.follower.turnToDegrees(this.degrees.getAsDouble());
    }

    @Override
    public boolean finished() {
        return !this.follower.follower.isTurning();
    }
}
