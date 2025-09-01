package org.firstinspires.ftc.teamcode.paths.testing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class TestPath {

    public static Pose start = new Pose(0, 0, 0);
    public static Pose midpoint = new Pose(30, 0, 0);
    public static Pose end = new Pose(40, 0, Math.toRadians(90));
    public static Pose spline = new Pose(20, 30, Math.toRadians(180));

    public static PathChain part1(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierLine(start, midpoint))
                .setTangentHeadingInterpolation()
                .addPath(new BezierLine(midpoint, end))
                .setLinearHeadingInterpolation(midpoint.getHeading(), end.getHeading())
                .setBrakingStrength(2)
                .build();
    }

    public static PathChain part2(Follower follower) {
        return follower.pathBuilder()
                .addPath(new BezierCurve(end, spline, start))
                .setTangentHeadingInterpolation()
                .setBrakingStrength(2)
                .build();
    }
}
