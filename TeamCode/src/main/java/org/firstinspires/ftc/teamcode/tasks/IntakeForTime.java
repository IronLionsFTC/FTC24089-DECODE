package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;

public class IntakeForTime extends Task {

    private Intake intake;
    private Transfer transfer;
    private Timer timer;
    private double time;

    public IntakeForTime(Intake intake, Transfer transfer, double time) {
        this.intake = intake;
        this.transfer = transfer;
        this.timer = new Timer();
        this.time = time;
    }

    @Override
    public void init() {
        this.intake.setState(Intake.State.Positive);
        this.transfer.setState(Transfer.State.Intaking);
        this.timer.resetTimer();
    }

    @Override
    public boolean finished() {
        double ctime = timer.getElapsedTimeSeconds();
        return ctime > time;
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Zero);
        this.transfer.setState(Transfer.State.Rest);
    }

}
