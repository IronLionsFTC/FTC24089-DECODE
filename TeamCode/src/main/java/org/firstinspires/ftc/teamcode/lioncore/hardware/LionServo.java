package org.firstinspires.ftc.teamcode.lioncore.hardware;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LionServo {
    private Servo hardware;
    private Servo hardware2;
    private boolean hasSecond;
    private boolean mirror;
    private double position;
    private Timer timeSinceLastMovement;

    private LionServo(HardwareMap hwmp, String name) {
        this.timeSinceLastMovement = new Timer();
        this.hardware = hwmp.get(Servo.class, name);
        this.hasSecond = false;
        this.mirror = false;
        this.position = 0;
    }

    private void addServo(HardwareMap hwmp, String name, boolean mirror) {
        this.hardware2 = hwmp.get(Servo.class, name);
        this.hasSecond = true;
        this.mirror = true;
    }

    public static LionServo single(HardwareMap hwmp, String name, double startPosition) {
        LionServo servo = new LionServo(hwmp, name);
        servo.setPosition(startPosition);
        return servo;
    }

    public static LionServo mirrored(HardwareMap hwmp, String original, String reversed, double startPosition) {
        LionServo servo = new LionServo(hwmp, original);
        servo.addServo(hwmp, reversed, true);
        servo.setPosition(startPosition);
        return servo;
    }

    public void setPosition(double position) {
        if (this.position == position) return;
        this.timeSinceLastMovement.resetTimer();
        this.position = position;
        this.hardware.setPosition(this.position);
        if (this.hasSecond) {
            double other = this.mirror ? 1 - this.position : this.position;
            this.hardware2.setPosition(other);
        }
    }

    public double getPosition() {
        return this.position;
    }

    public double timeSinceLastMovement() {
        return this.timeSinceLastMovement.getElapsedTimeSeconds();
    }
}
