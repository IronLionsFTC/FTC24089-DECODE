package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.paths.testing.FarPathRed;
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
        this.follower.follower.setStartingPose(FarPathRed.start);
        this.shooter = new Shooter(follower::position, follower::velocity, new Vector3(8, -5, 40));

        return Jobs.create()
                .addSeries(
                        new TeleOpFlywheel(intake, transfer, shooter).with(
                                new FollowPath(follower, FarPathRed.startToShoot(follower)).holdEnd(true)
                        ),

                        new Sleep(2),
                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, FarPathRed.intakeA(follower)).then(
                                new Sleep(1).then(new FollowPath(follower, FarPathRed.intakeCreepA(follower)))
                        ).with(
                            new IntakeForTime(intake, transfer, 4)
                        ),
                        new FollowPath(follower, FarPathRed.shootA(follower)).holdEnd(true),
                        new Sleep(0.8),
                        new AutoShootAll(intake, transfer, shooter),
                        new FollowPath(follower, FarPathRed.intakeB(follower)),
                        new FollowPath(follower, FarPathRed.intakeCreepB(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, FarPathRed.shootB(follower)).holdEnd(true),

                        new Sleep(0.8),
                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, FarPathRed.intakeC(follower)),
                        new FollowPath(follower, FarPathRed.intakeCreepC(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, FarPathRed.shootC(follower)).holdEnd(true),

                        new Sleep(0.8),
                        new AutoShootAll(intake, transfer, shooter),
                        new FollowPath(follower, FarPathRed.intakeA(follower))
                )

                .registerSystem(follower)
                .registerSystem(intake)
                .registerSystem(transfer)
                .registerSystem(shooter);
    }
}
