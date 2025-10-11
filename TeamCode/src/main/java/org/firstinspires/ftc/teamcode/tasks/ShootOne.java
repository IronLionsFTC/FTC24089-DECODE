package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;

public class ShootOne extends Task {

    private Intake intake;
    private Transfer transfer;
    private Shooter shooter;

    double targetRPM;
    double angle;

    // State of task
    boolean begunShooting = false;

    public ShootOne(Intake intake, Transfer transfer, Shooter shooter, double targetRPM, double angle) {
        this.intake = intake;
        this.transfer = transfer;
        this.shooter = shooter;
        this.targetRPM = targetRPM;
        this.angle = angle;
    }

    @Override
    public void init() {
        this.intake.setState(Intake.State.Idle);
        this.transfer.setState(Transfer.State.Rest);

        this.shooter.setTargetRPM(this.targetRPM);
        this.shooter.setHoodAngle(this.angle);
        this.shooter.setState(Shooter.State.Target);
    }

    @Override
    public void run() {
        if (this.shooter.getRPM() > this.shooter.getTargetRPM() * 0.9 && !this.begunShooting) {
            this.begunShooting = true;
            this.transfer.setState(Transfer.State.Shooting);
        }
    }

    @Override
    public boolean finished() {
        return (this.transfer.timeSinceOpening() > 0.3);
    }

    @Override
    public void end(boolean _) {
        this.transfer.setState(Transfer.State.Rest);
    }
}
