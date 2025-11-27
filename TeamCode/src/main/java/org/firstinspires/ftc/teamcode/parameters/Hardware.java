package org.firstinspires.ftc.teamcode.parameters;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Hardware {
    public static class Motors {
        public static class Names {
            public static String frontRight = "rightFront";
            public static String frontLeft = "leftFront";
            public static String backRight = "rightRear";
            public static String backLeft = "leftRear";
            public static String shooter1 = "shooter1";
            public static String shooter2 = "shooter2";
            public static String feed = "transfer";
            public static String turret = "turret";
        }

        public static class Reversed {
            public static boolean frontRight = true;
            public static boolean frontLeft = false;
            public static boolean backRight = true;
            public static boolean backLeft = false;

            // Ensure these are opposing as motors are geared together.
            public static boolean shooter1 = false;
            public static boolean shooter2 = true;

            public static boolean feed = false;
            public static boolean turret = false;
        }

        public static class ZPB {
            public static DcMotor.ZeroPowerBehavior driveMotors = DcMotor.ZeroPowerBehavior.BRAKE;
            public static DcMotor.ZeroPowerBehavior shooterMotors = DcMotor.ZeroPowerBehavior.FLOAT;
            public static DcMotor.ZeroPowerBehavior transferMotor = DcMotor.ZeroPowerBehavior.BRAKE;
            public static DcMotor.ZeroPowerBehavior turretMotor = DcMotor.ZeroPowerBehavior.BRAKE;
        }
    }

    public static class Servos {
        public static class Names {
            public static String rtl = "rtl";
            public static String rbl = "rbl";
            public static String ltl = "ltl";
            public static String lbl = "lbl";

            public static String leftBlock = "leftBlock";
            public static String rightBlock = "rightBlock";
            public static String hood = "hood";
        }

        public static class ZeroPositions {
            public static double blockPosition = 0;
            public static double hood = 0.57;
        }
    }
}
