package org.firstinspires.ftc.teamcode.paths.nationals;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;

public class TheseusBlue {
    public static Pose start = new Pose(-20, 2, Math.toDegrees(0));
    public static Vector3 goal = new Vector3(0, 0, 30);
    public static Pose closeZone = new Pose(-20, 32);
    public static Pose intakeSpikeStart1 = new Pose(-12, 45);
    public static Pose intakeSpikeEnd1 = new Pose(8, 45);
    public static Pose intakeSpikeStart2 = new Pose(-12, 70);
    public static Pose intakeSpikeEnd2 = new Pose(8, 70);
    public static Pose leverAlign = new Pose(-2, 55);
    public static Pose leverHit = new Pose(8, 55);

    public static PathChain startToShoot(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(start, closeZone)
                )
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain firstIntake(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(closeZone, intakeSpikeStart1)
                )
                .setConstantHeadingInterpolation(0)
                .addPath(
                        new BezierLine(intakeSpikeStart1, intakeSpikeEnd1)
                )
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain firstIntakeTurnForGate(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(intakeSpikeEnd1, leverAlign)
                )
                .setLinearHeadingInterpolation(0, Math.toDegrees(90))
                .build();
    }

    public static PathChain hitGate(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(leverAlign, leverHit)
                )
                .setConstantHeadingInterpolation(Math.toDegrees(90))
                .build();
    }

    public static PathChain gateToShoot(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(leverHit, closeZone)
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }
}
