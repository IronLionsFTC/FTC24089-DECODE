package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Shooter;

public class RevFlywheel extends Task {

    private Shooter shooter;
    private double target;

    public RevFlywheel(Shooter shooter, double target) {
        this.shooter = shooter;
        this.target = target;
    }

    @Override
    public void init() {
        this.shooter.setState(Shooter.State.Target);
        this.shooter.setTargetRPM(target);
    }

    @Override
    public boolean finished() {
        return Math.abs(this.shooter.getTargetRPM() - this.shooter.getRPM()) < 200;
    }
}
