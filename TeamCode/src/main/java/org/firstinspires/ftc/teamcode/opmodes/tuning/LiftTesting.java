package org.firstinspires.ftc.teamcode.opmodes.tuning;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Lift;

@TeleOp
public class LiftTesting extends TaskOpMode {
    private Lift lift;

    public Jobs spawn() {
        this.lift = new Lift();

        return Jobs.create()
                .registerSystem(lift);
    }
}
