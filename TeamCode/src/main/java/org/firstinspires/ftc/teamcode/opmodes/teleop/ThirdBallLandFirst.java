package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cache.Ordering;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;
import org.firstinspires.ftc.teamcode.tasks.IntakeUntilFull;
import org.firstinspires.ftc.teamcode.tasks.RevFlywheel;
import org.firstinspires.ftc.teamcode.tasks.ShootOneNoBlock;

@TeleOp
public class ThirdBallLandFirst extends TaskOpMode {

    private Intake intake;
    private Shooter shooter;
    private Transfer transfer;
    private ColourChamber colourChamber;
    private Ordering ordering;

    @Override
    public Jobs spawn() {

        this.intake = new Intake();
        this.shooter = new Shooter();
        this.transfer = new Transfer();
        this.colourChamber = new ColourChamber();
        this.ordering = new Ordering();

        return Jobs.create()

                .addSeries(
                        new IntakeUntilFull(intake, transfer, colourChamber, ordering),
                        new RevFlywheel(shooter, 3300, 0),
                        new ShootOneNoBlock(intake, transfer, shooter, 3300, 0),
                        new ShootOneNoBlock(intake, transfer, shooter, 3300, 0),
                        new ShootOneNoBlock(intake, transfer, shooter, 2600, 1.4)
                )

                .registerSystem(shooter)
                .registerSystem(transfer)
                .registerSystem(colourChamber)
                .registerSystem(intake)
                .registerSystem(shooter);

    }
}
