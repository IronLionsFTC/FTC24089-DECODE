package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Drivebase;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;

@TeleOp
public class Teleop extends TaskOpMode {

    private Drivebase drivebase;
    private Intake intake;
    private Shooter shooter;
    private Transfer transfer;

    public Jobs spawn() {
        this.intake = new Intake();
        this.shooter = new Shooter();
        this.transfer = new Transfer();

        this.drivebase = new Drivebase(
                controller1.leftJoystick::x,
                controller1.leftJoystick::y,
                controller1.rightJoystick::y
        );

        return Jobs.create()
                .registerSystem(drivebase)
                .registerSystem(shooter)
                .registerSystem(transfer)
                .registerSystem(intake);

    }
}
