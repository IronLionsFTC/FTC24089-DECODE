package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.lioncore.math.pid.PID;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.parameters.Software;

import java.util.function.DoubleSupplier;

public class Shooter extends SystemBase {

    public enum State {
        Rest,
        Coast,
        Target,
        Compensate,
        AutoAimed,
        AutoAimedFullPower
    }

    private LionMotor shooter;
    private LionServo hood;
    private State state;
    private double targetRPM;
    private double nomalisedHoodAngle;

    private DoubleSupplier distanceOmeter;

    // PID controller
    private PID velocityController;
    private double rpm;

    public Shooter(DoubleSupplier distanceOmeter) {
        this.state = State.Rest;
        this.targetRPM = 3000;
        this.rpm = 0;
        this.nomalisedHoodAngle = 0;
        this.distanceOmeter = distanceOmeter;
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

        if (this.rpm > this.getTargetRPM() + 200 && this.state == State.Compensate) this.state = State.Target;

        // Update PID constants in case of tuning, costless
        this.velocityController.setConstants(
                Software.PID.VelocityController.P,
                Software.PID.VelocityController.I,
                Software.PID.VelocityController.D
        );

        this.rpm = this.shooter.getVelocity(28.0);

        double angle;
        double trpm;
        double distance = this.distanceOmeter.getAsDouble();

        if (this.state == State.Target || this.state == State.Compensate) {
            angle = this.nomalisedHoodAngle;
            trpm = this.getTargetRPM();
        }
        else if (this.state == State.AutoAimed || this.state == State.AutoAimedFullPower) {
            // Approximate the RPM and hood angle required at distance
            trpm = distance * 11.2 + 1980;

            // Hood angle
            angle = distance * 0.004;
        }
        else {
            angle = 0;
            trpm = 0;
        }

        // Adjustment for RPM error
        double absE = Math.abs(trpm - this.rpm);
        angle -= absE * 0.0005;
        if (angle < 0) angle = 0;

        double response = this.velocityController.calculate(
                this.rpm,
                trpm
        );

        if (trpm == 0) response = 0;

        if (response < 0) response *= 0.3;
        if ((this.state == State.Compensate || this.state == State.AutoAimedFullPower) && rpm < trpm - 100){
            response = 1;
        }

        if (this.state == State.Rest) response = 0;

        this.shooter.setPower(response);
        this.hood.setPosition(this.calculateHoodAngle(angle));

        telemetry.addData("POS", this.shooter.getPosition());
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

    public double getTargetRPM() {
        switch (this.state) {
            case Rest:
                return 0.0;
            case Coast:
                return Software.Constants.CruiseSpeed;
            case Target:
                return this.targetRPM + 200;
        }

        return 0.0;
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
}
