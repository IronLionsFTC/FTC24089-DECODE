package org.firstinspires.ftc.teamcode.lioncore.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LionMotor {
    private DcMotor motor;
    private double position;
    private double power;
    private boolean hasEncoder;

    private LionMotor(HardwareMap hardwareMap, String name, boolean hasEncoder) {
        this.motor = hardwareMap.get(DcMotor.class, name);
        this.position = 0;
        this.power = 0;
        this.hasEncoder = hasEncoder;
    }

    public static LionMotor withoutEncoder(HardwareMap hardwareMap, String name) {
        return new LionMotor(hardwareMap, name, false);
    }

    public static LionMotor withEncoder(HardwareMap hardwareMap, String name) {
        return new LionMotor(hardwareMap, name, true);
    }

    public void setPower(double power) {
        this.power = power;
        this.motor.setPower(power);
    }

    public double getPower() {
        return this.power;
    }

    public double getPosition() {
        if (this.hasEncoder) {
            return this.motor.getCurrentPosition();
        } else {
            return 0.0;
        }
    }

    public void resetPosition() {
        if (this.hasEncoder) {
            this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public void setReversed(boolean reversed) {
        if (reversed) this.motor.setDirection(DcMotorSimple.Direction.REVERSE);
        else this.motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setZPB(DcMotor.ZeroPowerBehavior zpb) {
        this.motor.setZeroPowerBehavior(zpb);
    }
}
