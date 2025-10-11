package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Shooter;

public class TeleOpTogglePower extends Task {

    private Shooter shooter;

    public TeleOpTogglePower(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void init() {
        if (this.shooter.getRawTargetRPM() == 4500) this.shooter.setTargetRPM(3000);
        else shooter.setTargetRPM(4500);
    }

    @Override
    public boolean finished() {
        return true;
    }
}
