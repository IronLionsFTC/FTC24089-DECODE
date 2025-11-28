package org.firstinspires.ftc.teamcode.opmodes.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

@TeleOp
public class ServoTuningOpmode extends OpMode {
    private LionServo block;

    @Config
    @Configurable
    public static class ServoPositions {
        public static double blockPosition = Hardware.Servos.ZeroPositions.blockPosition;
    }

    @Override
    public void init() {
        block = LionServo.mirrored(hardwareMap, Hardware.Servos.Names.leftBlock, Hardware.Servos.Names.rightBlock, Hardware.Servos.ZeroPositions.blockPosition);
    }

    @Override
    public void loop() {
        block.setPosition(ServoPositions.blockPosition);
    }
}
