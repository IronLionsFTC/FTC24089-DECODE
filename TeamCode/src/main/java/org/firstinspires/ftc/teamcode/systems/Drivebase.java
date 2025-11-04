package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.math.types.Vector;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
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
    private GoBildaPinpointDriver pinpoint;

    private State state;

    public Drivebase(DoubleSupplier driveX, DoubleSupplier driveY, DoubleSupplier yaw) {
        this.driveX = driveX;
        this.driveY = driveY;
        this.yaw = yaw;
        this.state = State.Manual;
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
        this.pinpoint.setPosX(12, DistanceUnit.INCH);
        this.pinpoint.setPosY(0, DistanceUnit.INCH);
    }

    public void update(TelemetryManager telemetry) {

        double currentHeading = pinpoint.getHeading(AngleUnit.DEGREES);
        double currentX = pinpoint.getPosX(DistanceUnit.INCH);
        double currentY = pinpoint.getPosY(DistanceUnit.INCH);
        double targetHeading = Math.toDegrees(Math.atan2(-currentY, -currentX));

        telemetry.addData("CurrentX", currentX);
        telemetry.addData("CurrentY", currentY);
        telemetry.addData("CurrentHeading", currentHeading);
        telemetry.addData("TargetHeading", targetHeading);

        Vector drive = Vector.cartesian(this.driveX.getAsDouble(), this.driveY.getAsDouble());
        double yaw = this.yaw.getAsDouble() * 0.6;

        frontRight.setPower(drive.y() + drive.x() + yaw);
        frontLeft.setPower(drive.y() - drive.x() - yaw);
        backRight.setPower(drive.y() - drive.x() + yaw);
        backLeft.setPower(drive.y() + drive.x() - yaw);
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }
}
