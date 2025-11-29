package org.firstinspires.ftc.teamcode.systems;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
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
        Eject,
        Shooting
    }

    // State
    private State state = State.Rest;
    private Timer timer;

    // Associated motors / servos
    LionMotor feedMotor;
    LionServo block;

    @Config
    @Configurable
    public static class FeedConstants {
        public static double power = -2;
        public static double blockPosition = -3;
    }

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

        boolean override = false;

        if (FeedConstants.blockPosition > 0) {
            this.block.setPosition(FeedConstants.blockPosition);
            override = true;
        }

        if (FeedConstants.power > -2) {
            this.feedMotor.setPower(FeedConstants.power);
            override = true;
        }

        if (override) return;

         // Apply power based on state
        switch (this.state) {

            case Rest:
                this.feedMotor.setPower(0);
                this.block.setPosition(Hardware.Servos.ZeroPositions.blockPosition);
                break;

            case Slow:
                this.feedMotor.setPower(0.7);
                this.block.setPosition(Hardware.Servos.ZeroPositions.blockPosition);
                break;

            case Full:
                this.feedMotor.setPower(1);
                this.block.setPosition(Hardware.Servos.ZeroPositions.blockPosition);
                break;

            case Eject:
                this.feedMotor.setPower(-1);
                this.block.setPosition(Hardware.Servos.ZeroPositions.blockPosition);
                break;

            case Shooting:
                this.feedMotor.setPower(0.5);
                this.block.setPosition(Software.Constants.Unblock);
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
