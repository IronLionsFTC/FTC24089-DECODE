package org.firstinspires.ftc.teamcode.paths.testing;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;

public class FifteenPathBlue {

    public static Pose start = new Pose(
            FifteenPathRed.start.getX(),
            FifteenPathRed.start.getY() * -1,
            FifteenPathRed.start.getHeading()
    );

    public static Pose intakeA = new Pose(
            FifteenPathRed.intakeA.getX(),
            FifteenPathRed.intakeA.getY() * -1,
            FifteenPathRed.intakeA.getHeading()
    );
    public static Pose intakeAEnd = new Pose(
            FifteenPathRed.intakeAEnd.getX(),
            FifteenPathRed.intakeAEnd.getY() * -1,
            FifteenPathRed.intakeAEnd.getHeading()
    );

    public static Pose hitLever = new Pose(
            FifteenPathRed.hitLever.getX(),
            FifteenPathRed.hitLever.getY() * -1,
            FifteenPathRed.hitLever.getHeading()
    );
    public static Pose hitLeverEnd = new Pose(
            FifteenPathRed.hitLeverEnd.getX(),
            FifteenPathRed.hitLeverEnd.getY() * -1,
            FifteenPathRed.hitLeverEnd.getHeading()
    );

    public static Pose shootA = new Pose(
            FifteenPathRed.shootA.getX(),
            FifteenPathRed.shootA.getY() * -1,
            FifteenPathRed.shootA.getHeading()
    );

    public static Pose intakeB = new Pose(
            FifteenPathRed.intakeB.getX(),
            FifteenPathRed.intakeB.getY() * -1,
            FifteenPathRed.intakeB.getHeading()
    );
    public static Pose intakeBEnd = new Pose(
            FifteenPathRed.intakeBEnd.getX(),
            FifteenPathRed.intakeBEnd.getY() * -1,
            FifteenPathRed.intakeBEnd.getHeading()
    );

    public static Pose intakeC = new Pose(
            FifteenPathRed.intakeC.getX(),
            FifteenPathRed.intakeC.getY() * -1,
            FifteenPathRed.intakeC.getHeading()
    );
    public static Pose intakeCEnd = new Pose(
            FifteenPathRed.intakeCEnd.getX(),
            FifteenPathRed.intakeCEnd.getY() * -1,
            FifteenPathRed.intakeCEnd.getHeading()
    );

    public static Pose intakeD = new Pose(
            FifteenPathRed.intakeD.getX(),
            FifteenPathRed.intakeD.getY() * -1,
            FifteenPathRed.intakeD.getHeading()
    );
    public static Pose intakeDEnd = new Pose(
            FifteenPathRed.intakeDEnd.getX(),
            FifteenPathRed.intakeDEnd.getY() * -1,
            FifteenPathRed.intakeDEnd.getHeading()
    );

    public static Pose farShoot = new Pose(
            FifteenPathRed.farShoot.getX(),
            FifteenPathRed.farShoot.getY() * -1,
            FifteenPathRed.farShoot.getHeading()
    );

    public static PathChain startToShoot(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(start, shootA))
                .setConstantHeadingInterpolation(Math.toRadians(-48))
                .build();
    }

    public static PathChain intakeA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shootA, intakeA))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shootA, intakeB))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shootA, intakeC))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeD(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(farShoot, intakeD))
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
                .setConstantHeadingInterpolation(Math.toRadians(-90))
                .addPath(new BezierLine(hitLever, hitLeverEnd))
                .setConstantHeadingInterpolation(Math.toRadians(-90))
                .build();
    }

    public static PathChain intakeCreepB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeB, intakeBEnd))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeCreepC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeC, intakeCEnd))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeCreepD(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeD, intakeDEnd))
                .setConstantHeadingInterpolation(Math.toRadians(-10))
                .build();
    }

    public static PathChain shootA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(hitLeverEnd, shootA))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-48))
                .build();
    }

    public static PathChain shootB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeBEnd, shootA))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-48))
                .build();
    }

    public static PathChain shootC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeCEnd, farShoot))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-67))
                .build();
    }

    public static PathChain shootD(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeDEnd, farShoot))
                .setConstantHeadingInterpolation(Math.toRadians(-67))
                .build();
    }

    public static PathChain park(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(farShoot, intakeA))
                .setTangentHeadingInterpolation()
                .build();
    }
}
