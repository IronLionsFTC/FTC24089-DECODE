package org.firstinspires.ftc.teamcode.paths.core;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Core {
    public static Follower loadFollower(HardwareMap hardwareMap) {
        return Constants.createFollower(hardwareMap);
    }
}
