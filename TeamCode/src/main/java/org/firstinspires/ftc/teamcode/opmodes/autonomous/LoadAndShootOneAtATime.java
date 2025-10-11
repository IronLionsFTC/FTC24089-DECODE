package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;
import org.firstinspires.ftc.teamcode.tasks.IntakeUntilFull;
import org.firstinspires.ftc.teamcode.tasks.RevFlywheel;
import org.firstinspires.ftc.teamcode.tasks.ShootOneNoBlock;

@Autonomous
public class LoadAndShootOneAtATime extends TaskOpMode {

    public Intake intake;
    public Transfer transfer;
    public Shooter shooter;
    public ColourChamber colourChamber;

    @Override
    public Jobs spawn() {

        this.intake = new Intake();
        this.transfer = new Transfer();
        this.shooter = new Shooter();
        this.colourChamber = new ColourChamber();

        return Jobs.create()

                .addSeries(
                        new IntakeUntilFull(intake, transfer, colourChamber, panelsTelemetry),
                        new RevFlywheel(shooter, 4400),
                        new ShootOneNoBlock(intake, transfer, shooter, 4400, 0.15),
                        new ShootOneNoBlock(intake, transfer, shooter, 3600, 1.1),
                        new ShootOneNoBlock(intake, transfer, shooter, 3600, 1.1)
                )
                .endWhenTasksFinished()

                .registerSystem(intake)
                .registerSystem(transfer)
                .registerSystem(shooter)
                .registerSystem(colourChamber);

    }

}
