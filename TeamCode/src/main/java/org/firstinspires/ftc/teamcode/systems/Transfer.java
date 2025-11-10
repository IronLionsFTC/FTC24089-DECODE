package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.parameters.Software;

import java.util.function.BooleanSupplier;

public class Transfer extends SystemBase {

    public enum State {
        Shooting,
        ShootingSlower,
        Reverse,
        Intaking,
        Queueing,
        Rest
    }

    private LionMotor transferMotor;
    private LionServo transferServo;
    private BooleanSupplier longShot;
    private State state;

    public Transfer(BooleanSupplier longShot) {
        this.longShot = longShot;
    }

    public Transfer() {
        this.longShot = () -> false;
    }

    @Override
    public void loadHardware(HardwareMap hwmp) {
        this.state = State.Rest;
        this.transferMotor = LionMotor.withoutEncoder(hwmp, Hardware.Motors.Names.transferMotor);
        this.transferMotor.setReversed(Hardware.Motors.Reversed.transferMotor);
        this.transferMotor.setZPB(Hardware.Motors.ZPB.transferMotor);
        this.transferServo = LionServo.mirrored(
                hwmp,
                Hardware.Servos.Names.leftBlock,
                Hardware.Servos.Names.rightBlock,
                Hardware.Servos.ZeroPositions.blockPosition
        );
    }

    @Override
    public void init() {}

    @Override
    public void update(TelemetryManager telemetry) {
        double power = 0;
        double blockPosition = Hardware.Servos.ZeroPositions.blockPosition;

        switch (this.state) {
            case Reverse:
                power = -0.5;
                blockPosition = Software.Constants.Unblock;
                break;
            case Intaking:
                power = 0.5;
                break;
            case Shooting:
                power = 1;
                blockPosition = Software.Constants.Unblock;
                break;
            case ShootingSlower:
                if (longShot.getAsBoolean()) power = 0.35;
                else power = 0.6;
                blockPosition = Software.Constants.Unblock;
                break;
            case Queueing:
                power = 0.6;
                break;
        }

        this.transferMotor.setPower(power);
        this.transferServo.setPosition(blockPosition);
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public double timeSinceOpening() {
        if (this.state != State.Shooting) { return 0; }
        return this.transferServo.timeSinceLastMovement();
    }
}
