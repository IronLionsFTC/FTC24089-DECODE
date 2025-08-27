package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.PathChain;

import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskBase;

public class FollowPath extends TaskBase {
    private Follower follower;
    private PathChain pathChain;
    private double speed;
    private boolean holdEnd;

    public FollowPath(Follower follower, PathChain pathChain) {
        this.follower = follower;
        this.pathChain = pathChain;
        this.speed = 1;
        this.holdEnd = false;
    }

    public TaskBase setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public TaskBase holdEnd(boolean holdEnd) {
        this.holdEnd = holdEnd;
        return this;
    }

    @Override
    public void init() {
        this.follower.setMaxPower(this.speed);
        this.follower.followPath(this.pathChain, this.holdEnd);
    }

    @Override
    public boolean finished() {
        return this.follower.getCurrentTValue() > 0.98;
    }

    @Override
    public void end(boolean interrupted) {
        this.follower.setMaxPower(1);
        if (interrupted) {
            this.follower.breakFollowing();
        }
    }
}
