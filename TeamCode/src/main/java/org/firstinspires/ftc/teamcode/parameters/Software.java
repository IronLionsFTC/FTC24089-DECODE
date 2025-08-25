package org.firstinspires.ftc.teamcode.parameters;

import com.acmerobotics.dashboard.config.Config;

public class Software {
    public static class PID {

        @Config
        public static class Intake {
            public static double P = 0.0;
            public static double I = 0.0;
            public static double D = 0.0;
        }

        @Config
        public static class Outtake {
            public static double P = 0.0;
            public static double I = 0.0;
            public static double D = 0.0;
        }
    }
}
