package org.firstinspires.ftc.teamcode.tasks;

import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.teamcode.cache.Ordering;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Series;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Transfer;

public class TASK {
    public static Task airsort(Intake intake, Transfer transfer, Shooter shooter, ColourChamber colourChamber, Ordering motif, Ordering order, TelemetryManager panelsTelemetry) {
        Ordering.AirsortPattern pattern = order.calculateAirSort(motif);

        switch (pattern) {
            case FirstToSecond:
                return new Series(
                        new RevFlywheel(shooter, 4400, 0.3),
                        new ShootOneNoBlock(intake, transfer, shooter, 4400, 0.3),
                        new ShootOneNoBlock(intake, transfer, shooter, 3400, 1.4),
                        new ShootOne(intake, transfer, shooter, 3400, 1)
                );
            case SecondToThird:
                return new Series(
                        new RevFlywheel(shooter, 3400, 1.4),
                        new ShootOneNoBlock(intake, transfer, shooter, 3400, 1.4),
                        new ShootOne(intake, transfer, shooter, 4400, 0.3),
                        new ShootOneNoBlock(intake, transfer, shooter, 3400, 1.4)
                );
            case FirstToThird:
                return new Series(
                        new RevFlywheel(shooter, 4600, 0.25),
                        new ShootOneNoBlock(intake, transfer, shooter, 4400, 0.3),
                        new ShootOneNoBlock(intake, transfer, shooter, 3400, 1.4),
                        new ShootOneNoBlock(intake, transfer, shooter, 3400, 1.4)
                );
            case ThirdToFirst:
                return new Series(
                        new RevFlywheel(shooter, 4600, 0.25),
                        new ShootOneNoBlock(intake, transfer, shooter, 4400, 0.3),
                        new ShootOneNoBlock(intake, transfer, shooter, 4400, 0.3),
                        new ShootOneNoBlock(intake, transfer, shooter, 3400, 1.4)
                );
            default:
                return new Series(
                        new RevFlywheel(shooter, 3400, 1.4),
                        new ShootOneNoBlock(intake, transfer, shooter, 3400, 1.4),
                        new ShootOneNoBlock(intake, transfer, shooter, 3400, 1.4),
                        new ShootOneNoBlock(intake, transfer, shooter, 3400, 1.4)
                );
        }
    }
}
