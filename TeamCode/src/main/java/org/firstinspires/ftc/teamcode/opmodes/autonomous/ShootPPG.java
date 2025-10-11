package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.cache.Ordering;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;
import org.firstinspires.ftc.teamcode.tasks.IntakeUntilFull;
import org.firstinspires.ftc.teamcode.tasks.TASK;

@Autonomous
public class ShootPPG extends TaskOpMode {

    public Intake intake;
    public Transfer transfer;
    public Shooter shooter;
    public ColourChamber colourChamber;
    public Ordering ordering;
    public Ordering motif;

    @Override
    public Jobs spawn() {

        this.intake = new Intake();
        this.transfer = new Transfer();
        this.shooter = new Shooter();
        this.colourChamber = new ColourChamber();
        this.ordering = new Ordering();
        this.motif = new Ordering();

        this.motif.set(
                ColourChamber.Ball.Purple,
                ColourChamber.Ball.Purple,
                ColourChamber.Ball.Green
        );

        return Jobs.create()

                .addSeries(
                    new IntakeUntilFull(intake, transfer, colourChamber, ordering),
                    TASK.airsort(intake, transfer, shooter, colourChamber, motif, ordering, panelsTelemetry)
                )
                .endWhenTasksFinished()

                .registerSystem(intake)
                .registerSystem(transfer)
                .registerSystem(shooter)
                .registerSystem(colourChamber);

    }

}
