package org.firstinspires.ftc.teamcode.tobycode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.parameters.Hardware;


@TeleOp
public class TobyTeleOp extends LinearOpMode {
    private LionMotor lf;
    private LionMotor rf;
    private  LionMotor lb;
    private LionMotor rb;


    @Override
    public void runOpMode() {

        LionMotor rf = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.frontRight);
        LionMotor lf = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.frontLeft);
        LionMotor rb = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.backRight);
        LionMotor lb = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.backLeft);

        LionMotor sm = LionMotor.masterSlaves(hardwareMap, Hardware.Motors.Names.outtake1, Hardware.Motors.Names.outtake2);

        rb.setZPB(Hardware.Motors.ZPB.driveMotors);
        lb.setZPB(Hardware.Motors.ZPB.driveMotors);
        lf.setZPB(Hardware.Motors.ZPB.driveMotors);
        rf.setZPB(Hardware.Motors.ZPB.driveMotors);

        sm.setZPB(Hardware.Motors.ZPB.outtakeMotors);
        sm.resetPosition();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double z = gamepad1.right_stick_x;

            lf.setPower (-z+x-y);
            rf.setPower(-z+x+y);
            lb.setPower(-z-x-y);
            rb.setPower(-z-x+y);

            sm.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

            telemetry.addData("Status", "Running");
            telemetry.addData("Slide Position", sm.getPosition());
            telemetry.update();
        }
    }
}
