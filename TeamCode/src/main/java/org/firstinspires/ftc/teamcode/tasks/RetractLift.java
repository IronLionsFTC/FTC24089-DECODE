package org.firstinspires.ftc.teamcode.tasks;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Lift;

public class RetractLift extends Task {
    private Lift lift;
    private Timer timer;

    public RetractLift(Lift lift) {
        this.lift = lift;
        this.timer = new Timer();
    }

    @Override
    public void init() {
        this.timer.resetTimer();
        this.lift.setState(Lift.State.Retract);
    }

    @Override
    public boolean finished() {
        return this.timer.getElapsedTimeSeconds() > 0.3;
    }

    @Override
    public void end(boolean _) {
        this.lift.setState(Lift.State.Unpowered);
    }
}
