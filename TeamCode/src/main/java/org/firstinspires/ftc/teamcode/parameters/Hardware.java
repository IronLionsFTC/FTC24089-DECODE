package org.firstinspires.ftc.teamcode.parameters;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Hardware {
    public static class Motors {
        public static class Names {
            public static String frontRight = "rightFront";
            public static String frontLeft = "leftFront";
            public static String backRight = "rightRear";
            public static String backLeft = "leftRear";
        }

        public static class Reversed {
            public static boolean frontRight = true;
            public static boolean frontLeft = false;
            public static boolean backRight = true;
            public static boolean backLeft = false;
        }

        public static class ZPB {
            public static DcMotor.ZeroPowerBehavior driveMotors = DcMotor.ZeroPowerBehavior.BRAKE;
        }
    }
}
