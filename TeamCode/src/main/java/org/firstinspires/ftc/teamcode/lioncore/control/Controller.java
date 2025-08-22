package org.firstinspires.ftc.teamcode.lioncore.control;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.lioncore.math.types.Vector;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskBase;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class Controller {
    private Gamepad gamepad;
    public LeftJoystick leftJoystick;
    public RightJoystick rightJoystick;
    public Trackpad trackpad;

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

        public Vector position() {
            return Vector.cartesian(this.gamepad.touchpad_finger_1_x, this.gamepad.touchpad_finger_1_y);
        }

        public boolean pressed() {
            return this.gamepad.touchpad;
        }
    }

    public class Button {
        private BooleanSupplier button;
        private boolean last;
        private boolean current;

        private boolean hasOnPress = false;
        private boolean stopOnRelease = false;
        private TaskBase onPress;

        private boolean hasOnRelease = false;
        private TaskBase onRelease;

        private Button(BooleanSupplier button) {
            this.button = button;
        }

        public void update() {
            this.last = this.current;
            this.current = this.button.getAsBoolean();
        }

        public boolean pressed() { return this.current; }
    }

    public Controller(Gamepad gamepad) {
        this.gamepad = gamepad;
        this.leftJoystick = new LeftJoystick(gamepad);
        this.rightJoystick = new RightJoystick(gamepad);
        this.trackpad = new Trackpad(gamepad);
    }

    public void update() {

    }

    private boolean a() { return this.gamepad.a; }
    private boolean b() { return this.gamepad.b; }
    private boolean x() { return this.gamepad.x; }
    private boolean y() { return this.gamepad.y; }

    private boolean up() { return this.gamepad.dpad_up; }
    private boolean down() { return this.gamepad.dpad_up; }
    private boolean right() { return this.gamepad.dpad_up; }
    private boolean left() { return this.gamepad.dpad_up; }

    private boolean leftBumper() { return this.gamepad.left_bumper; }
    private boolean rightBumper() { return this.gamepad.right_bumper; }
}
