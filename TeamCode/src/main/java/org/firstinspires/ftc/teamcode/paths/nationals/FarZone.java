package org.firstinspires.ftc.teamcode.paths.nationals;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;

public class FarZone {
    public static Pose start = new Pose(0, 0, 0);
    public static Vector3 goal = new Vector3(55.11, -125.98, -30);
    public static Pose farZone = new Pose(-2, -8, -Math.toDegrees(-15));
    public static Pose wallIntake = new Pose(23, 0, Math.toDegrees(0));
    public static Pose intakeSpikeStart = new Pose(5, -28, -Math.toDegrees(0));
    public static Pose intakeSpikeEnd = new Pose(20, -28);
    public static Pose intakeSpikeStart2 = new Pose(5, -53, -Math.toDegrees(0));
    public static Pose intakeSpikeEnd2 = new Pose(20, -53);

    public static PathChain startToShoot(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                    new BezierLine(start, farZone)
                )
                .setLinearHeadingInterpolation(start.getHeading(), farZone.getHeading())
                .build();
    }

    public static PathChain shootToWall(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(farZone, wallIntake)
                )
                .setTangentHeadingInterpolation()
                .build();
    }

    public static PathChain wallToShoot(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(wallIntake, farZone)
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    public static PathChain shootToFirstIntake(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(farZone, intakeSpikeStart)
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(intakeSpikeStart, intakeSpikeEnd)
                )
                .setTangentHeadingInterpolation()
                .build();
    }

    public static PathChain firstIntakeToShoot(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(intakeSpikeEnd, farZone)
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    public static PathChain shootToSecondIntake(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(farZone, intakeSpikeStart2)
                )
                .setLinearHeadingInterpolation(farZone.getHeading(), intakeSpikeStart.getHeading())
                .addPath(
                        new BezierLine(intakeSpikeStart2, intakeSpikeEnd2)
                )
                .setTangentHeadingInterpolation()
                .build();
    }

    public static PathChain secondIntakeToShoot(FollowerWrapper follower) {
        return new PathBuilder(follower.follower)
                .addPath(
                        new BezierLine(intakeSpikeEnd2, farZone)
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }
}
