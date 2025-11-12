package org.firstinspires.ftc.teamcode.paths.testing;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;

public class FarPath {

    public static Pose start = new Pose(-20, -124, Math.toRadians(0));

    public static Pose intakeA = new Pose(-5, -53);
    public static Pose intakeAEnd = new Pose(20, -53);
    public static Pose hitLever = new Pose(16, -60);
    public static Pose hitLeverEnd = new Pose(24, -65);
    public static Pose shootA = new Pose(-10, -48);

    public static Pose intakeB = new Pose(-5, -79, 0);
    public static Pose intakeBEnd = new Pose(24, -79, 0);

    public static Pose intakeC = new Pose(-5, -99, 0);
    public static Pose intakeCEnd = new Pose(24, -99, 0);

    public static Pose intakeD = new Pose(24, -124, 0);
    public static Pose intakeDEnd = new Pose(24, -110, 0);

    public static Pose farShoot = new Pose(-20, -116);

    public static PathChain startToShoot(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(start, farShoot))
                .setConstantHeadingInterpolation(Math.toRadians(68))
                .build();
    }

    public static PathChain intakeA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(farShoot, intakeD))
                .setConstantHeadingInterpolation(0)
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
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(67))
                .build();
    }

    public static PathChain shootB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeCEnd, farShoot))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(67))
                .build();
    }

    public static PathChain shootC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeBEnd, farShoot))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(67))
                .build();
    }
}
