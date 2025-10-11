package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;

public class TeleOpShootOne extends Task {

    private Intake intake;
    private Transfer transfer;
    private Shooter shooter;
    private Timer timer;

    public TeleOpShootOne(Intake intake, Transfer transfer, Shooter shooter) {
        this.intake = intake;
        this.transfer = transfer;
        this.shooter = shooter;
        this.timer = new Timer();
    }

    @Override
    public void init() {
        this.intake.setState(Intake.State.Positive);
        this.transfer.setState(Transfer.State.Shooting);
        this.timer.resetTimer();
    }

    @Override
    public boolean finished() {
        return this.timer.getElapsedTimeSeconds() > 0.15;
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Idle);
        this.transfer.setState(Transfer.State.Queueing);
    }

}
