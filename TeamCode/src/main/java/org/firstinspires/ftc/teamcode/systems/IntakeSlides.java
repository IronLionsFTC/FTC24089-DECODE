package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.math.pid.PID;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.parameters.Software;

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
        this.motorController = new PID(
                Software.PID.Intake.P,
                Software.PID.Intake.I,
                Software.PID.Intake.D
        );
    }

    @Override
    public void loadHardware(HardwareMap hardwareMap) {
        this.motor = LionMotor.withEncoder(hardwareMap, Hardware.Motors.Names.intake);
        this.motor.setReversed(Hardware.Motors.Reversed.intake);
        this.motor.setZPB(Hardware.Motors.ZPB.intakeMotor);
    }

    @Override
    public void init() {
        this.motor.resetPosition();
    }

    @Override
    public void update() {

        this.motorController.setConstants(
                Software.PID.Intake.P,
                Software.PID.Intake.I,
                Software.PID.Intake.D
        );

        double target;
        double current = this.motor.getPosition();

        switch (this.state) {
            case Retracted:
                target = 0.0;
                break;
            case Extended:
                target = Hardware.Ranges.Slides.intakeExtension;
                break;
            default:
                target = 0.0;
                break;
        }

        double power = this.motorController.calculate(current, target);
        this.motor.setPower(power);
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getPosition() {
        return this.motor.cachedPosition();
    }

    public boolean isAtTarget() {
        return this.motor.cachedPosition() > 0.95 * Hardware.Ranges.Slides.intakeExtension;
    }
}
