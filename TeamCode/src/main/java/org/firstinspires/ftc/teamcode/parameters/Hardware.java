package org.firstinspires.ftc.teamcode.parameters;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Hardware {
    public static class Motors {
        public static class Names {
            public static String frontRight = "rightFront";
            public static String frontLeft = "leftFront";
            public static String backRight = "rightRear";
            public static String backLeft = "leftRear";

            public static String outtake1 = "outtake1";
            public static String outtake2 = "outtake2";

            public static String intake = "intake";
        }

        public static class Reversed {
            public static boolean frontRight = true;
            public static boolean frontLeft = false;
            public static boolean backRight = true;
            public static boolean backLeft = false;

            public static boolean outtake1 = false;
            public static boolean outtake2 = false;

            public static boolean intake = false;
        }

        public static class ZPB {
            public static DcMotor.ZeroPowerBehavior driveMotors = DcMotor.ZeroPowerBehavior.BRAKE;
            public static DcMotor.ZeroPowerBehavior outtakeMotors = DcMotor.ZeroPowerBehavior.FLOAT;
            public static DcMotor.ZeroPowerBehavior intakeMotor = DcMotor.ZeroPowerBehavior.FLOAT;
        }
    }
}
