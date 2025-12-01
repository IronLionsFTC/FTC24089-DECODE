package org.firstinspires.ftc.teamcode.opmodes.autopmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Forever;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Feed;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;
import org.firstinspires.ftc.teamcode.systems.Turret;
import org.firstinspires.ftc.teamcode.tasks.FollowPath;
import org.firstinspires.ftc.teamcode.tasks.ShootForTime;
import org.firstinspires.ftc.teamcode.tasks.ToggleIntake;
import org.firstinspires.ftc.teamcode.tasks.ToggleShooterMode;

@Autonomous
public class TheseusBlue extends TaskOpMode {

    private FollowerWrapper follower;
    private Feed feed;
    private Turret turret;

    public Jobs spawn() {
        this.follower = new FollowerWrapper(hardwareMap);
        this.follower.follower.setStartingPose(org.firstinspires.ftc.teamcode.paths.nationals.TheseusBlue.start);
        this.feed = new Feed();
        this.turret = new Turret();

        Turret.RobotConstants.tx = org.firstinspires.ftc.teamcode.paths.nationals.TheseusBlue.goal.x;
        Turret.RobotConstants.ty = org.firstinspires.ftc.teamcode.paths.nationals.TheseusBlue.goal.y;
        Turret.RobotConstants.tz = org.firstinspires.ftc.teamcode.paths.nationals.TheseusBlue.goal.z;

        return Jobs.create()
                .addTask(
                        new Forever(follower.follower::update)
                )
                .addSeries(
                        new ToggleShooterMode(turret),
                        new FollowPath(follower, org.firstinspires.ftc.teamcode.paths.nationals.TheseusBlue.startToShoot(follower)).holdEnd(true),
                        new Sleep(2),
                        new ShootForTime(feed, turret, 1.5, false),
                        new FollowPath(follower, org.firstinspires.ftc.teamcode.paths.nationals.TheseusBlue.firstIntake(follower)).with(
                                new ToggleIntake(feed)
                        ),
                        new FollowPath(follower, org.firstinspires.ftc.teamcode.paths.nationals.TheseusBlue.firstIntakeTurnForGate(follower)),
                        new ToggleIntake(feed),
                        new FollowPath(follower, org.firstinspires.ftc.teamcode.paths.nationals.TheseusBlue.hitGate(follower)),
                        new Sleep(2),
                        new FollowPath(follower, org.firstinspires.ftc.teamcode.paths.nationals.TheseusBlue.gateToShoot(follower)),
                        new ShootForTime(feed, turret, 1.5, false)
                )
                .registerSystem(feed)
                .registerSystem(turret)
                .registerSystem(follower);
    }
}
