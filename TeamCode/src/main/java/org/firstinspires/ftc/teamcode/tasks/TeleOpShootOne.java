package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;

public class TeleOpShootOne extends Task {

    private Intake intake;
    private Transfer transfer;
    private Timer timer;

    public TeleOpShootOne(Intake intake, Transfer transfer) {
        this.intake = intake;
        this.transfer = transfer;
        this.timer = new Timer();
    }

    @Override
    public void init() {
        this.intake.setState(Intake.State.Positive);
        this.transfer.setState(Transfer.State.ShootingSlower);
        this.timer.resetTimer();
    }

    @Override
    public boolean finished() {
        return this.timer.getElapsedTimeSeconds() > 0.1;
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Idle);
        this.transfer.setState(Transfer.State.Queueing);
    }

}
