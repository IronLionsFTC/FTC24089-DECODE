package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

@TeleOp
public class RawShooterTest extends OpMode {

    private LionMotor motorA;
    private LionMotor motorB;

    public void init() {
        motorA = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.topShooter);
        motorB = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.bottomShooter);

        motorA.setZPB(Hardware.Motors.ZPB.shooterMotors);
        motorB.setZPB(Hardware.Motors.ZPB.shooterMotors);

        motorA.setReversed(Hardware.Motors.Reversed.topShooter);
        motorB.setReversed(Hardware.Motors.Reversed.bottomShooter);
    }

    public void loop() {
        motorA.setPower(1);
        motorB.setPower(1);
    }
}
