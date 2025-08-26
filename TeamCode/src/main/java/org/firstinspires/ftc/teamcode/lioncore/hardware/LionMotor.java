package org.firstinspires.ftc.teamcode.lioncore.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

public class LionMotor {
    private List<DcMotor> motors;
    private double position;
    private double power;

    private LionMotor(HardwareMap hardwareMap, String... names) {
        this.motors = new ArrayList<>();
        for (String name : names) {
            this.motors.add(hardwareMap.get(DcMotor.class, name));
        }
        this.position = 0;
        this.power = 0;
    }

    public static LionMotor withoutEncoder(HardwareMap hardwareMap, String name) {
        return new LionMotor(hardwareMap, name);
    }

    public static LionMotor withEncoder(HardwareMap hardwareMap, String name) {
        return new LionMotor(hardwareMap, name);
    }

    /**
     * A LionMotor which operates multiple motors at once using the encoder of a singular motor.
     * @param hardwareMap The hardwaremap
     * @param names Any number of motors
     * @return
     */
    public static LionMotor masterSlaves(HardwareMap hardwareMap, String... names) {
        return new LionMotor(hardwareMap, names);
    }

    public void setPower(double power) {
        this.power = power;
        for (DcMotor motor : this.motors) {
            motor.setPower(power);
        }
    }

    public double getPower() {
        return this.power;
    }

    public double getPosition() {
        if (this.motors.isEmpty()) return 0.0;
        this.position = this.motors.get(0).getCurrentPosition();
        return this.cachedPosition();
    }

    public double cachedPosition() {
        return this.position;
    }

    public void resetPosition() {
        if (this.motors.isEmpty()) return;
        this.motors.get(0).setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motors.get(0).setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setReversed(boolean... reversed) {
        assert reversed.length == motors.size();
        for (int idx = 0; idx < this.motors.size(); idx++) {
            if (reversed[idx]) this.motors.get(idx).setDirection(DcMotorSimple.Direction.REVERSE);
            else this.motors.get(idx).setDirection(DcMotorSimple.Direction.FORWARD);
        }
    }

    public void setZPB(DcMotor.ZeroPowerBehavior zpb) {
        for (DcMotor motor : this.motors) {
            motor.setZeroPowerBehavior(zpb);
        }
    }
}
