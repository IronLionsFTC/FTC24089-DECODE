package org.firstinspires.ftc.teamcode.parameters;

import com.acmerobotics.dashboard.config.Config;

public class Software {
    public static class PID {

        @Config
        public static class VelocityController {
            public static double P = 0.0;
            public static double I = 0.0;
            public static double D = 0.0;
        }
    }

    @Config
    public static class Constants {
        public static double CruiseSpeed = 1000;
        public static double Unblock = 0.45;
        public static double HoodMax = 0.89;
    }

    @Config
    public static class HSVFilter {

        public static boolean showRaw = false;

        public static class Purple {
            public static class H {
                public static double min = 190;
                public static double max = 240;
            }

            public static class S {
                public static double min = 0.3;
                public static double max = 1;
            }

            public static class V {
                public static double min = 0.3;
                public static double max = 1e9;
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
                public static double min = 0.3;
                public static double max = 1e9;
            }
        }
    }
}
