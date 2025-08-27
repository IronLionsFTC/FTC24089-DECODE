package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

public class OuttakeSlides extends SystemBase {

    // Motors - A owns the encoder
    private LionMotor motorA;
    private LionMotor motorB;

    public OuttakeSlides() {}

    public void loadHardware(HardwareMap hardwareMap) {
        this.motorA = LionMotor.withEncoder(hardwareMap, Hardware.Motors.Names.outtake1);
        this.motorB = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.outtake2);
        this.motorA.setReversed(Hardware.Motors.Reversed.outtake1);
        this.motorB.setReversed(Hardware.Motors.Reversed.outtake2);
        this.motorA.setZPB(Hardware.Motors.ZPB.outtakeMotors);
        this.motorB.setZPB(Hardware.Motors.ZPB.outtakeMotors);
    }

    public void init() {
        this.motorA.resetPosition();
    }

    public void update(Telemetry telemetry) {

    }
}
