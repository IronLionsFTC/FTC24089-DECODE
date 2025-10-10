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
}
