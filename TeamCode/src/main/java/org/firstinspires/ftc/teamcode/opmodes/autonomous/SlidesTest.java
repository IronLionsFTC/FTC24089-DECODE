package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Repeat;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Series;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.IntakeSlides;
import org.firstinspires.ftc.teamcode.tasks.ExtendIntake;
import org.firstinspires.ftc.teamcode.tasks.RetractIntake;

@Autonomous
public class SlidesTest extends TaskOpMode {

    private IntakeSlides intake;

    public Jobs spawn() {
        intake = new IntakeSlides();

        return Jobs.create()

                // Register any subsystems used in this OpMode.
                .registerSystem(intake)

                // Add a singular task (a series that repeats forever) to the OpMode.
                .addTask(
                    new Repeat(
                        new Series(
                            new Sleep(1),
                            new ExtendIntake(intake),
                            new Sleep(1),
                            new RetractIntake(intake)
                        ),
                3
                    )
                )

                // Tell the OpMode it should end when the task has finished (which is never in this example).
                .endWhenTasksFinished();
    }
}
