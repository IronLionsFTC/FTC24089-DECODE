package org.firstinspires.ftc.teamcode.tasks;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Feed;
import org.firstinspires.ftc.teamcode.systems.Turret;

public class ControlConfig extends Task {

    private Feed feed;
    private Turret turret;

    public ControlConfig(Feed feed, Turret turret) {
        this.feed = feed;
        this.turret = turret;
    }

    @Config
    @Configurable
    public static class ShooterState {
        public static double mode = 0;
    }

    @Override
    public void run() {
        this.turret.setState(Turret.State.Shooting);
        if (ShooterState.mode == 1) {
            this.feed.setState(Feed.State.Full);
        } else if (ShooterState.mode == 2) {
            this.feed.setState(Feed.State.Shooting);
        } else {
            this.feed.setState(Feed.State.Rest);
        }
    }
}
