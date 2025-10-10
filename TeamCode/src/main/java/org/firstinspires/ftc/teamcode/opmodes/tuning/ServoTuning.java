package org.firstinspires.ftc.teamcode.opmodes.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

@TeleOp(name = "SERVO TUNING OPMODE")
public class ServoTuning extends OpMode {

    // Servos to block the ball from entering flywheel
    private LionServo leftBlock;
    private LionServo rightBlock;

    // Limelight pitch servo
    private LionServo limelight;

    // Hood Servo
    private LionServo hoodServo;

    // Class to hold the servo positions
    @Config
    @Configurable
    public static class ServoPositions {
        public static double leftBlockPosition = Hardware.Servos.ZeroPositions.blockPosition;
        public static double rightBlockPosition = 1 - Hardware.Servos.ZeroPositions.blockPosition;
        public static double limelightPosition = Hardware.Servos.ZeroPositions.limelight;
        public static double hoodServoPosition = Hardware.Servos.ZeroPositions.hood;
    }

    @Override
    public void init() {

        // Load the servos from HardwareMap
        this.leftBlock = LionServo.single(
                hardwareMap,
                Hardware.Servos.Names.leftBlock,
                Hardware.Servos.ZeroPositions.blockPosition
        );

        this.rightBlock = LionServo.single(
                hardwareMap,
                Hardware.Servos.Names.rightBlock,
                1 - Hardware.Servos.ZeroPositions.blockPosition
        );

        this.limelight = LionServo.single(hardwareMap, Hardware.Servos.Names.limelightServo, Hardware.Servos.ZeroPositions.limelight);
        this.hoodServo = LionServo.single(hardwareMap, Hardware.Servos.Names.shooterHood, Hardware.Servos.ZeroPositions.hood);

    }

    @Override
    public void loop() {

        this.leftBlock.setPosition(ServoPositions.leftBlockPosition);
        this.rightBlock.setPosition(ServoPositions.rightBlockPosition);
        this.limelight.setPosition(ServoPositions.limelightPosition);
        this.hoodServo.setPosition(ServoPositions.hoodServoPosition);

    }
}
