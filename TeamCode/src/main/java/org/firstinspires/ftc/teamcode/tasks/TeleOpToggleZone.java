package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;

public class TeleOpToggleZone extends Task {

    private Shooter shooter;

    public TeleOpToggleZone(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void init() {
        if (shooter.getRawTargetRPM() == 3700) {
            this.shooter.setTargetRPM(2400);
            this.shooter.setHoodAngle(0);
        } else {
            this.shooter.setTargetRPM(3700);
            this.shooter.setHoodAngle(0.5);
        }
    }

    @Override
    public boolean finished() {
        return true;
    }
}
