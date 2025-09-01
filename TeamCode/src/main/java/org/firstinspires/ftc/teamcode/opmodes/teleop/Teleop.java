package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Forever;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Run;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Drivebase;
import org.firstinspires.ftc.teamcode.systems.IntakeSlides;
import org.firstinspires.ftc.teamcode.tasks.CycleIntake;

@TeleOp
public class Teleop extends TaskOpMode {

    private SystemBase drivebase;
    private IntakeSlides intakeSlides;

    public Jobs spawn() {

        this.drivebase = new Drivebase(
                controller1.leftJoystick::x,
                controller1.leftJoystick::y,
                controller1.rightJoystick::y
        );

        this.intakeSlides = new IntakeSlides();

        // Cycle the intake when 'X' is pressed
        controller1.X.onPress(
            new CycleIntake(this.intakeSlides)
        );

        return Jobs.create()
                .addTask(
                        new Forever(
                            new Run(() -> panelsTelemetry.addData("UPDATE", intakeSlides.getPosition())).then(
                                new Sleep(1)
                            )
                        )
                )
                .registerSystem(drivebase)
                .registerSystem(intakeSlides);
    }
}
