package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.math.Vector3;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class FollowerWrapper extends SystemBase {

    public Follower follower;
    public FollowerWrapper(HardwareMap hwmp) {
        this.follower = Constants.createFollower(hwmp);
    }

    @Override
    public void loadHardware(HardwareMap hwmp) { }

    @Override
    public void init() { }

    @Override
    public void update(TelemetryManager telemetryManager) {
        this.follower.update();
    }

    public Vector3 position() {
        Pose pose = this.follower.getPose();
        return new Vector3(
            pose.getX(), pose.getY(), 0
        );
    }

    public Vector3 velocity() {
        Vector pose = this.follower.getVelocity();
        return new Vector3(
                pose.getXComponent(), pose.getYComponent(), 0
        );
    }
}
