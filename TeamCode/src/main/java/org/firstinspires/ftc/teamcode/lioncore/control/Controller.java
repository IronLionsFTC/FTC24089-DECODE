package org.firstinspires.ftc.teamcode.lioncore.control;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.lioncore.math.types.Vector;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;

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
            this.left = new Button();
            this.right = new Button();
        }

        private void update(Gamepad gamepad) {
            this.left.update(gamepad.left_bumper);
            this.right.update(gamepad.right_bumper);
        }
    }

    public class Dpad {
        private Gamepad gamepad;

        public Button up;
        public Button down;
        public Button left;
        public Button right;

        private Dpad(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.up = new Button();
            this.down = new Button();
            this.right = new Button();
            this.left = new Button();
        }

        private void update(Gamepad gamepad) {
            this.up.update(gamepad.dpad_up);
            this.down.update(gamepad.dpad_down);
            this.right.update(gamepad.dpad_right);
            this.left.update(gamepad.dpad_left);
        }
    }

    public class LeftJoystick {
        private Gamepad gamepad;
        public Button button;

        private LeftJoystick(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.button = new Button();
        }

        public double x() { return this.gamepad.left_stick_x; }
        public double y() { return this.gamepad.left_stick_y; }

        private void update(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.button.update(gamepad.left_stick_button);
        }
    }

    public class RightJoystick {
        private Gamepad gamepad;
        public Button button;

        private RightJoystick(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.button = new Button();
        }

        public double x() { return this.gamepad.right_stick_x; }
        public double y() { return this.gamepad.right_stick_y; }

        private void update(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.button.update(gamepad.right_stick_button);
        }
    }

    public class Trackpad {
        private Gamepad gamepad;
        private Button button;

        private Trackpad(Gamepad gamepad) {
            this.gamepad = gamepad;
            this.button = new Button();
        }

        private void update(Gamepad gamepad) {
            this.button.update(gamepad.touchpad);
        }

        public Vector position() {
            return Vector.cartesian(this.gamepad.touchpad_finger_1_x, this.gamepad.touchpad_finger_1_y);
        }

        private boolean pressed() {
            return this.gamepad.touchpad;
        }
    }

    public class Button {
        private boolean last;
        private boolean current;

        private boolean hasOnPress = false;
        private boolean cancelOnRelease = false;
        private Task onPress;
        private boolean onPressRunning = false;

        private boolean hasOnRelease = false;
        private Task onRelease;
        private boolean onReleaseRunning = false;

        private Button() { }

        public void update(boolean button) {
            this.last = this.current;
            this.current = button;

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
        public void onPress(Task task) {
            this.onPress = task;
            this.hasOnPress = true;
        }

        /**
         * Schedules a task that is init when the button is pressed and runs until either the button is released or it finishes.
         * @param task
         */
        public void whilePressed(Task task) {
            this.onPress = task;
            this.hasOnPress = true;
            this.cancelOnRelease = true;
        }

        /**
         * Schedules a task that is init when the button is released and runs forever until it finishes.
         * @param task
         */
        public void onRelease(Task task) {
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
        this.A = new Button();
        this.B = new Button();
        this.X = new Button();
        this.Y = new Button();
    }

    /**
     * Poll the controller for all buttons. This must be called once per loop cycle.
     */
    public void update(Gamepad gamepad) {
        this.dpad.update(gamepad);
        this.leftJoystick.update(gamepad);
        this.rightJoystick.update(gamepad);
        this.bumpers.update(gamepad);
        this.trackpad.update(gamepad);
        this.A.update(gamepad.a);
        this.B.update(gamepad.b);
        this.X.update(gamepad.x);
        this.Y.update(gamepad.y);
    }
}
