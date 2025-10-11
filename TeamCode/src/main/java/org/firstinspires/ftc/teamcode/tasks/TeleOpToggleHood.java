package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Shooter;

public class TeleOpToggleHood extends Task {

    private Shooter shooter;

    public TeleOpToggleHood(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void init() {
        this.shooter.setHoodAngle(1 - this.shooter.getHoodAngle());
    }

    @Override
    public boolean finished() {
        return true;
    }
}
