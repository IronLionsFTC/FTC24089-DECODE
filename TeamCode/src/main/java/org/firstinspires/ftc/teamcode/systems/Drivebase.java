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

    private Vector3 position = new Vector3(0, 0, 0);
    private Vector3 velocity = new Vector3(0, 0, 0);
    private Vector3 previousPosition = new Vector3(0, 0, 0);
    private long previousTime = 0; // milliseconds
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
        this.previousTime = System.currentTimeMillis();
    }

    public void update(TelemetryManager telemetry) {

        this.pinpoint.update();

        double currentHeading = pinpoint.getHeading(AngleUnit.DEGREES);
        double currentX = pinpoint.getPosX(DistanceUnit.INCH);
        double currentY = pinpoint.getPosY(DistanceUnit.INCH);

        this.position = new Vector3(currentX, currentY, 0);

        // --- Compute velocity from difference in position ---
        long currentTime = System.currentTimeMillis();
        double deltaTime = (currentTime - previousTime) / 1000.0; // seconds

        if (deltaTime > 0) {
            double velX = (position.x - previousPosition.x) / deltaTime;
            double velY = (position.y - previousPosition.y) / deltaTime;
            this.velocity = new Vector3(velX, velY, 0);
        }

        // Save current state for next update
        previousPosition = new Vector3(position.x, position.y, position.z);
        previousTime = currentTime;

        double targetHeading = this.azimuth.getAsDouble();
        if (targetHeading < -180) targetHeading += 360;
        if (targetHeading > 180) targetHeading -= 360;

        telemetry.addData("CurrentX", currentX);
        telemetry.addData("CurrentY", currentY);
        telemetry.addData("CurrentHeading", currentHeading);
        telemetry.addData("TargetHeading", targetHeading);
        telemetry.addData("State", state);
        telemetry.addData("VelocityX", velocity.x);
        telemetry.addData("VelocityY", velocity.y);

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
    }

    public void setState(State state) { this.state = state; }
    public State getState() { return this.state; }

    public double getDistance() {
        return Math.sqrt(Math.pow(this.position.x, 2) + Math.pow(this.position.y, 2));
    }

    public Vector3 getPosition() { return this.position; }
    public Vector3 getVelocity() { return this.velocity; }
}
