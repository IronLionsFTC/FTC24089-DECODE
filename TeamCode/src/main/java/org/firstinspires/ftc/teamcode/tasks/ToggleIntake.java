package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Intake;

public class ToggleIntake extends Task {
    private Intake intake;

    public ToggleIntake(Intake intake){
        this.intake = intake;
    }

    @Override
    public void init(){
        switch (this.intake.getState()) {
            case Positive:
                this.intake.setState(Intake.State.Zero);
                break;
            case Negative:
                this.intake.setState(Intake.State.Zero);
                break;
            case Zero:
                this.intake.setState(Intake.State.Positive);
                break;
        }
    }

    @Override
    public boolean finished(){
        return true;
    }

    













}
