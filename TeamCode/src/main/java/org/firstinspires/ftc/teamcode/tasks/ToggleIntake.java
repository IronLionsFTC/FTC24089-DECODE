package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Feed;

public class ToggleIntake extends Task {
    private Feed feed;

    public ToggleIntake(Feed feed) {
        this.feed = feed;
    }

    @Override
    public void init() {
        switch (this.feed.getState()) {
            case Rest:
                this.feed.setState(Feed.State.Full);
                break;
            default:
                this.feed.setState(Feed.State.Rest);
                break;
        }
    }

    @Override
    public boolean finished() {
        return true;
    }
}
