package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.lioncore.math.pid.PID;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.math.BallisticsSolver;
import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.parameters.Software;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

public class Shooter extends SystemBase {

    public enum State {
        Rest,
        AdvancedTargetting,
        AdvancedTargettingCompensation
    }

    public double airsortFactor = 1;

    private LionMotor shooter;
    private LionServo hood;
    private State state;
    private double targetRPM;
    private double nomalisedHoodAngle;
    private Vector3 target;

    private DoubleSupplier distanceOmeter;

    private Vector3.Vector3Supplier robotPosition = () -> new Vector3(0, 0, 0);
    private Vector3.Vector3Supplier robotVelocity = () -> new Vector3(0, 0, 0);
    private boolean flatShot = false;
    private double lastAzimuth = 0;
    private double loopCount = 0;

    // PID controller
    private PID velocityController;
    private double rpm;

    List<Double> rpmBuf = new ArrayList<>();

    public Shooter(DoubleSupplier distanceOmeter) {
        this.state = State.Rest;
        this.targetRPM = 3000;
        this.rpm = 0;
        this.nomalisedHoodAngle = 0;
        this.distanceOmeter = distanceOmeter;
        this.target = new Vector3(0, 0, 40);
    }

    public Shooter(Vector3.Vector3Supplier p, Vector3.Vector3Supplier v) {
        this.state = State.Rest;
        this.targetRPM = 3000;
        this.rpm = 0;
        this.nomalisedHoodAngle = 0;
        this.robotPosition = p;
        this.robotVelocity = v;
        this.distanceOmeter = () -> this.robotPosition.get().sub(this.robotVelocity.get()).length();
        this.target = new Vector3(0, 0, 40);
    }

    public Shooter(Vector3.Vector3Supplier p, Vector3.Vector3Supplier v, Vector3 target) {
        this.state = State.Rest;
        this.targetRPM = 3000;
        this.rpm = 0;
        this.nomalisedHoodAngle = 0;
        this.robotPosition = p;
        this.robotVelocity = v;
        this.distanceOmeter = () -> this.robotPosition.get().sub(this.robotVelocity.get()).length();
        this.target = target;
    }

    @Override
    public void loadHardware(HardwareMap hwmp) {
        this.shooter = LionMotor.masterSlaves(hwmp, Hardware.Motors.Names.topShooter, Hardware.Motors.Names.bottomShooter);
        this.shooter.setReversed(Hardware.Motors.Reversed.topShooter, Hardware.Motors.Reversed.bottomShooter);
        this.shooter.setZPB(Hardware.Motors.ZPB.shooterMotors);
        this.hood = LionServo.single(hwmp, Hardware.Servos.Names.shooterHood, Hardware.Servos.ZeroPositions.hood);
    }

    @Override
    public void init() {
        this.shooter.resetPosition();
        this.velocityController = new PID(
                Software.PID.VelocityController.P,
                Software.PID.VelocityController.I,
                Software.PID.VelocityController.D
        );
    }

    @Override
    public void update(TelemetryManager telemetry) {

        // Update PID constants in case of tuning, costless
        this.velocityController.setConstants(
                Software.PID.VelocityController.P,
                Software.PID.VelocityController.I,
                Software.PID.VelocityController.D
        );

        this.rpm = this.shooter.getVelocity(28.0);
        this.rpmBuf.add(this.rpm);

        double angle;
        double trpm;
        double distance = this.distanceOmeter.getAsDouble();

        // Approximate the RPM and hood angle required at distance
        trpm = distance * 11.7 + 1850;
        // 0.004
        angle = distance * 0.0063 / airsortFactor;

        loopCount += 1;

        if (loopCount < 3) {
            loopCount += 1;
            return;
        }

        loopCount = 0;

        Vector3 pos = this.robotPosition.get();
        Vector3 vel = this.robotVelocity.get();

        Vector3 r = this.target.sub(pos);
        Vector3 rHat = r.normalize();
        double velocityToward = vel.dot(rHat);
        trpm -= velocityToward * 19.8;

        if (this.rpmBuf.size() > 5) this.rpmBuf.remove(0);

        double dy = target.y - (robotPosition.get().y + robotVelocity.get().y * 0.5);
        double dx = target.x - (robotPosition.get().x + robotVelocity.get().x * 0.5);

        this.lastAzimuth = Math.toDegrees(Math.atan2(dy, dx));
        double altitude = 0;

        telemetry.addData("ROBOT POSITION", robotPosition.get());
        telemetry.addData("ROBOT VELOCITY", robotVelocity.get());
        telemetry.addData("TARGET ANGLE", altitude);

        trpm *= airsortFactor;

        double response = this.velocityController.calculate(
                this.rpm,
                trpm
        );

        if (trpm == 0) response = 0;

        if (response < 0) response *= 0.2;

        if (this.state == State.Rest) response = 0;


        if (this.state == State.AdvancedTargettingCompensation && rpm < trpm-50){
            response = 1;
        }

        this.shooter.setPower(response);
        if (this.state == State.Rest) this.hood.setPosition(this.calculateHoodAngle(0));
        else this.hood.setPosition(this.calculateHoodAngle(angle));

        telemetry.addData("RPM", this.rpm);
    }

    private double calculateHoodAngle(double angle) {
        return Hardware.Servos.ZeroPositions.hood
                + angle
                * (Software.Constants.HoodMax - Hardware.Servos.ZeroPositions.hood);
    }

    public void setTargetRPM(double targetRPM) {
        this.targetRPM = targetRPM;
    }
    public double getRPM() {
        return this.rpm;
    }
    public void setState(State state) {
        this.state = state;
    }
    public State getState() {
        return this.state;
    }
    public void setHoodAngle(double normalisedAngle) {
        this.nomalisedHoodAngle = normalisedAngle;
    }
    public double getHoodAngle() {
        return this.nomalisedHoodAngle;
    }
    public double getRawTargetRPM() {
        return this.targetRPM;
    }

    public void setFlatShot(boolean flatShot) { this.flatShot = flatShot; }
    public boolean getFlatShot() { return this.flatShot;}

    private double mapElevationToDouble(double elevationDeg) {
        double angleMin = 35.0;
        double angleMax = 60.0;
        return (angleMax - elevationDeg) / (angleMax - angleMin);
    }

    public double yieldAzimuth() {
        return this.lastAzimuth;
    }
}
