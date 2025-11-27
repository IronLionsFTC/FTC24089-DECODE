package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.paths.testing.FarPathBlue;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;
import org.firstinspires.ftc.teamcode.tasks.AutoShootAll;
import org.firstinspires.ftc.teamcode.tasks.FollowPath;
import org.firstinspires.ftc.teamcode.tasks.IntakeForTime;
import org.firstinspires.ftc.teamcode.tasks.TeleOpFlywheel;

@Autonomous(name = "9 FAR BLUE")
public class FarZoneBlue extends TaskOpMode {

    private FollowerWrapper follower;
    public Intake intake;
    public Transfer transfer;
    public Shooter shooter;

    @Override
    public Jobs spawn() {

        this.intake = new Intake();
        this.transfer = new Transfer();
        this.follower = new FollowerWrapper(hardwareMap);
        this.follower.follower.setStartingPose(FarPathBlue.start);
        this.shooter = new Shooter(follower::position, follower::velocity, new Vector3(8, -5, 40));

        this.shooter.speedFactor = 0.98;

        return Jobs.create()
                .addSeries(
                        new TeleOpFlywheel(intake, transfer, shooter).with(
                                new FollowPath(follower, FarPathBlue.startToShoot(follower)).holdEnd(true)
                        ),

                        new Sleep(2),
                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, FarPathBlue.intakeA(follower)).then(
                                new Sleep(1).then(new FollowPath(follower, FarPathBlue.intakeCreepA(follower)))
                        ).with(
                            new IntakeForTime(intake, transfer, 4)
                        ),
                        new FollowPath(follower, FarPathBlue.shootA(follower)).holdEnd(true),
                        new Sleep(0.8),
                        new AutoShootAll(intake, transfer, shooter),
                        new FollowPath(follower, FarPathBlue.intakeB(follower)),
                        new FollowPath(follower, FarPathBlue.intakeCreepB(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, FarPathBlue.shootB(follower)).holdEnd(true),

                        new Sleep(0.8),
                        new AutoShootAll(intake, transfer, shooter),

                        new FollowPath(follower, FarPathBlue.intakeC(follower)),
                        new FollowPath(follower, FarPathBlue.intakeCreepC(follower)).setSpeed(1).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, FarPathBlue.shootC(follower)).holdEnd(true),

                        new Sleep(0.8),
                        new AutoShootAll(intake, transfer, shooter),
                        new FollowPath(follower, FarPathBlue.park(follower))
                )

                .registerSystem(follower)
                .registerSystem(intake)
                .registerSystem(transfer)
                .registerSystem(shooter);
    }
}
