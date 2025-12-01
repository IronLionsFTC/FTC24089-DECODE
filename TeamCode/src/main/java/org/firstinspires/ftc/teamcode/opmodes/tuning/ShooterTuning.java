package org.firstinspires.ftc.teamcode.opmodes.tuning;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Feed;
import org.firstinspires.ftc.teamcode.systems.Turret;
import org.firstinspires.ftc.teamcode.tasks.ControlConfig;

@TeleOp
public class ShooterTuning extends TaskOpMode {

    private Feed feed;
    private Turret turret;

    public Jobs spawn() {

        this.feed = new Feed();
        this.turret = new Turret();

        return Jobs.create()
                .addTask(new ControlConfig(feed, turret))
                .registerSystem(feed)
                .registerSystem(turret);

    }

}
