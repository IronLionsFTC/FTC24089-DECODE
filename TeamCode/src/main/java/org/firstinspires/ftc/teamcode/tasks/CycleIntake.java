package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.IntakeSlides;

public class CycleIntake extends Task {
    private IntakeSlides intake;

    public CycleIntake(IntakeSlides intake) {
        this.intake = intake;
    }

    public void init() {
        switch (intake.getState()) {
            case Retracted:
                this.intake.setState(IntakeSlides.State.Extended);
                break;
            case Extended:
                this.intake.setState(IntakeSlides.State.Retracted);
                break;
        }
    }
}
