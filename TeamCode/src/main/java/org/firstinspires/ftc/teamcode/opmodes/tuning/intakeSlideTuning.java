package org.firstinspires.ftc.teamcode.opmodes.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.math.pid.PID;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

@TeleOp
public class intakeSlideTuning extends OpMode {
    private LionMotor intakeSlideMotor;
    private PID motorController;

    @Config
    public static class IntakePIDTuning {
        public static double P = 0.0;
        public static double I = 0.0;
        public static double D = 0.0;
        public static double T = 0.0;
    }

    public void init() {
        intakeSlideMotor = LionMotor.withEncoder(hardwareMap, Hardware.Motors.Names.intake);
        intakeSlideMotor.resetPosition();
        motorController = new PID(IntakePIDTuning.P, IntakePIDTuning.I, IntakePIDTuning.D);
    }

    public void loop() {
        motorController.setConstants(IntakePIDTuning.P, IntakePIDTuning.I, IntakePIDTuning.D);
        intakeSlideMotor.setPower(motorController.calculate(intakeSlideMotor.getPosition(), IntakePIDTuning.T));
        telemetry.addData("Intake Position", intakeSlideMotor.cachedPosition());
        telemetry.addData("Intake Power", intakeSlideMotor.getPower());
        telemetry.update();
    }
}
