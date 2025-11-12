package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.paths.testing.TestPath;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;
import org.firstinspires.ftc.teamcode.tasks.FollowPath;

@TeleOp
public class PedroTaskTest extends TaskOpMode {

    private FollowerWrapper follower;

    @Override
    public Jobs spawn() {

        this.follower = new FollowerWrapper(hardwareMap);

        return Jobs.create()
                .addSeries(
                        new FollowPath(
                                follower, TestPath.intakeA(follower)
                        ),
                        new FollowPath(
                                follower, TestPath.shootA(follower)
                        )
                )
                .registerSystem(follower);
    }
}
