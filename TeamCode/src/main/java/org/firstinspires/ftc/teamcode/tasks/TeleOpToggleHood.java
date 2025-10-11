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
        if (this.shooter.getHoodAngle() == 0) {
            this.shooter.setHoodAngle(1);
        } else if (this.shooter.getHoodAngle() == 1) {
            this.shooter.setHoodAngle(1.4);
        } else {
            this.shooter.setHoodAngle(0);
        }
    }

    @Override
    public boolean finished() {
        return true;
    }
}
