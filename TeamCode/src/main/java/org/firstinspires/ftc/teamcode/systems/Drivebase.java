package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionMotor;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

import java.util.function.DoubleSupplier;

public class Drivebase extends SystemBase {

    private LionMotor frontRight;
    private LionMotor frontLeft;
    private LionMotor backRight;
    private LionMotor backLeft;
    private DoubleSupplier driveX;
    private DoubleSupplier driveY;
    private DoubleSupplier yawX;

    public Drivebase() {}

    public void loadHardware(HardwareMap hardwareMap) {
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

    public void init() {}

    public void update() {
        double cx = driveX.getAsDouble();
        double cy = driveY.getAsDouble();
        double cr = yawX.getAsDouble();
        frontRight.setPower(cy + cx + cr);
        frontLeft.setPower(cy - cx - cr);
        backRight.setPower(cy - cx + cr);
        backLeft.setPower(cy + cx - cr);
    }
}
