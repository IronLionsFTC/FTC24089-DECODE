package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cache.Ordering;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Run;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.Drivebase;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;
import org.firstinspires.ftc.teamcode.tasks.IntakeUntilFull;
import org.firstinspires.ftc.teamcode.tasks.ReverseIntakeUntilEmpty;
import org.firstinspires.ftc.teamcode.tasks.SpinIntakeToKeepBallsIn;
import org.firstinspires.ftc.teamcode.tasks.TeleOpFlywheel;
import org.firstinspires.ftc.teamcode.tasks.TeleOpShootAll;
import org.firstinspires.ftc.teamcode.tasks.TeleOpShootOne;
import org.firstinspires.ftc.teamcode.tasks.TeleOpToggleZone;
import org.firstinspires.ftc.teamcode.tasks.ToggleDrivebaseMode;
import org.firstinspires.ftc.teamcode.tasks.ToggleIntake;
import org.firstinspires.ftc.teamcode.tasks.TogglePower;

@TeleOp
public class Teleop extends TaskOpMode {

    private Drivebase drivebase;
    private Intake intake;
    private Shooter shooter;
    private Transfer transfer;

    public Jobs spawn() {

        this.drivebase = new Drivebase(
                controller1.leftJoystick::x,
                controller1.leftJoystick::y,
                controller1.rightJoystick::y
        );

        this.shooter = new Shooter(drivebase::getPosition, drivebase::getVelocity);
        this.shooter.speedFactor = 0.96; // start slower
        this.drivebase.setAzimuthSupplier(this.shooter::yieldAzimuth);
        this.transfer = new Transfer(() -> drivebase.getDistance() > 70);
        this.intake = new Intake(() -> drivebase.getDistance() > 70);

        this.controller1.X.onPressToggle(
                new ToggleIntake(intake, transfer)
        );

        this.controller1.A.onPress(
                new TeleOpFlywheel(intake, transfer, shooter)
        );

        this.controller1.rightTrigger.asButton.onPress(
                new TeleOpShootAll(intake, transfer, shooter, drivebase)
        );

        this.controller1.leftTrigger.asButton.onPress(
                new TeleOpShootOne(intake, transfer)
        );

        this.controller1.dpad.up.onPress(
                new TeleOpToggleZone(shooter)
        );

        this.controller1.bumpers.right.onPress(
                new ToggleDrivebaseMode(drivebase)
        );

        this.controller1.bumpers.left.onPress(
                new Run(drivebase::init)
        );

        this.controller1.dpad.right.onPress(
                new TogglePower(shooter)
        );

        return Jobs.create()
                .registerSystem(drivebase)
                .registerSystem(shooter)
                .registerSystem(transfer)
                .registerSystem(intake);

    }
}
