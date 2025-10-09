package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Intake;

public class RunIntake extends Task {
    private Intake intake;
    private Timer timer;
    private double duration;

    public RunIntake(Intake intake, double duration) {
        this.intake = intake;
        this.duration = duration;
        this.timer = new Timer();
    }

    @Override
    public void init() {
        this.timer.resetTimer();
        this.intake.setState(Intake.State.Positive);
    }

    @Override
    public boolean finished() {
        double currentTime = this.timer.getElapsedTimeSeconds();

        if (currentTime >= duration) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        this.intake.setState(Intake.State.Zero);
    }
}
