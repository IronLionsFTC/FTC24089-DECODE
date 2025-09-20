package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Drivebase;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.tasks.ToggleIntake;

@TeleOp
public class Teleop extends TaskOpMode {

    private Drivebase drivebase;
    private Intake intake;

    public Jobs spawn() {

        this.drivebase = new Drivebase(
                controller1.leftJoystick::x,
                controller1.leftJoystick::y,
                controller1.rightJoystick::y
        );

        this.intake = new Intake();

        this.controller1.X.onPress(new ToggleIntake(this.intake));

        return Jobs.create()
                .registerSystem(intake)
                .registerSystem(drivebase);
    }
}
