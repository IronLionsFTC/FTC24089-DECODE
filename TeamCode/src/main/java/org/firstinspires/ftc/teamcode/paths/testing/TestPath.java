package org.firstinspires.ftc.teamcode.paths.testing;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;

public class TestPath {

    public static Pose start = new Pose(14, -14, Math.toRadians(45));
    public static Pose shoot = new Pose(5, -28, Math.toRadians(45));

    public static Pose intakeA = new Pose(-5, -51);
    public static Pose intakeAEnd = new Pose(20, -51);
    public static Pose hitLever = new Pose(16, -61);
    public static Pose hitLeverEnd = new Pose(19, -62);
    public static Pose shootA = new Pose(-10, -55);

    public static Pose intakeB = new Pose(-5, -79, 0);
    public static Pose intakeBEnd = new Pose(20, -79, 0);

    public static Pose intakeC = new Pose(-5, -99, 0);
    public static Pose intakeCEnd = new Pose(20, -99, 0);

    public static Pose shootTurnPoint = new Pose(0, -33, Math.toRadians(45));
    public static Pose intakeD = new Pose(20, -112, 0);
    public static Pose intakeDEnd = new Pose(26, -125, 0);
    public static Pose intakeDEnd2 = new Pose(5, -125, 0);

    public static Pose farShoot = new Pose(-20, -120);

    public static PathChain startToShoot(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(start, shoot))
                .setConstantHeadingInterpolation(Math.toRadians(45))
                .build();
    }

    public static PathChain intakeA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shoot, intakeA))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shootA, intakeB))
                .setTangentHeadingInterpolation()
                .build();
    }

    public static PathChain intakeC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shootA, intakeC))
                .setTangentHeadingInterpolation()
                .build();
    }

    public static PathChain intakeD(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shootA, intakeD))
                .setTangentHeadingInterpolation()
                .build();
    }

    public static PathChain intakeCreepA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeA, intakeAEnd))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain hitLeverFromA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeAEnd, hitLever))
                .setConstantHeadingInterpolation(Math.toDegrees(-90))
                .addPath(new BezierLine(hitLever, hitLeverEnd))
                .setConstantHeadingInterpolation(Math.toDegrees(-90))
                .build();
    }

    public static PathChain intakeCreepB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeB, intakeBEnd))
                .setConstantHeadingInterpolation(0)
                .addPath(new BezierLine(intakeBEnd, intakeB))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeCreepC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeC, intakeCEnd))
                .setConstantHeadingInterpolation(0)
                .addPath(new BezierLine(intakeCEnd, intakeC))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeCreepD(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeD, intakeDEnd))
                .setConstantHeadingInterpolation(Math.toRadians(-90))
                .addPath(new BezierLine(intakeD, intakeDEnd2))
                .setConstantHeadingInterpolation(Math.toRadians(-90))
                .build();
    }

    public static PathChain shootA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(hitLeverEnd, shootA))
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    public static PathChain shootB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeB, shootA))
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    public static PathChain shootC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeC, shootA))
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    public static PathChain shootD(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeDEnd2, farShoot))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();
    }
}
