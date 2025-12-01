package org.firstinspires.ftc.teamcode.parameters;

import com.acmerobotics.dashboard.config.Config;

public class Software {
    public static class PID {

        @Config
        public static class VelocityController {
            public static double P = 0.005;
            public static double I = 0.0;
            public static double D = 0.0;
        }
    }

    @Config
    public static class SoftwareConstants {
        public static double CruiseSpeed = 1000;
        public static double Unblock = 0.6;
        public static double HoodMax = 0.85;
    }

    @Config
    public static class HSVFilter {

        public static boolean showRaw = false;

        public static class Purple {
            public static class H {
                public static double min = 190;
                public static double max = 250;
            }

            public static class S {
                public static double min = 0.3;
                public static double max = 1;
            }

            public static class V {
                public static double min = 0.3;
                public static double max = 100;
            }
        }
        public static class Green {
            public static class H {
                public static double min = 125;
                public static double max = 175;
            }

            public static class S {
                public static double min = 0.3;
                public static double max = 1;
            }

            public static class V {
                public static double min = 0.8;
                public static double max = 100;
            }
        }
    }
}
