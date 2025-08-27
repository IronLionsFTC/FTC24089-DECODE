package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskBase;
import org.firstinspires.ftc.teamcode.systems.IntakeSlides;

public class RetractIntake extends TaskBase {
    private IntakeSlides intakeSlides;
    public RetractIntake(IntakeSlides intakeSlides) {
        this.intakeSlides = intakeSlides;
    }

    public void init() {
        this.intakeSlides.setState(IntakeSlides.State.Retracted);
    }

    public boolean finished() {
        return this.intakeSlides.getPosition() < 10;
    }
}
