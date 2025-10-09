package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.Shooter;

@TeleOp
public class ShooterTest extends TaskOpMode {

    public Shooter shooter;

    @Override
    public Jobs spawn() {

        this.shooter = new Shooter();

        return Jobs.create()
                .registerSystem(shooter);

    }
}
