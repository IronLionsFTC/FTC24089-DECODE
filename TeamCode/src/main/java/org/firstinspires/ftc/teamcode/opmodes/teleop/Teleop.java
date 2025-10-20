package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cache.Ordering;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
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

@TeleOp
public class Teleop extends TaskOpMode {

    private Drivebase drivebase;
    private Intake intake;
    private Shooter shooter;
    private Transfer transfer;
    private ColourChamber colourChamber;
    private Ordering ordering;

    public Jobs spawn() {
        this.intake = new Intake();
        this.shooter = new Shooter();
        this.transfer = new Transfer();
        this.colourChamber = new ColourChamber();
        this.ordering = new Ordering();

        this.drivebase = new Drivebase(
                controller1.leftJoystick::x,
                controller1.leftJoystick::y,
                controller1.rightJoystick::y
        );

        this.controller1.X.onPressToggle(
                new IntakeUntilFull(intake, transfer, colourChamber, ordering)
        );

        this.controller1.Y.onPress(
                new ReverseIntakeUntilEmpty(intake, transfer, colourChamber)
        );

        this.controller1.A.onPressToggle(
                new TeleOpFlywheel(intake, transfer, shooter)
        );

        this.controller1.rightTrigger.asButton.onPress(
                new TeleOpShootAll(intake, transfer, shooter)
        );

        this.controller1.leftTrigger.asButton.onPress(
                new TeleOpShootOne(intake, transfer)
        );

        this.controller1.dpad.up.onPress(
                new TeleOpToggleZone(shooter)
        );

        return Jobs.create()
                .registerSystem(drivebase)
                .registerSystem(shooter)
                .registerSystem(transfer)
                .registerSystem(colourChamber)
                .registerSystem(intake)

                .addTask(new SpinIntakeToKeepBallsIn(intake, colourChamber));

    }
}
