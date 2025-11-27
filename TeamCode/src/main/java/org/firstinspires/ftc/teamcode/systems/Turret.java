package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.lioncore.math.pid.PID;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

public class Turret extends SystemBase {

    public enum State {
        Rest,
        Tracking,
        Shooting
    }

    // State
    private State state = State.Rest;
    private Timer timer;

    // Hardware
    private LionMotor shooter;
    private LionMotor turret;
    private LionServo hood;

    // Odometry
    private GoBildaPinpointDriver pinpoint;

    // Control
    private PID turretController;
    private PID flywheelController;

    public Turret() {}

    @Override
    public void loadHardware(HardwareMap hardwareMap) {
        this.shooter = LionMotor.masterSlaves(hardwareMap, Hardware.Motors.Names.shooter1, Hardware.Motors.Names.shooter2);
        this.turret = LionMotor.withEncoder(hardwareMap, Hardware.Motors.Names.turret);
        this.hood = LionServo.single(hardwareMap, Hardware.Servos.Names.hood, Hardware.Servos.ZeroPositions.hood);
    }

    @Override
    public void init() {
        this.shooter.setReversed(Hardware.Motors.Reversed.shooter1, Hardware.Motors.Reversed.shooter2);
        this.shooter.setZPB(Hardware.Motors.ZPB.shooterMotors);
        this.turret.setReversed(Hardware.Motors.Reversed.turret);
        this.turret.setZPB(Hardware.Motors.ZPB.turretMotor);
        this.timer.resetTimer();
    }

    @Override
    public void update(TelemetryManager telemetry) {

        double targetRPM;
        double hoodAngle; // [0, 1], 1 being VERTICAL and 0 being HORIZONTAL

        // State machine
        switch (this.state) {
            case Rest:
                targetRPM = 0;
                hoodAngle = 1;
                break;

            case Tracking: case Shooting:
                targetRPM =
        }

    }
}
