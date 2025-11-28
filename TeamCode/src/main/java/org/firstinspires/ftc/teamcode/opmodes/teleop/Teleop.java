package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Run;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Drivebase;
import org.firstinspires.ftc.teamcode.systems.Feed;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.Turret;
import org.firstinspires.ftc.teamcode.tasks.ShootForTime;
import org.firstinspires.ftc.teamcode.tasks.ToggleIntake;
import org.firstinspires.ftc.teamcode.tasks.ToggleShooterMode;

@TeleOp
public class Teleop extends TaskOpMode {

    private Feed feed;
    private Turret turret;
    private Drivebase drivebase;
    private Lift lift;

    public Jobs spawn() {

        this.lift = new Lift();
        this.feed = new Feed();
        this.turret = new Turret();
        this.drivebase = new Drivebase(
                controller1.leftJoystick::x,
                controller1.leftJoystick::y,
                controller1.rightJoystick::y
        );

        this.controller1.X.onPress(
                new ToggleIntake(feed)
        );

        this.controller1.rightTrigger.asButton.onPress(
                new ShootForTime(feed, turret, 1.5)
        );

        this.controller1.A.onPress(
                new ToggleShooterMode(turret)
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
