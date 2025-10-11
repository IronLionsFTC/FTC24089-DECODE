package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Shooter;

public class stopFlywheel extends Task {
    private Shooter stop;

    public stopFlywheel(Shooter stop) {
        this.stop = stop;
    }

    @Override
    public void init() {
    }

    @Override
    public void stop() {
        this.stop.setState(Shooter.State.Rest);
    }

    @Override
    public boolean finished() {
        return true;
    }
}
