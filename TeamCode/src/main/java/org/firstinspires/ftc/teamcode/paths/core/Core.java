package org.firstinspires.ftc.teamcode.paths.core;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedropathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedropathing.constants.LConstants;

public class Core {
    public static Follower loadFollower(HardwareMap hardwareMap) {
        Follower follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(new Pose(0, 0, 0));
        return follower;
    }

    public static PathChain chain(Path... paths) {
        PathBuilder builder = new PathBuilder();
        for (Path path : paths) {
            builder.addPath(path);
        }
        return builder.build();
    }

    public static Path simpleLine(Point a, Point b, double heading) {
        Path line = new Path(
                new BezierLine(
                        a,
                        b
                )
        );
        line.setConstantHeadingInterpolation(heading);
        return line;
    }
}
