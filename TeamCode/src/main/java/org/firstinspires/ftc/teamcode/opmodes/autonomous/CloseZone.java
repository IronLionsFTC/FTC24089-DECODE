package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.follower.Follower;
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
import org.firstinspires.ftc.teamcode.tasks.FollowPath;
import org.firstinspires.ftc.teamcode.tasks.IntakeForTime;
import org.firstinspires.ftc.teamcode.tasks.IntakeUntilFull;
import org.firstinspires.ftc.teamcode.tasks.RevFlywheel;
import org.firstinspires.ftc.teamcode.tasks.TeleOpShootAll;

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
        this.shooter = new Shooter(() -> 0);
        this.follower = new FollowerWrapper(hardwareMap);

        return Jobs.create()
                .addSeries(
                        new RevFlywheel(shooter, 2650, 0.3).with(
                                new FollowPath(follower, TestPath.startToShoot(follower))
                        ),
                        new TeleOpShootAll(intake, transfer, shooter).with(
                                new FollowPath(follower, TestPath.shootPath(follower))
                        ),

                        new FollowPath(follower, TestPath.intakeA(follower)),
                        new FollowPath(follower, TestPath.intakeCreepA(follower)).setSpeed(0.7).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, TestPath.hitLeverFromA(follower)),
                        new FollowPath(follower, TestPath.shootA(follower)),
                        new TeleOpShootAll(intake, transfer, shooter).with(
                                new FollowPath(follower, TestPath.shootPath(follower))
                        ),
                        new FollowPath(follower, TestPath.intakeB(follower)),
                        new FollowPath(follower, TestPath.intakeCreepB(follower)).setSpeed(0.7).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, TestPath.shootB(follower)),
                        new TeleOpShootAll(intake, transfer, shooter).with(
                                new FollowPath(follower, TestPath.shootPath(follower))
                        ),
                        new FollowPath(follower, TestPath.intakeC(follower)),
                        new FollowPath(follower, TestPath.intakeCreepC(follower)).setSpeed(0.7).with(
                                new IntakeForTime(intake, transfer, 1.5)
                        ),
                        new FollowPath(follower, TestPath.shootC(follower)),
                        new TeleOpShootAll(intake, transfer, shooter).with(
                                new FollowPath(follower, TestPath.shootPath(follower))
                        )
                )

                .registerSystem(follower)
                .registerSystem(intake)
                .registerSystem(transfer)
                .registerSystem(shooter);
    }
}
