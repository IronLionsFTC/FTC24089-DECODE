package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

@TeleOp
public class linearTeleopDrive extends LinearOpMode {
    public void runOpMode() {
        LionMotor frontRight = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.frontRight);
        LionMotor frontLeft = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.frontLeft);
        LionMotor backRight = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.backRight);
        LionMotor backLeft = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.backLeft);

        frontLeft.setReversed(Hardware.Motors.Reversed.frontLeft);
        backLeft.setReversed(Hardware.Motors.Reversed.backLeft);

        if (isStopRequested()) return;
        waitForStart();

        while (this.opModeIsActive()) {
            double cx = gamepad1.left_stick_x;
            double cy = gamepad1.left_stick_y;
            double cr = gamepad1.right_stick_x;
            frontRight.setPower(cy + cx + cr);
            frontLeft.setPower(cy - cx - cr);
            backRight.setPower(cy - cx + cr);
            backLeft.setPower(cy + cx - cr);
        }
    }
}
