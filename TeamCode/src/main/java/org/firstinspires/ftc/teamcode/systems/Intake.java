package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

public class Intake extends SystemBase {

    private LionMotor motor;

    public Intake() {}

    public void loadHardware(HardwareMap hardwareMap) {
        this.motor = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.intake);
    }

    public void init() {
        this.motor.setPower(0);
        this.motor.setZPB(Hardware.Motors.ZPB.intakeMotor);
        this.motor.setReversed(Hardware.Motors.Reversed.intake);
    }

    public void update(TelemetryManager telemetry) {

    }
}
