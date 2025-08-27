package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskBase;
import org.firstinspires.ftc.teamcode.systems.IntakeSlides;

public class CycleIntake extends TaskBase {
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
