package org.firstinspires.ftc.teamcode.paths.testing;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;

public class FarPathBlue {

    public static Pose start = new Pose(
            FarPathRed.start.getX(),
            FarPathRed.start.getY() * -1,
            FarPathRed.start.getHeading()
    );

    public static Pose intakeB = new Pose(
            FarPathRed.intakeB.getX(),
            FarPathRed.intakeB.getY() * -1,
            FarPathRed.intakeB.getHeading()
    );

    public static Pose intakeBEnd = new Pose(
            FarPathRed.intakeBEnd.getX(),
            FarPathRed.intakeBEnd.getY() * -1,
            FarPathRed.intakeBEnd.getHeading()
    );

    public static Pose intakeC = new Pose(
            FarPathRed.intakeC.getX(),
            FarPathRed.intakeC.getY() * -1,
            FarPathRed.intakeC.getHeading()
    );

    public static Pose intakeCEnd = new Pose(
            FarPathRed.intakeCEnd.getX(),
            FarPathRed.intakeCEnd.getY() * -1,
            FarPathRed.intakeCEnd.getHeading()
    );

    public static Pose intakeD = new Pose(
            FarPathRed.intakeD.getX(),
            FarPathRed.intakeD.getY() * -1,
            FarPathRed.intakeD.getHeading()
    );

    public static Pose intakeDEnd = new Pose(
            FarPathRed.intakeDEnd.getX(),
            FarPathRed.intakeDEnd.getY() * -1,
            FarPathRed.intakeDEnd.getHeading()
    );

    public static Pose farShoot = new Pose(
            FarPathRed.farShoot.getX(),
            FarPathRed.farShoot.getY() * -1,
            FarPathRed.farShoot.getHeading()
    );

    public static PathChain startToShoot(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(start, farShoot))
                .setConstantHeadingInterpolation(Math.toRadians(-67))
                .build();
    }

    public static PathChain intakeA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(farShoot, intakeD))
                .setConstantHeadingInterpolation(Math.toRadians(10))
                .build();
    }

    public static PathChain intakeB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(farShoot, intakeC))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(farShoot, intakeB))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeCreepA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeD, intakeDEnd))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeCreepB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeC, intakeCEnd))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeCreepC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeB, intakeBEnd))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain shootA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeDEnd, farShoot))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-67))
                .build();
    }

    public static PathChain shootB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeCEnd, farShoot))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-67))
                .build();
    }

    public static PathChain shootC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeBEnd, farShoot))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-67))
                .build();
    }

    public static PathChain park(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(farShoot, intakeC))
                .setTangentHeadingInterpolation()
                .build();
    }
}
