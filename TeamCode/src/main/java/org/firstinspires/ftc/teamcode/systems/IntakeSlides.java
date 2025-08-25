package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.math.pid.PID;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

public class IntakeSlides extends SystemBase {
    private IntakeSlides.State state;
    private LionMotor motor;
    private PID motorController;

    public enum State {
        Retracted,
        Extended
    }

    public IntakeSlides() {
        this.state = State.Retracted;
    }

    @Override
    public void loadHardware(HardwareMap hardwareMap) {
        this.motor = LionMotor.withEncoder(hardwareMap, Hardware.Motors.Names.intake);
        this.motor.setReversed(Hardware.Motors.Reversed.intake);
        this.motor.setZPB(Hardware.Motors.ZPB.intakeMotor);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }
}
