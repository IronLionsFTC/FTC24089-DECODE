package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.lioncore.math.pid.PID;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.parameters.Software;

public class Shooter extends SystemBase {

    public enum State {
        Rest,
        Coast,
        Target
    }

    private LionMotor shooter;
    private LionServo hood;
    private State state;
    private double targetRPM;
    private double nomalisedHoodAngle;

    // PID controller
    private PID velocityController;
    private double rpm;

    public Shooter() {
        this.state = State.Rest;
        this.targetRPM = 3000;
        this.rpm = 0;
        this.nomalisedHoodAngle = 0;
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

        double response = this.velocityController.calculate(
                this.rpm,
                this.getTargetRPM()
        );

        this.shooter.setPower(response);
        this.hood.setPosition(Hardware.Servos.ZeroPositions.hood + this.nomalisedHoodAngle * (Software.Constants.HoodMax - Hardware.Servos.ZeroPositions.hood));

        telemetry.addData("POS", this.shooter.getPosition());
        telemetry.addData("RPM", this.rpm);
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
                return this.targetRPM;
        }

        return 0.0;
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

    public double getHoodAngle(double hoodAngle) {
        return this.nomalisedHoodAngle;
    }
}
