package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.paths.testing.FarPath;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;
import org.firstinspires.ftc.teamcode.tasks.AutoShootAll;
import org.firstinspires.ftc.teamcode.tasks.FollowPath;
import org.firstinspires.ftc.teamcode.tasks.IntakeForTime;
import org.firstinspires.ftc.teamcode.tasks.TeleOpFlywheel;

@Autonomous(name = "9 FAR")
public class FarZone extends TaskOpMode {

    private FollowerWrapper follower;
    public Intake intake;
    public Transfer transfer;
    public Shooter shooter;

    @Override
    public Jobs spawn() {

        this.intake = new Intake();
        this.transfer = new Transfer();
        this.follower = new FollowerWrapper(hardwareMap);
        this.follower.follower.setStartingPose(FarPath.start);
        this.shooter = new Shooter(follower::position, follower::velocity, new Vector3(8, -5, 40));

        return Jobs.create()
                .addSeries(
                        new TeleOpFlywheel(intake, transfer, shooter).with(
                                new FollowPath(follower, FarPath.startToShoot(follower)).holdEnd(true)
                        ),

                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, FarPath.intakeA(follower)),
                        new FollowPath(follower, FarPath.intakeCreepA(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, FarPath.shootA(follower)).holdEnd(true),
                        new AutoShootAll(intake, transfer, shooter),
                        new FollowPath(follower, FarPath.intakeB(follower)),
                        new FollowPath(follower, FarPath.intakeCreepB(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, FarPath.shootB(follower)).holdEnd(true),

                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, FarPath.intakeC(follower)),
                        new FollowPath(follower, FarPath.intakeCreepC(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, FarPath.shootC(follower)).holdEnd(true),

                        new Sleep(0.5),
                        new AutoShootAll(intake, transfer, shooter)
                )

                .registerSystem(follower)
                .registerSystem(intake)
                .registerSystem(transfer)
                .registerSystem(shooter);
    }
}
