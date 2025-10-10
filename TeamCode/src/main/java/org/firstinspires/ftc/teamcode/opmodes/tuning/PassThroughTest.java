package org.firstinspires.ftc.teamcode.opmodes.tuning;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.parameters.Software;

@TeleOp
public class PassThroughTest extends OpMode {

    private LionMotor intake;
    private LionMotor transfer;
    private LionMotor outtake;
    private LionServo block;
    private LionServo hood;
    private Timer timer;

    @Override
    public void init() {
        this.intake = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.intakeMotor);
        this.transfer = LionMotor.withEncoder(hardwareMap, Hardware.Motors.Names.transferMotor);
        this.outtake = LionMotor.masterSlaves(
                hardwareMap,
                Hardware.Motors.Names.topShooter,
                Hardware.Motors.Names.bottomShooter
        );

        this.intake.setReversed(Hardware.Motors.Reversed.intakeMotor);
        this.transfer.setReversed(Hardware.Motors.Reversed.transferMotor);
        this.outtake.setReversed(Hardware.Motors.Reversed.topShooter, Hardware.Motors.Reversed.bottomShooter);

        this.intake.setZPB(Hardware.Motors.ZPB.intakeMotor);
        this.transfer.setZPB(Hardware.Motors.ZPB.transferMotor);
        this.outtake.setZPB(Hardware.Motors.ZPB.shooterMotors);

        this.block = LionServo.mirrored(hardwareMap, Hardware.Servos.Names.leftBlock, Hardware.Servos.Names.rightBlock, Hardware.Servos.ZeroPositions.blockPosition);
        this.timer = new Timer();
        this.timer.resetTimer();

        this.hood = LionServo.single(hardwareMap, Hardware.Servos.Names.shooterHood, Hardware.Servos.ZeroPositions.hood);
    }

    @Override
    public void loop() {
        this.intake.setPower(1);
        this.transfer.setPower(1);
        this.outtake.setPower(1);

        double currentTime = this.timer.getElapsedTimeSeconds();

        if (currentTime > 8 && currentTime < 8.5) {
            this.block.setPosition(Software.Constants.Unblock);
            this.hood.setPosition(Hardware.Servos.ZeroPositions.hood);
        } else if (currentTime < 12) {
            this.block.setPosition(Hardware.Servos.ZeroPositions.blockPosition);
            this.hood.setPosition(Software.Constants.HoodMax);
        } else {
            this.hood.setPosition(Software.Constants.HoodMax);
            this.block.setPosition(Software.Constants.Unblock);
        }
    }
}
