package org.firstinspires.ftc.teamcode.lioncore.control;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.lioncore.math.types.Vector;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskBase;

import java.util.function.BooleanSupplier;

public class Controller {
    private Gamepad gamepad;
    public LeftJoystick leftJoystick;
    public RightJoystick rightJoystick;
    public Trackpad trackpad;

    public Button A;
    public Button B;
    public Button X;
    public Button Y;

    public Dpad dpad;
    public Bumpers bumpers;

    public class Bumpers {
        private Gamepad gamepad;

        public Button left;
        public Button right;

        private Bumpers(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.left = new Button(this::leftPressed);
            this.right = new Button(this::rightPressed);
        }

        private void update() {
            this.left.update();
            this.right.update();
        }

        private boolean leftPressed() { return this.gamepad.left_bumper; }
        private boolean rightPressed() { return this.gamepad.right_bumper; }
    }

    public class Dpad {
        private Gamepad gamepad;

        public Button up;
        public Button down;
        public Button left;
        public Button right;

        private Dpad(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.up = new Button(this::getUp);
            this.down = new Button(this::getDown);
            this.right = new Button(this::getRight);
            this.left = new Button(this::getLeft);
        }

        private void update() {
            this.up.update();
            this.down.update();
            this.right.update();
            this.left.update();
        }

        private boolean getUp() { return this.gamepad.dpad_up; }
        private boolean getDown() { return this.gamepad.dpad_down; }
        private boolean getRight() { return this.gamepad.dpad_right; }
        private boolean getLeft() { return this.gamepad.dpad_left; }
    }

    public class LeftJoystick {
        private Gamepad gamepad;
        public Button button;

        private LeftJoystick(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.button = new Button(this::pressed);
        }

        public double x() { return this.gamepad.left_stick_x; }
        public double y() { return this.gamepad.left_stick_y; }
        private boolean pressed() { return this.gamepad.left_stick_button; }
        private void update() { this.button.update(); }
    }

    public class RightJoystick {
        private Gamepad gamepad;
        public Button button;

        private RightJoystick(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.button = new Button(this::pressed);
        }

        public double x() { return this.gamepad.right_stick_x; }
        public double y() { return this.gamepad.right_stick_y; }
        private boolean pressed() { return this.gamepad.right_stick_button; }
        private void update() { this.button.update(); }
    }

    public class Trackpad {
        private Gamepad gamepad;
        private Button button;

        private Trackpad(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.button = new Button(this::pressed);
        }

        private void update() {
            this.button.update();
        }

        public Vector position() {
            return Vector.cartesian(this.gamepad.touchpad_finger_1_x, this.gamepad.touchpad_finger_1_y);
        }

        private boolean pressed() {
            return this.gamepad.touchpad;
        }
    }

    public class Button {
        private BooleanSupplier button;
        private boolean last;
        private boolean current;

        private boolean hasOnPress = false;
        private boolean cancelOnRelease = false;
        private TaskBase onPress;
        private boolean onPressRunning = false;

        private boolean hasOnRelease = false;
        private TaskBase onRelease;
        private boolean onReleaseRunning = false;

        private Button(BooleanSupplier button) {
            this.button = button;
        }

        public void update() {
            this.last = this.current;
            this.current = this.button.getAsBoolean();

            // If it was just turned on (schedule task)
            if (!this.last && this.current && this.hasOnPress) {
                if (this.onPressRunning) this.onPress.end(true);
                this.onPress.init();
            }

            if (this.last && !this.current && this.cancelOnRelease && this.hasOnPress && this.onPressRunning) {
                if (!this.onPress.finished()) {
                    this.onPress.end(true);
                }
                this.onPressRunning = false;
            }

            if (this.last && !this.current && this.hasOnRelease) {
                if (this.onReleaseRunning) this.onRelease.end(true);
                this.onRelease.init();
            }

            if (this.hasOnPress && this.onPressRunning) {
                this.onPress.run();
                if (this.onPress.finished()) {
                    this.onPress.end(false);
                    this.onPressRunning = false;
                }
            }

            if (this.hasOnRelease && this.onReleaseRunning) {
                this.onRelease.run();
                if (this.onRelease.finished()) {
                    this.onRelease.end(false);
                    this.onReleaseRunning = false;
                }
            }
        }

        /**
         * Schedules a task that is init when the button is pressed and run forever until it finishes.
         * @param task
         */
        public void onPress(TaskBase task) {
            this.onPress = task;
            this.hasOnPress = true;
        }

        /**
         * Schedules a task that is init when the button is pressed and runs until either the button is released or it finishes.
         * @param task
         */
        public void whilePressed(TaskBase task) {
            this.onPress = task;
            this.hasOnPress = true;
            this.cancelOnRelease = true;
        }

        /**
         * Schedules a task that is init when the button is released and runs forever until it finishes.
         * @param task
         */
        public void onRelease(TaskBase task) {
            this.onRelease = task;
            this.hasOnRelease = true;
        }

        public boolean pressed() { return this.current; }
    }

    public Controller(Gamepad gamepad) {
        this.gamepad = gamepad;
        this.leftJoystick = new LeftJoystick(this.gamepad);
        this.rightJoystick = new RightJoystick(this.gamepad);
        this.trackpad = new Trackpad(this.gamepad);
        this.dpad = new Dpad(this.gamepad);
        this.bumpers = new Bumpers(this.gamepad);
    }

    public void update() {
        this.dpad.update();
        this.leftJoystick.update();
        this.rightJoystick.update();
        this.bumpers.update();
        this.trackpad.update();
    }

    private boolean a() { return this.gamepad.a; }
    private boolean b() { return this.gamepad.b; }
    private boolean x() { return this.gamepad.x; }
    private boolean y() { return this.gamepad.y; }
}
