package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
    public void update(TelemetryManager telemetry) {

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

        telemetry.addData("Current Pos", current);
        telemetry.addData("Current Pow", power);
        telemetry.addData("Current State", state);
    }

    // Getters & Setters (exterior mutability is confusing at scale)

    public State getState() { return this.state; }
    public void setState(State state) {
        this.state = state;
    }

    public double getPosition() {
        return this.motor.cachedPosition();
    }
    public void setPosition(double position) { Hardware.Ranges.Slides.intakeExtension = position; }
    public void resetPosition() { Hardware.Ranges.Slides.intakeExtension = Hardware.Ranges.Slides.intakeMaximumExtension; }
}
