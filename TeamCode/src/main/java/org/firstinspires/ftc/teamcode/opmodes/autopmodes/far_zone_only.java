package org.firstinspires.ftc.teamcode.opmodes.autopmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.paths.nationals.FarZone;
import org.firstinspires.ftc.teamcode.systems.Feed;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;
import org.firstinspires.ftc.teamcode.systems.Turret;
import org.firstinspires.ftc.teamcode.tasks.FollowPath;
import org.firstinspires.ftc.teamcode.tasks.ShootForTime;
import org.firstinspires.ftc.teamcode.tasks.ToggleIntake;
import org.firstinspires.ftc.teamcode.tasks.ToggleShooterMode;

@Autonomous
public class far_zone_only extends TaskOpMode {

    private FollowerWrapper follower;
    private Feed feed;
    private Turret turret;

    // 125.98
    // 55.11
    public Jobs spawn() {
        this.follower = new FollowerWrapper(hardwareMap);
        this.feed = new Feed();
        this.turret = new Turret();

        Turret.Constants.tx = FarZone.goal.x;
        Turret.Constants.ty = FarZone.goal.y;
        Turret.Constants.tz = FarZone.goal.z;

        return Jobs.create()
                .addSeries(
                        new ToggleShooterMode(turret),
                        new FollowPath(follower, FarZone.startToShoot(follower)),
                        new Sleep(2),
                        new ShootForTime(feed, turret, 1.5, false),
                        new FollowPath(follower, FarZone.shootToWall(follower)).with(
                                new ToggleIntake(feed)
                        ),
                        new FollowPath(follower, FarZone.wallToShoot(follower)),
                        new Sleep(0.5),
                        new ToggleIntake(feed),
                        new ShootForTime(feed, turret, 1.5, false),
                        new FollowPath(follower, FarZone.shootToFirstIntake(follower)).with(
                                new ToggleIntake(feed)
                        ),
                        new Sleep(0.5),
                        new ToggleIntake(feed),
                        new FollowPath(follower, FarZone.firstIntakeToShoot(follower)),
                        new ShootForTime(feed, turret, 1.5, false),
                        new FollowPath(follower, FarZone.shootToSecondIntake(follower)).with(
                                new ToggleIntake(feed)
                        ),
                        new Sleep(0.5),
                        new ToggleIntake(feed),
                        new FollowPath(follower, FarZone.secondIntakeToShoot(follower)),
                        new ShootForTime(feed, turret, 1.5, false)
                )
                .registerSystem(feed)
                .registerSystem(turret)
                .registerSystem(follower);
    }
}
