package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;

public class TeleOpFlywheel extends Task {

    private Intake intake;
    private Transfer transfer;
    private Shooter shooter;

    public TeleOpFlywheel(Intake intake, Transfer transfer, Shooter shooter) {
        this.shooter = shooter;
        this.intake = intake;
        this.transfer = transfer;
    }

    @Override
    public void init() {
        if (this.shooter.getState() == Shooter.State.Rest) this.shooter.setState(Shooter.State.AdvancedTargettingCompensation);
        else this.shooter.setState(Shooter.State.Rest);
    }

    @Override
    public boolean finished() {
        return true;
    }
}
