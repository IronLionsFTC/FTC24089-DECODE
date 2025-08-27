package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Series;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.IntakeSlides;
import org.firstinspires.ftc.teamcode.tasks.ExtendIntake;
import org.firstinspires.ftc.teamcode.tasks.RetractIntake;

public class SlidesTest extends TaskOpMode {

    private IntakeSlides intake;

    public Jobs spawn() {
        intake = new IntakeSlides();

        return new Jobs(
                new Series(
                        new Sleep(1),
                        new ExtendIntake(intake),
                        new Sleep(1),
                        new RetractIntake(intake),
                        new Sleep(1)
                ),
                intake
        );
    }
}
