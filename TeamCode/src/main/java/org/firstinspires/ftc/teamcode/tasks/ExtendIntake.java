package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.systems.IntakeSlides;

public class ExtendIntake extends TaskBase {
    private IntakeSlides slides;
    public ExtendIntake(IntakeSlides slides) {
        this.slides = slides;
    }

    public void init() {
        this.slides.setState(IntakeSlides.State.Extended);
    }

    public boolean finished() {
        return this.slides.getPosition() > Hardware.Ranges.Slides.intakeExtension * 0.95;
    }
}