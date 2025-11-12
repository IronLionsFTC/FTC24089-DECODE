package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;

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
        Pose current = this.follower.follower.getPose();
        Pose newPose = new Pose(current.getX() + 5, current.getY() + 5);
        this.follower.follower.followPath(
                new PathBuilder(follower.follower)
                        .addPath(new BezierLine(current, newPose))
                        .setConstantHeadingInterpolation(Math.toRadians(this.degrees.getAsDouble()))
                        .build()
        );
        this.follower.follower.setMaxPower(1);
        this.follower.follower.turnToDegrees(this.degrees.getAsDouble());
    }

    @Override
    public boolean finished() {
        return Math.toDegrees(this.follower.follower.getHeadingError()) < 4;
    }
}
