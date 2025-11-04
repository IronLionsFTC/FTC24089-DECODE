package org.firstinspires.ftc.teamcode.parameters;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Hardware {
    public static class Motors {
        public static class Names {
            public static String frontRight = "rightFront";
            public static String frontLeft = "leftFront";
            public static String backRight = "rightRear";
            public static String backLeft = "leftRear";
            public static String topShooter = "topShooter";
            public static String bottomShooter = "bottomShooter";
            public static String intakeMotor = "intakeMotor";
            public static String transferMotor = "transferMotor";
        }

        public static class Reversed {
            public static boolean frontRight = true;
            public static boolean frontLeft = false;
            public static boolean backRight = true;
            public static boolean backLeft = false;

            // Ensure these are opposing as motors are geared together.
            public static boolean topShooter = false;
            public static boolean bottomShooter = true;

            public static boolean intakeMotor = false;
            public static boolean transferMotor = false;
        }

        public static class ZPB {
            public static DcMotor.ZeroPowerBehavior driveMotors = DcMotor.ZeroPowerBehavior.BRAKE;
            public static DcMotor.ZeroPowerBehavior shooterMotors = DcMotor.ZeroPowerBehavior.FLOAT;
            public static DcMotor.ZeroPowerBehavior intakeMotor = DcMotor.ZeroPowerBehavior.BRAKE;
            public static DcMotor.ZeroPowerBehavior transferMotor = DcMotor.ZeroPowerBehavior.BRAKE;
        }
    }

    public static class Indicators {
        public static class Names {
            public static String light = "light";
        }
    }

    public static class Servos {
        public static class Names {
            public static String leftBlock = "leftBlock";
            public static String rightBlock = "rightBlock";
            public static String limelightServo = "limelightServo";
            public static String shooterHood = "shooterHood";
        }

        public static class ZeroPositions {
            public static double blockPosition = 0.27;
            public static double limelight = 0.4;
            public static double hood = 1;
        }
    }

    public static class ColourSensors {
        public static class Names {
            public static String firstLeft = "lc1";
            public static String secondLeft = "lc2";
            public static String thirdLeft = "lc3";
        }
    }
}
