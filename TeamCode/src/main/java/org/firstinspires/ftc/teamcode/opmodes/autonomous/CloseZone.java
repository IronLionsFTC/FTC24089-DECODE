package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cache.Ordering;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.paths.testing.TestPath;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;
import org.firstinspires.ftc.teamcode.tasks.AutoShootAll;
import org.firstinspires.ftc.teamcode.tasks.FollowPath;
import org.firstinspires.ftc.teamcode.tasks.IntakeForTime;
import org.firstinspires.ftc.teamcode.tasks.IntakeUntilFull;
import org.firstinspires.ftc.teamcode.tasks.RevFlywheel;
import org.firstinspires.ftc.teamcode.tasks.TeleOpFlywheel;
import org.firstinspires.ftc.teamcode.tasks.TeleOpShootAll;
import org.firstinspires.ftc.teamcode.tasks.TurnToDegrees;

@TeleOp
public class CloseZone extends TaskOpMode {

    private FollowerWrapper follower;
    public Intake intake;
    public Transfer transfer;
    public Shooter shooter;

    @Override
    public Jobs spawn() {

        this.intake = new Intake();
        this.transfer = new Transfer();
        this.shooter = new Shooter(() -> follower.follower.getPose().distanceFrom(new Pose(0, 0, 0)));
        this.follower = new FollowerWrapper(hardwareMap);
        this.follower.follower.setStartingPose(new Pose(14, -14, Math.toRadians(45)));

        return Jobs.create()
                .addSeries(
                        new TeleOpFlywheel(intake, transfer, shooter).with(
                                new FollowPath(follower, TestPath.startToShoot(follower))
                        ),

                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, TestPath.intakeA(follower)),
                        new FollowPath(follower, TestPath.intakeCreepA(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, TestPath.hitLeverFromA(follower)),
                        new FollowPath(follower, TestPath.shootA(follower)),

                        new TurnToDegrees(follower, shooter::yieldAzimuth),
                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, TestPath.intakeB(follower)),
                        new FollowPath(follower, TestPath.intakeCreepB(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, TestPath.shootB(follower)),

                        new TurnToDegrees(follower, shooter::yieldAzimuth),
                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, TestPath.intakeC(follower)),
                        new FollowPath(follower, TestPath.intakeCreepC(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, TestPath.shootC(follower)),

                        new TurnToDegrees(follower, shooter::yieldAzimuth),
                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, TestPath.intakeD(follower)),

                        new FollowPath(follower, TestPath.intakeCreepD(follower)).with(
                                new IntakeForTime(intake, transfer, 2)
                        ),

                        new FollowPath(follower, TestPath.shootD(follower)),

                        new TurnToDegrees(follower, shooter::yieldAzimuth),
                        new AutoShootAll(intake, transfer, shooter)
                )

                .registerSystem(follower)
                .registerSystem(intake)
                .registerSystem(transfer)
                .registerSystem(shooter);
    }
}
