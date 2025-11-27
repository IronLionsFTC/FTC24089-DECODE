package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.parameters.Software;

public class Feed extends SystemBase {

    public enum State {
        Rest,
        Slow,
        Full,
        Eject
    }

    // State
    private State state = State.Rest;
    private Timer timer;

    // Associated motors / servos
    LionMotor feedMotor;
    LionServo block;

    public Feed() {}

    @Override
    public void loadHardware(HardwareMap hardwareMap) {
        this.feedMotor = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.feed);
        this.block = LionServo.mirrored(hardwareMap, Hardware.Servos.Names.leftBlock, Hardware.Servos.Names.rightBlock, Hardware.Servos.ZeroPositions.blockPosition);
    }

    @Override
    public void init() {
        this.timer = new Timer();
        this.timer.resetTimer();
        this.feedMotor.setReversed(Hardware.Motors.Reversed.feed);
        this.feedMotor.setZPB(Hardware.Motors.ZPB.transferMotor);
    }

    @Override
    public void update(TelemetryManager telemetry) {

         // Apply power based on state
        switch (this.state) {

            case Rest:
                this.feedMotor.setPower(0);
                this.block.setPosition(Hardware.Servos.ZeroPositions.blockPosition);
                break;

            case Slow:
                this.feedMotor.setPower(0.7);
                this.block.setPosition(Software.Constants.Unblock);
                break;

            case Full:
                this.feedMotor.setPower(1);
                this.block.setPosition(Software.Constants.Unblock);
                break;

            case Eject:
                this.feedMotor.setPower(-1);
                this.block.setPosition(Hardware.Servos.ZeroPositions.blockPosition);
                break;
        }
    }

    public State getState() { return this.state; }
    public double getTimeInState() { return this.timer.getElapsedTimeSeconds(); }

    public void setState(State state) {
        this.state = state;
        this.timer.resetTimer();
    }
}
