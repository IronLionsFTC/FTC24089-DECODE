package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;

public class AutoShootAll extends Task {

    private Intake intake;
    private Transfer transfer;
    private Timer timer;
    private Shooter shooter;

    private double originalHood;

    public AutoShootAll(Intake intake, Transfer transfer, Shooter shooter) {
        this.intake = intake;
        this.transfer = transfer;
        this.shooter = shooter;
        this.timer = new Timer();
        this.originalHood = 0;
    }

    @Override
    public void init() {
        this.timer.resetTimer();
        this.shooter.setState(Shooter.State.AdvancedTargetting);
    }

    @Override
    public void run() {
        if (timer.getElapsedTimeSeconds() > 0.5) {
            this.intake.setState(Intake.State.Shooting);
            this.transfer.setState(Transfer.State.ShootingSlower);
            this.shooter.setState(Shooter.State.AdvancedTargettingCompensation);
        }
    }

    @Override
    public boolean finished() {
        double time = timer.getElapsedTimeSeconds();
        return time > 1.5;
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Zero);
        this.transfer.setState(Transfer.State.Rest);
        this.shooter.setState(Shooter.State.AdvancedTargetting);
    }

}
