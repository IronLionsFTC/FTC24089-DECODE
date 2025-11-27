package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;

public class SetHoodAngle extends Task {
    private double angle;
    private Shooter shooter;

    public SetHoodAngle(Shooter shooter, double angle ) {
        this.shooter = shooter;
        this.angle = angle;

    }

    @Override
    public void init() {
    }

    @Override
    public void run() {
        this.shooter.setHoodAngle(this.angle);
    }

    @Override
    public boolean finished() {
        return true;
    }


}
