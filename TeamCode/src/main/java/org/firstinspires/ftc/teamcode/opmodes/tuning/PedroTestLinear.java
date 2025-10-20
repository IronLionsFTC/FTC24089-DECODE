package org.firstinspires.ftc.teamcode.opmodes.tuning;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.paths.testing.TestPath;
import org.firstinspires.ftc.teamcode.systems.FollowerWrapper;

@Autonomous
public class PedroTestLinear extends OpMode {

    FollowerWrapper follower;
    private boolean started = false;
    private boolean finishedA = false;

    public void init() {
        this.follower = new FollowerWrapper(hardwareMap);
        follower.loadHardware(hardwareMap);
        follower.init();
    }

    public void loop() {
        if (!started) {
            this.follower.follower.followPath(TestPath.intakeA(follower));
            started = true;
        }

        if (!this.finishedA && !this.follower.follower.isBusy()) {
            this.follower.follower.followPath(TestPath.shootA(follower));
        }

        this.follower.follower.update();
    }
}
