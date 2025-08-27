package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Forever;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Parallel;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Series;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.paths.core.Core;
import org.firstinspires.ftc.teamcode.paths.testing.TestPath;
import org.firstinspires.ftc.teamcode.systems.IntakeSlides;
import org.firstinspires.ftc.teamcode.tasks.ExtendIntake;
import org.firstinspires.ftc.teamcode.tasks.FollowPath;
import org.firstinspires.ftc.teamcode.tasks.RetractIntake;

@Autonomous
public class SlidesTest extends TaskOpMode {

    private IntakeSlides intake;
    private Follower follower;

    public Jobs spawn() {
        intake = new IntakeSlides();
        follower = Core.loadFollower(hardwareMap);

        return new Jobs(
                new Parallel(
                        new Forever(follower::update),
                        new Series(
                            new ExtendIntake(intake).with(
                                    new FollowPath(follower, TestPath.firstSegment)
                            ),
                            new RetractIntake(intake).with(
                                    new FollowPath(follower, TestPath.secondSegment)
                            )
                        )
                ),
                intake
        );
    }
}
