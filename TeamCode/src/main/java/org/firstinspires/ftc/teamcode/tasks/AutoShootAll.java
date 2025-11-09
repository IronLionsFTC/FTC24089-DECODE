package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;

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
        this.intake.setState(Intake.State.Shooting);
        this.transfer.setState(Transfer.State.ShootingSlower);
        this.timer.resetTimer();
        this.originalHood = this.shooter.getHoodAngle();
        this.shooter.setState(Shooter.State.AutoAimedFullPower);
    }

    @Override
    public boolean finished() {

        double time = timer.getElapsedTimeSeconds();
        return time > 1;
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Zero);
        this.transfer.setState(Transfer.State.Rest);
        this.shooter.setState(Shooter.State.AutoAimed);
        this.shooter.setHoodAngle(this.originalHood);
    }

}
