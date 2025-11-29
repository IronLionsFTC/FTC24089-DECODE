package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Feed;
import org.firstinspires.ftc.teamcode.systems.Turret;

public class ShootForTime extends Task {
    private double shootTime;
    private Feed feed;
    private Turret turret;
    private Timer timer;
    boolean stopShooterAtEnd;

    public ShootForTime(Feed feed, Turret turret, double time) {
        this.feed = feed;
        this.turret = turret;
        this.shootTime = time;
        this.timer = new Timer();
        this.stopShooterAtEnd = true;
    }

    public ShootForTime(Feed feed, Turret turret, double time, boolean cancelShooter) {
        this.feed = feed;
        this.turret = turret;
        this.shootTime = time;
        this.timer = new Timer();
        this.stopShooterAtEnd = cancelShooter;
    }

    @Override
    public void init() {
        this.timer.resetTimer();
        if (this.turret.getState() == Turret.State.Shooting) {
            this.feed.setState(Feed.State.Shooting);
        }
    }

    @Override
    public boolean finished() {
        return this.timer.getElapsedTimeSeconds() > this.shootTime;
    }

    @Override
    public void end(boolean i) {
        this.feed.setState(Feed.State.Rest);
        if (this.stopShooterAtEnd) this.turret.setState(Turret.State.Tracking);
    }
}
