package org.firstinspires.ftc.teamcode.paths.testing;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;

public class TestPath {

    public static Pose start = new Pose(0, 0, 0);
    public static Pose intakeA = new Pose(-5, 51, 0);
    public static Pose intakeAEnd = new Pose(20, 51, 0);
    public static Pose hitLever = new Pose(24, 61);
    public static Pose intakeB = new Pose(-5, 75, 0);
    public static Pose intakeBEnd = new Pose(22, 75, 0);
    public static Pose intakeC = new Pose(-5, 99, 0);
    public static Pose intakeCEnd = new Pose(26, 99, 0);
    public static Pose shoot = new Pose(-20, 55, Math.toRadians(-45));
    public static Pose shootEnd = new Pose(-10, 45, Math.toRadians(-45));

    public static PathChain startToShoot(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(start, shoot))
                .setConstantHeadingInterpolation(Math.toRadians(-45))
                .build();
    }

    public static PathChain shootPath(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shoot, shootEnd))
                .setConstantHeadingInterpolation(Math.toRadians(-45))
                .build();
    }

    public static PathChain intakeA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shootEnd, intakeA))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shootEnd, intakeB))
                .setConstantHeadingInterpolation(0)
                .build();
    }

    public static PathChain intakeC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(shootEnd, intakeC))
                .setConstantHeadingInterpolation(0)
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
                .addPath(new BezierLine(intakeAEnd, intakeA))
                .setConstantHeadingInterpolation(0)
                .addPath(new BezierLine(intakeA, hitLever))
                .setConstantHeadingInterpolation(0)
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

    public static PathChain shootA(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(hitLever, shoot))
                .setLinearHeadingInterpolation(0, shoot.getHeading())
                .build();
    }

    public static PathChain shootB(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeBEnd, shoot))
                .setLinearHeadingInterpolation(0, shoot.getHeading())
                .build();
    }

    public static PathChain shootC(FollowerWrapper follower) {
        return follower.follower.pathBuilder()
                .addPath(new BezierLine(intakeCEnd, shoot))
                .setLinearHeadingInterpolation(0, shoot.getHeading())
                .build();
    }
}
