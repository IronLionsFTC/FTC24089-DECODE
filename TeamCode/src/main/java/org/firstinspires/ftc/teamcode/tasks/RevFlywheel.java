package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Shooter;

public class RevFlywheel extends Task {

    private Shooter shooter;
    private double target;
    private double angle;

    public RevFlywheel(Shooter shooter, double target, double angle) {
        this.shooter = shooter;
        this.target = target;
        this.angle = angle;
    }

    @Override
    public void init() {
        this.shooter.setState(Shooter.State.AutoAimed);
        this.shooter.setTargetRPM(target);
        this.shooter.setHoodAngle(angle);
    }

    @Override
    public boolean finished() {
        return Math.abs(this.shooter.getTargetRPM() - this.shooter.getRPM()) < 200;
    }
}
