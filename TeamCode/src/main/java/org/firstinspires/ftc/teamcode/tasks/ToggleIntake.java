package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;

public class ToggleIntake extends Task {
    private Intake intake;
    private Transfer transfer;

    public ToggleIntake(Intake intake, Transfer transfer){
        this.intake = intake;
        this.transfer = transfer;
    }

    @Override
    public void init(){
        switch (this.intake.getState()) {
            case Positive:
                this.intake.setState(Intake.State.Zero);
                this.transfer.setState(Transfer.State.Rest);
                break;
            case Negative:
                this.intake.setState(Intake.State.Zero);
                this.transfer.setState(Transfer.State.Rest);
                break;
            case Zero:
                this.intake.setState(Intake.State.Positive);
                this.transfer.setState(Transfer.State.Intaking);
                break;
        }
    }

    @Override
    public boolean finished(){
        return true;
    }















}
