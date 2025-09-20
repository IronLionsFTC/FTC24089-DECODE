package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

public class Outtake extends SystemBase {

    private LionMotor motorPair;
    public Outtake() {}
    private boolean running;

    @Override
    public void loadHardware(HardwareMap hardwareMap) {
        this.motorPair = LionMotor.masterSlaves(hardwareMap, Hardware.Motors.Names.topShooter, Hardware.Motors.Names.bottomShooter);
        this.motorPair.setZPB(Hardware.Motors.ZPB.outtakeMotors);
        this.motorPair.setReversed(Hardware.Motors.Reversed.topShooter, Hardware.Motors.Reversed.bottomShooter);
    }

    @Override
    public void init() {
        this.motorPair.setPower(0);
        this.running = false;
    }

    @Override
    public void update(TelemetryManager telemetry) {
        if (this.running) this.motorPair.setPower(1);
        else this.motorPair.setPower(0);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return this.running;
    }
}
