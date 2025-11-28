package org.firstinspires.ftc.teamcode.opmodes.tuning;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Feed;
import org.firstinspires.ftc.teamcode.systems.Turret;

@TeleOp
public class TurretTuning extends TaskOpMode {

    private Turret turret;
    private Feed feed;

    @Override
    public Jobs spawn() {
        this.turret = new Turret();
        this.feed = new Feed();

        return Jobs.create()
                .registerSystem(feed)
                .registerSystem(turret);
    }
}
