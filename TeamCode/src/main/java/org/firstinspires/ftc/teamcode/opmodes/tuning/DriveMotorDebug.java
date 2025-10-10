package org.firstinspires.ftc.teamcode.opmodes.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

@TeleOp
public class DriveMotorDebug extends OpMode {

    private LionMotor frontLeft;
    private LionMotor frontRight;
    private LionMotor backLeft;
    private LionMotor backRight;

    @Config
    @Configurable
    public static class DriveMotorDebugPowers {
        public static double frontLeft = 0;
        public static double frontRight = 0;
        public static double backLeft = 0;
        public static double backRight = 0;
    }

    @Override
    public void init() {
        this.frontLeft = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.frontLeft);
        this.frontLeft.setReversed(Hardware.Motors.Reversed.frontLeft);
        this.frontLeft.setZPB(Hardware.Motors.ZPB.driveMotors);

        this.frontRight = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.frontRight);
        this.frontRight.setReversed(Hardware.Motors.Reversed.frontRight);
        this.frontRight.setZPB(Hardware.Motors.ZPB.driveMotors);

        this.backLeft = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.backLeft);
        this.backLeft.setReversed(Hardware.Motors.Reversed.backLeft);
        this.backLeft.setZPB(Hardware.Motors.ZPB.driveMotors);

        this.backRight = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.backRight);
        this.backRight.setReversed(Hardware.Motors.Reversed.backRight);
        this.backRight.setZPB(Hardware.Motors.ZPB.driveMotors);
    }

    @Override
    public void loop() {
        this.frontLeft.setPower(DriveMotorDebugPowers.frontLeft);
        this.frontRight.setPower(DriveMotorDebugPowers.frontRight);
        this.backLeft.setPower(DriveMotorDebugPowers.backLeft);
        this.backRight.setPower(DriveMotorDebugPowers.backRight);
    }

}
