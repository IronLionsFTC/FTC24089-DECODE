package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Run;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Drivebase;
import org.firstinspires.ftc.teamcode.systems.Feed;
import org.firstinspires.ftc.teamcode.systems.Turret;

@TeleOp
public class Teleop extends TaskOpMode {

    private Feed feed;
    private Turret turret;
    private Drivebase drivebase;

    public Jobs spawn() {

        this.drivebase = new Drivebase(
                controller1.leftJoystick::x,
                controller1.leftJoystick::y,
                controller1.rightJoystick::y
        );

        this.controller1.bumpers.left.onPress(
                new Run(turret::relocalise)
        );

        return Jobs.create()
                .registerSystem(feed)
                .registerSystem(turret)
                .registerSystem(drivebase);
    }
}
