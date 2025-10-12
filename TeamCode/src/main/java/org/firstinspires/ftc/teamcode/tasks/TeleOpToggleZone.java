package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Shooter;

public class TeleOpToggleZone extends Task {

    private Shooter shooter;

    public TeleOpToggleZone(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void init() {
        if (shooter.getRawTargetRPM() == 3550) {
            this.shooter.setTargetRPM(2800);
            this.shooter.setHoodAngle(0.5);
        } else {
            this.shooter.setTargetRPM(3550);
            this.shooter.setHoodAngle(1.2);
        }
    }

    @Override
    public boolean finished() {
        return true;
    }
}
