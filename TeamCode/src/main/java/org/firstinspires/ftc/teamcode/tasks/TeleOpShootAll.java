package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Transfer;

public class TeleOpShootAll extends Task {

    private Intake intake;
    private Transfer transfer;
    private Timer timer;

    public TeleOpShootAll(Intake intake, Transfer transfer) {
        this.intake = intake;
        this.transfer = transfer;
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
        return this.timer.getElapsedTimeSeconds() > 1;
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Zero);
        this.transfer.setState(Transfer.State.Rest);
    }

}
