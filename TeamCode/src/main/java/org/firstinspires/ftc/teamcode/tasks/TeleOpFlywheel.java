package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;

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
        this.shooter.setState(Shooter.State.AdvancedTargettingCompensation);
    }

    @Override
    public void end(boolean _) {
        this.shooter.setState(Shooter.State.Rest);
        this.intake.setState(Intake.State.Zero);
        this.transfer.setState(Transfer.State.Rest);
    }
}
