package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

public class Transfer extends SystemBase {

    public enum State {
        Shooting,
        Reverse,
        Intaking,
        Rest
    }

    private LionMotor transferMotor;
    private LionServo transferServo;
    private State state;

    public Transfer() {

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
        double blockPosition = 0;

        switch (this.state) {
            case Rest:
                power = 0;
                break;
            case Reverse:
                power = -0.5;
                break;
            case Intaking:
                power = 0.5;
                break;
            case Shooting:
                power = 1;
                break;
        }
    }
}
