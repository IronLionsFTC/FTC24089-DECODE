package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Parallel;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskBase;
import org.firstinspires.ftc.teamcode.lioncore.tasks.WaitUntil;

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
        return !this.follower.isBusy();
    }

    @Override
    public void end(boolean interrupted) {
        this.follower.setMaxPower(1);
        if (interrupted) {
            this.follower.breakFollowing();
        }
    }

    /**
     * Execute a task once the follower has reached a certain percentage.
     * This task will not finish until both the path and the task are finished.
     * @param progress The progress (T) value at which to start the task [0, 1]
     * @param tasks Tasks to execute (in parallel)
     * @return
     */
    public TaskBase uponProgress(double progress, TaskBase... tasks) {
        return this.with(
                new WaitUntil(() -> this.follower.getCurrentTValue() > progress).then(
                        new Parallel(tasks)
                )
        );
    }

    /**
     * Execute a task once the follower has reached a certain percentage.
     * Will finish once the follower is done, even if the tasks are not complete.
     * @param progress The progress (T) value at which to start the task [0, 1]
     * @param tasks Tasks to execute (in parallel)
     * @return
     */
    public TaskBase uponProgressUntilPathComplete(double progress, TaskBase... tasks) {
        return this.master(
            tasks
        );
    }
}
