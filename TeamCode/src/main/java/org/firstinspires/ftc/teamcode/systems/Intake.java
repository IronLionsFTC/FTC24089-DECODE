package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

public class Intake extends SystemBase {

    public enum State {
        Negative,
        Zero,
        Positive,
        Idle,
        CVAssist
    }

    private LionMotor intakeMotor;
    private State state;
    private Limelight limelight;

    public Intake() {
        this.state = State.Zero;
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



