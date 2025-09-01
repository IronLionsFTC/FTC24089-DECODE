package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.bylazar.gamepad.PanelsGamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

@TeleOp
public class linearTeleopDrive extends LinearOpMode {

    public void runOpMode() {

        Gamepad gamepad = PanelsGamepad.INSTANCE.getFirstManager().asCombinedFTCGamepad(gamepad1);

        LionMotor frontRight = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.frontRight);
        LionMotor frontLeft = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.frontLeft);
        LionMotor backRight = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.backRight);
        LionMotor backLeft = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.backLeft);
        frontRight.setReversed(Hardware.Motors.Reversed.frontRight);
        frontLeft.setReversed(Hardware.Motors.Reversed.frontLeft);
        backRight.setReversed(Hardware.Motors.Reversed.backRight);
        backLeft.setReversed(Hardware.Motors.Reversed.backLeft);
        frontRight.setZPB(Hardware.Motors.ZPB.driveMotors);
        frontLeft.setZPB(Hardware.Motors.ZPB.driveMotors);
        backRight.setZPB(Hardware.Motors.ZPB.driveMotors);
        backLeft.setZPB(Hardware.Motors.ZPB.driveMotors);

        DcMotor slideA = hardwareMap.get(DcMotor.class, "outtake1");
        DcMotor slideB = hardwareMap.get(DcMotor.class, "outtake2");
        slideA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideA.setZeroPowerBehavior(Hardware.Motors.ZPB.outtakeMotors);
        slideB.setZeroPowerBehavior(Hardware.Motors.ZPB.outtakeMotors);

        if (isStopRequested()) return;
        waitForStart();

        while (this.opModeIsActive()) {
            double cx = gamepad.left_stick_x;
            double cy = gamepad.left_stick_y;
            double cr = gamepad.right_stick_x;
            frontRight.setPower(cy + cx + cr);
            frontLeft.setPower(cy - cx - cr);
            backRight.setPower(cy - cx + cr);
            backLeft.setPower(cy + cx - cr);
            double sp = gamepad.right_trigger - gamepad.left_trigger;
            telemetry.addData("slidePower", sp);
            slideA.setPower(sp);
            slideB.setPower(sp);

            telemetry.addData("outtakePos", slideA.getCurrentPosition());
        }
    }
}
