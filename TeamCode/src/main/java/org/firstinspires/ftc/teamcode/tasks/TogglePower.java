package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Shooter;

public class TogglePower extends Task {
    private Shooter shooter;

    public TogglePower(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void init() {
        if (this.shooter.speedFactor < 1) this.shooter.speedFactor = 1;
        else this.shooter.speedFactor = 0.96;
    }

    @Override
    public boolean finished() {
        return true;
    }
}
