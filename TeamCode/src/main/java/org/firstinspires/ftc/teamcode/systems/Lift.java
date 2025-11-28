package org.firstinspires.ftc.teamcode.systems;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

public class Lift extends SystemBase {
    private CRServo a;
    private CRServo b;
    private CRServo c;
    private CRServo d;

    public enum State {
        Unpowered,
        Retract,
        Lift
    }

    private State state = State.Unpowered;

    public Lift() {}

    @Override
    public void loadHardware(HardwareMap hardwareMap) {
        this.a = hardwareMap.get(CRServo.class, Hardware.Servos.Names.rtl);
        this.b = hardwareMap.get(CRServo.class, Hardware.Servos.Names.rbl);
        this.c = hardwareMap.get(CRServo.class, Hardware.Servos.Names.ltl);
        this.d = hardwareMap.get(CRServo.class, Hardware.Servos.Names.lbl);
    }

    @Config
    @Configurable
    public static class LiftTuning {
        public static double liftPower = 2;
    }

    @Override
    public void init() {

    }

    @Override
    public void update(TelemetryManager telemetry) {

        if (LiftTuning.liftPower <= 1) {
            this.setPower(LiftTuning.liftPower);
            return;
        }

        switch (this.state) {
            case Unpowered:
                this.setPower(0);
                break;
            case Retract:
                this.setPower(-0.1);
                break;
            case Lift:
                this.setPower(0.8);
                break;
        }
    }

    public void setPower(double power) {
        this.a.setPower(power);
        this.b.setPower(power);
        this.c.setPower(-power);
        this.d.setPower(-power);
    }

}
