package org.firstinspires.ftc.teamcode.systems;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.lioncore.math.pid.PID;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.math.ProjectileMotion;
import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

import java.util.ArrayList;
import java.util.List;

public class Turret extends SystemBase {

    public enum State {
        Rest,
        Tracking,
        Shooting
    }

    @Config
    @Configurable
    public static class Constants {
        public static double tx = 12;
        public static double ty = 0;
        public static double tz = 30;
        public static double turretOverride = 0;
        public static double hoodAngleOverride = -1;
        public static double tP = 0.035;
        public static double tI = 0;
        public static double tD = 0.001;
    }

    // State
    private State state = State.Tracking;
    private Timer timer;
    private Timer deltaTime;

    // Hardware
    private LionMotor shooter;
    private LionMotor turret;
    private LionServo hood;

    // Odometry
    private GoBildaPinpointDriver pinpoint;
    private Vector3 lastPosition = new Vector3(0, 0, 0);
    List<Vector3> velocityBuffer = new ArrayList<>();

    // Control
    private PID turretController;
    private PID flywheelController;
    List<Double> rpmBuffer = new ArrayList<>();

    public Turret() {
        this.turretController = new PID(0.025, 0, 0.003);
        this.flywheelController = new PID(0.005, 0, 0);
    }

    @Override
    public void loadHardware(HardwareMap hardwareMap) {
        this.pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        this.shooter = LionMotor.masterSlaves(hardwareMap, Hardware.Motors.Names.shooter1, Hardware.Motors.Names.shooter2);
        this.turret = LionMotor.withEncoder(hardwareMap, Hardware.Motors.Names.turret);
        this.hood = LionServo.single(hardwareMap, Hardware.Servos.Names.hood, Hardware.Servos.ZeroPositions.hood);
    }

    @Override
    public void init() {
        this.timer = new Timer();
        this.deltaTime = new Timer();
        this.shooter.setReversed(Hardware.Motors.Reversed.shooter1, Hardware.Motors.Reversed.shooter2);
        this.shooter.setZPB(Hardware.Motors.ZPB.shooterMotors);
        this.turret.setReversed(Hardware.Motors.Reversed.turret);
        this.turret.setZPB(Hardware.Motors.ZPB.turretMotor);
        this.timer.resetTimer();
        this.deltaTime.resetTimer();
        this.turret.resetPosition();
        this.pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        this.relocalise();
    }

    @Override
    public void update(TelemetryManager telemetry) {

        // Tuning
        this.turretController.setConstants(
                Constants.tP,
                Constants.tI,
                Constants.tD
        );

        // Odometry
        pinpoint.update();
        Vector3 robotPosition = new Vector3(
            pinpoint.getPosX(DistanceUnit.INCH),
            pinpoint.getPosY(DistanceUnit.INCH),
            0
        );

        double currentHeading = pinpoint.getHeading(AngleUnit.DEGREES);
        Vector3 ds = robotPosition.sub(lastPosition);
        double dt = this.deltaTime.getElapsedTimeSeconds();
        Vector3 velocity = ds.multiply(1 / dt);
        this.velocityBuffer.add(velocity);
        if (this.velocityBuffer.size() > 5) this.velocityBuffer.remove(0);

        // RPM control
        double currentRPM = shooter.getVelocity(28);
        this.rpmBuffer.add(currentRPM);
        if (this.rpmBuffer.size() > 5) this.rpmBuffer.remove(0);

        double targetRPM;
        double hoodAngle; // [0, 1], 1 being VERTICAL and 0 being HORIZONTAL
        double azimuth;

        // State machine
        switch (this.state) {
            case Tracking: case Shooting:
                ProjectileMotion.Solution solution = ProjectileMotion.calculate(robotPosition, this.getVelocity(), new Vector3(
                       Constants.tx, Constants.ty, Constants.tz
                ));
                targetRPM = solution.targetRPM;
                hoodAngle = solution.hoodAngle;
                azimuth = solution.azimuthHeading - currentHeading;
                break;

            default:
                targetRPM = 0;
                hoodAngle = 1;
                azimuth = currentHeading;
                break;
        }

        if (Constants.turretOverride != 0) azimuth = Constants.turretOverride;
        if (Constants.hoodAngleOverride >= 0) hoodAngle = Constants.hoodAngleOverride;

        this.deltaTime.resetTimer();
        this.lastPosition = robotPosition;
        if (state == State.Tracking) targetRPM = 0;

        // Flywheel RPM PID
        double flywheelResponse = 0;
        if (targetRPM > 0) flywheelResponse = this.flywheelController.calculate(this.getRPM(), targetRPM);

        // Turret turn PID
        while (azimuth < -180) azimuth += 360;
        while (azimuth > 180) azimuth -= 360;
        double turretResponse = this.turretController.calculate(this.turret.getPosition() * 0.279642857, azimuth);

        // Hardware
        this.turret.setPower(turretResponse);
        this.shooter.setPower(flywheelResponse);
        this.hood.setPosition(hoodAngle * 0.56 + 0.01);

        // Telemetry
        telemetry.addData("Raw Turret Pos", this.turret.getPosition());
        telemetry.addData("Turret Pos", this.turret.getPosition() * 0.279642857);
        telemetry.addData("Azimuth", azimuth);
        telemetry.addData("RobotPosition", this.lastPosition.toString());
        telemetry.addData("RobotVelocity", this.getVelocity().toString());
        telemetry.addData("RPM", this.getRPM());

    }

    public Vector3 getVelocity() {
        if (this.velocityBuffer.isEmpty()) return new Vector3(0, 0, 0);
        Vector3 buffer = new Vector3(0, 0, 0);
        for (Vector3 instant : this.velocityBuffer) buffer.add(instant);
        return buffer.multiply((double)1/ this.velocityBuffer.size());
    }

    public double getRPM() {
        if (this.rpmBuffer.isEmpty()) return 0;
        double sum = 0;
        for (double instant : this.rpmBuffer) sum += instant;
        return sum / rpmBuffer.size();
    }

    public void relocalise() {
        this.pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
