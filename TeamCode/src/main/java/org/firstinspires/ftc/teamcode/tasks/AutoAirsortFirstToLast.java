package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;

public class AutoAirsortFirstToLast extends Task {

    private Intake intake;
    private Transfer transfer;
    private Timer timer;
    private Shooter shooter;

    private double originalHood;

    public AutoAirsortFirstToLast(Intake intake, Transfer transfer, Shooter shooter) {
        this.intake = intake;
        this.transfer = transfer;
        this.shooter = shooter;
        this.timer = new Timer();
        this.originalHood = 0;
    }

    @Override
    public void init() {
        this.timer.resetTimer();
        this.shooter.airsortFactor = 0.5;
        this.shooter.speedFactor = 2.5;
        this.shooter.setState(Shooter.State.AdvancedTargetting);
        this.transfer.setState(Transfer.State.ShootingSlower);
        this.intake.setState(Intake.State.Shooting);
    }

    @Override
    public void run() {
        if (timer.getElapsedTimeSeconds() > 0.35) {
            this.intake.setState(Intake.State.Shooting);
            this.transfer.setState(Transfer.State.Rest);
            this.shooter.airsortFactor = 0;
            this.shooter.speedFactor = 1;
        } if (timer.getElapsedTimeSeconds() > 0.6) {
            this.transfer.setState(Transfer.State.ShootingSlower);
        }
    }

    @Override
    public boolean finished() {
        double time = timer.getElapsedTimeSeconds();
        return time > 2;
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Zero);
        this.transfer.setState(Transfer.State.Rest);
        this.shooter.setState(Shooter.State.AdvancedTargetting);
    }

}
