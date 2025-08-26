package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Forever;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Optional;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Parallel;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Run;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Drivebase;
import org.firstinspires.ftc.teamcode.systems.IntakeSlides;
import org.firstinspires.ftc.teamcode.tasks.ExtendIntake;

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

        controller1.bumpers.right.onPress(
                new ExtendIntake(intakeSlides)
        );

        this.intakeSlides = new IntakeSlides();

        return new Jobs(
                new Forever(this.telemetry::update),
                drivebase,
                intakeSlides
        );
    }
}
