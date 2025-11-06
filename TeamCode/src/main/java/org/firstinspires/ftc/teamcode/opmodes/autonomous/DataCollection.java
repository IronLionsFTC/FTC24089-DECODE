package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.cache.Ordering;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Jobs;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Sleep;
import org.firstinspires.ftc.teamcode.lioncore.tasks.TaskOpMode;
import org.firstinspires.ftc.teamcode.lioncore.tasks.WaitUntil;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;
import org.firstinspires.ftc.teamcode.tasks.IntakeUntilFull;
import org.firstinspires.ftc.teamcode.tasks.ShootOne;
import org.firstinspires.ftc.teamcode.tasks.TASK;

@TeleOp
public class DataCollection extends TaskOpMode {

    public Intake intake;
    public Transfer transfer;
    public Shooter shooter;
    public ColourChamber colourChamber;
    public Ordering ordering;

    @Config
    @Configurable
    public static class DataCollectionConstants {
        public static double RPM = 2000;
        public static double ANGLE = 0;
    }

    @Override
    public Jobs spawn() {

        this.intake = new Intake();
        this.transfer = new Transfer();
        this.shooter = new Shooter(() -> 0);
        this.colourChamber = new ColourChamber();
        this.ordering = new Ordering();

        return Jobs.create()

                .addSeries(
                    new IntakeUntilFull(intake, transfer, colourChamber, ordering),
                    new WaitUntil(() -> controller1.X.pressed()),
                    new ShootOne(intake, transfer, shooter, DataCollectionConstants.RPM, DataCollectionConstants.ANGLE),
                    new WaitUntil(() -> controller1.X.pressed()),
                    new ShootOne(intake, transfer, shooter, DataCollectionConstants.RPM, DataCollectionConstants.ANGLE),
                    new WaitUntil(() -> controller1.X.pressed()),
                    new ShootOne(intake, transfer, shooter, DataCollectionConstants.RPM, DataCollectionConstants.ANGLE),
                    new Sleep(1)
                )
                .endWhenTasksFinished()

                .registerSystem(intake)
                .registerSystem(transfer)
                .registerSystem(shooter)
                .registerSystem(colourChamber);

    }

}
