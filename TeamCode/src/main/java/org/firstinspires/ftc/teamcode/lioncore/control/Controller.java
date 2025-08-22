package org.firstinspires.ftc.teamcode.lioncore.control;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.DoubleSupplier;

public class Controller {
    private Gamepad gamepad;
    public LeftJoystick leftJoystick;
    public RightJoystick rightJoystick;

    public class LeftJoystick {
        private Gamepad gamepad;

        private LeftJoystick(Gamepad gamepad) {
            this.gamepad = gamepad;
        }

        public double x() { return this.gamepad.left_stick_x; }
        public double y() { return this.gamepad.left_stick_y; }
    }

    public class RightJoystick {
        private Gamepad gamepad;

        private RightJoystick(Gamepad gamepad) {
            this.gamepad = gamepad;
        }

        public double x() { return this.gamepad.right_stick_x; }
        public double y() { return this.gamepad.right_stick_y; }
    }

    public class Trackpad {
        private Gamepad gamepad;

        private Trackpad(Gamepad gamepad) {
            this.gamepad = gamepad;
        }
    }

    public Controller(Gamepad gamepad) {
        this.gamepad = gamepad;
        this.leftJoystick = new LeftJoystick(gamepad);
        this.rightJoystick = new RightJoystick(gamepad);
    }

    public boolean a() { return this.gamepad.a; }
    public boolean b() { return this.gamepad.b; }
    public boolean x() { return this.gamepad.x; }
    public boolean y() { return this.gamepad.y; }

    public boolean up() { return this.gamepad.dpad_up; }
    public boolean down() { return this.gamepad.dpad_up; }
    public boolean right() { return this.gamepad.dpad_up; }
    public boolean left() { return this.gamepad.dpad_up; }
}
