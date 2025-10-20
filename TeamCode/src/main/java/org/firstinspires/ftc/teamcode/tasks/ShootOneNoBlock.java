package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;

public class ShootOneNoBlock extends Task {

    private Intake intake;
    private Transfer transfer;
    private Shooter shooter;

    double targetRPM;
    double angle;

    Timer timer;

    public ShootOneNoBlock(Intake intake, Transfer transfer, Shooter shooter, double targetRPM, double angle) {
        this.intake = intake;
        this.transfer = transfer;
        this.shooter = shooter;
        this.targetRPM = targetRPM;
        this.angle = angle;
        this.timer = new Timer();
    }

    @Override
    public void init() {
        this.intake.setState(Intake.State.Positive);
        this.transfer.setState(Transfer.State.Shooting);
        this.shooter.setTargetRPM(this.targetRPM);
        this.shooter.setHoodAngle(this.angle);
        this.shooter.setState(Shooter.State.Compensate);
        this.timer.resetTimer();
    }

    @Override
    public boolean finished() {
        return (this.timer.getElapsedTimeSeconds() > 0.09);
    }
}
