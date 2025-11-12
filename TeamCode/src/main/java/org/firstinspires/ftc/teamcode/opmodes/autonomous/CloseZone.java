package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.paths.testing.TestPath;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;
import org.firstinspires.ftc.teamcode.tasks.AutoShootAll;
import org.firstinspires.ftc.teamcode.tasks.FollowPath;
import org.firstinspires.ftc.teamcode.tasks.IntakeForTime;
import org.firstinspires.ftc.teamcode.tasks.TeleOpFlywheel;
import org.firstinspires.ftc.teamcode.tasks.TurnToDegrees;

@Autonomous(name = "12 CLOSE + 9 FAR")
public class CloseZone extends TaskOpMode {

    private FollowerWrapper follower;
    public Intake intake;
    public Transfer transfer;
    public Shooter shooter;

    @Override
    public Jobs spawn() {

        this.intake = new Intake();
        this.transfer = new Transfer();
        this.follower = new FollowerWrapper(hardwareMap);
        this.follower.follower.setStartingPose(new Pose(14, -14, Math.toRadians(45)));
        this.shooter = new Shooter(follower::position, follower::velocity, new Vector3(8, -5, 40));

        return Jobs.create()
                .addSeries(
                        new TeleOpFlywheel(intake, transfer, shooter).with(
                                new FollowPath(follower, TestPath.startToShoot(follower)).holdEnd(true)
                        ),

                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, TestPath.intakeA(follower)),
                        new FollowPath(follower, TestPath.intakeCreepA(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, TestPath.hitLeverFromA(follower)),
                        new Sleep(0.8),
                        new FollowPath(follower, TestPath.shootA(follower)).holdEnd(true),

                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, TestPath.intakeB(follower)),
                        new FollowPath(follower, TestPath.intakeCreepB(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, TestPath.shootB(follower)).holdEnd(true),

                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, TestPath.intakeC(follower)),
                        new FollowPath(follower, TestPath.intakeCreepC(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, TestPath.shootC(follower)).holdEnd(true),

                        new Sleep(0.5),
                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, TestPath.intakeD(follower)).then(
                                new FollowPath(follower, TestPath.intakeCreepD(follower))
                        ).with(
                                new IntakeForTime(intake, transfer,3)
                        ),


                        new FollowPath(follower, TestPath.shootD(follower)).holdEnd(true),

                        new Sleep(0.5),
                        new AutoShootAll(intake, transfer, shooter),
                        new Sleep(2)
                )

                .registerSystem(follower)
                .registerSystem(intake)
                .registerSystem(transfer)
                .registerSystem(shooter);
    }
}
