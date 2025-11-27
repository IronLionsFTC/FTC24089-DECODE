package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Drivebase;

public class TeleOpShootAll extends Task {

    private Intake intake;
    private Transfer transfer;
    private Timer timer;
    private Shooter shooter;
    private Drivebase dt;

    private double originalHood;

    public TeleOpShootAll(Intake intake, Transfer transfer, Shooter shooter, Drivebase dt) {
        this.intake = intake;
        this.transfer = transfer;
        this.shooter = shooter;
        this.timer = new Timer();
        this.originalHood = 0;
        this.dt = dt;
    }

    @Override
    public void init() {
        this.intake.setState(Intake.State.Shooting);
        this.transfer.setState(Transfer.State.ShootingSlower);
        this.timer.resetTimer();
        this.originalHood = this.shooter.getHoodAngle();
        this.shooter.setState(Shooter.State.AdvancedTargetting);
    }

    @Override
    public boolean finished() {

        double time = timer.getElapsedTimeSeconds();
        return time > 1.2;
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Positive);
        this.transfer.setState(Transfer.State.Rest);
        this.shooter.setState(Shooter.State.Rest);
        this.shooter.setHoodAngle(this.originalHood);
        this.dt.setState(Drivebase.State.Manual);
    }

}
