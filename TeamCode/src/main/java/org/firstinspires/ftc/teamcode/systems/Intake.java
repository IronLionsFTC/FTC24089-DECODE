package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

import java.util.function.BooleanSupplier;

public class Intake extends SystemBase {

    public enum State {
        Negative,
        Zero,
        Positive,
        Idle,
        CVAssist,
        Shooting
    }

    private LionMotor intakeMotor;
    private State state;
    private Limelight limelight;
    private BooleanSupplier farZone;

    public Intake() {
        this.state = State.Zero;
        this.farZone = () -> false;
    }

    public Intake(BooleanSupplier farZone) {
        this.state = State.Zero;
        this.farZone = farZone;
    }

    @Override
    public void loadHardware(HardwareMap hwmp) {
        this.intakeMotor = LionMotor.withoutEncoder(hwmp, Hardware.Motors.Names.intakeMotor);
        this.intakeMotor.setReversed(Hardware.Motors.Reversed.intakeMotor);
        this.intakeMotor.setZPB(Hardware.Motors.ZPB.intakeMotor);
        this.limelight = new Limelight(hwmp);
    }

    @Override
    public void init() {

    }

    @Override
    public void update(TelemetryManager telemetry) {

        double power = 0;

        switch (this.state) {
            case Zero:
                break;
            case Negative:
                power = -0.3;
                break;
            case Positive:
                power = 1;
                break;
            case Idle:
                power = 0.3;
                break;
            case Shooting:
                if (this.farZone.getAsBoolean()) power = 0.5;
                else power = 0.9;
                break;

        }
        this.intakeMotor.setPower(power);
    }

    public void setState(State state) {
        if (state == State.CVAssist) {
            this.limelight.start();
        } else {
            this.limelight.stop();
        }
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

}



