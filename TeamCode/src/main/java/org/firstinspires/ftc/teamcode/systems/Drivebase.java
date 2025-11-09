package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.math.pid.PID;
import org.firstinspires.ftc.teamcode.lioncore.math.types.Vector;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

import java.util.function.DoubleSupplier;

public class Drivebase extends SystemBase {

    public enum State {
        Manual,
        AutoAlign
    }

    private LionMotor frontRight;
    private LionMotor frontLeft;
    private LionMotor backRight;
    private LionMotor backLeft;

    private DoubleSupplier driveX;
    private DoubleSupplier driveY;
    private DoubleSupplier yaw;
    private DoubleSupplier azimuth = () -> 0;
    private GoBildaPinpointDriver pinpoint;
    private PID controller;

    private Vector3 position;
    private Vector3 velocity;
    private State state;

    public Drivebase(DoubleSupplier driveX, DoubleSupplier driveY, DoubleSupplier yaw) {
        this.driveX = driveX;
        this.driveY = driveY;
        this.yaw = yaw;
        this.state = State.Manual;
        this.controller = new PID(0.02, 0, 0.002);
    }

    public void setAzimuthSupplier(DoubleSupplier azimuth) {
        this.azimuth = azimuth;
    }

    public void loadHardware(HardwareMap hardwareMap) {
        this.pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        this.frontRight = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.frontRight);
        this.frontLeft = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.frontLeft);
        this.backRight = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.backRight);
        this.backLeft = LionMotor.withoutEncoder(hardwareMap, Hardware.Motors.Names.backLeft);
        this.frontRight.setReversed(Hardware.Motors.Reversed.frontRight);
        this.frontLeft.setReversed(Hardware.Motors.Reversed.frontLeft);
        this.backRight.setReversed(Hardware.Motors.Reversed.backRight);
        this.backLeft.setReversed(Hardware.Motors.Reversed.backLeft);
        this.frontRight.setZPB(Hardware.Motors.ZPB.driveMotors);
        this.frontLeft.setZPB(Hardware.Motors.ZPB.driveMotors);
        this.backRight.setZPB(Hardware.Motors.ZPB.driveMotors);
        this.backLeft.setZPB(Hardware.Motors.ZPB.driveMotors);
    }

    public void init() {
        this.pinpoint.setPosX(-18, DistanceUnit.INCH);
        this.pinpoint.setPosY(0, DistanceUnit.INCH);
        this.pinpoint.setHeading(0, AngleUnit.DEGREES);
    }

    public void update(TelemetryManager telemetry) {

        double currentHeading = pinpoint.getHeading(AngleUnit.DEGREES);
        double currentX = pinpoint.getPosX(DistanceUnit.INCH);
        double currentY = pinpoint.getPosY(DistanceUnit.INCH);
        double targetHeading = 90 - this.azimuth.getAsDouble();

        if (targetHeading < -180) { targetHeading += 360; }
        if (targetHeading > 180) { targetHeading -= 360; }

        telemetry.addData("CurrentX", currentX);
        telemetry.addData("CurrentY", currentY);
        telemetry.addData("CurrentHeading", currentHeading);
        telemetry.addData("TargetHeading", targetHeading);
        telemetry.addData("State", state);

        Vector drive = Vector.cartesian(this.driveX.getAsDouble(), this.driveY.getAsDouble());
        double response = -this.controller.calculate(currentHeading, targetHeading);

        double yaw;
        switch (this.state) {
            case Manual:
                yaw = this.yaw.getAsDouble() * 0.6;
                break;
            case AutoAlign:
                yaw = response;
                break;
            default:
                yaw = 0;
        }

        frontRight.setPower(drive.y() + drive.x() + yaw);
        frontLeft.setPower(drive.y() - drive.x() - yaw);
        backRight.setPower(drive.y() - drive.x() + yaw);
        backLeft.setPower(drive.y() + drive.x() - yaw);

        this.pinpoint.update();
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public double getDistance() {
        return Math.sqrt(
                Math.pow(this.pinpoint.getPosX(DistanceUnit.INCH), 2)
                + Math.pow(this.pinpoint.getPosY(DistanceUnit.INCH), 2)
        );
    }

    public Vector3.Vector3Supplier getPosition() { return () -> this.position; }
    public Vector3.Vector3Supplier getVelocity() { return () -> this.velocity; }
}
