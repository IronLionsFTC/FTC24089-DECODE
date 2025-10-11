package org.firstinspires.ftc.teamcode.tasks;

import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Transfer;

public class IntakeUntilFull extends Task {

    private Intake intake;
    private Transfer transfer;
    private ColourChamber colours;
    private TelemetryManager telemetry;

    public IntakeUntilFull(Intake intake, Transfer transfer, ColourChamber colours, TelemetryManager telemetry) {
        this.intake = intake;
        this.transfer = transfer;
        this.colours = colours;
        this.telemetry = telemetry;
    }

    @Override
    public void init() {
        this.intake.setState(Intake.State.Positive);
        this.transfer.setState(Transfer.State.Intaking);
        telemetry.addLine("--- INIT ---");
    }

    @Override
    public void run() {
        if (colours.getTop() != ColourChamber.Ball.None) {
            this.transfer.setState(Transfer.State.Rest);
            telemetry.addLine("--- RESTING - TOP BALL OCCUPIED ---");
        }
        telemetry.addLine("--- RUNNING ---");
    }

    @Override
    public boolean finished() {
        telemetry.addLine("--- POLLING FINISHED ---");
        boolean done = (colours.getBottom() != ColourChamber.Ball.None && colours.getMiddle() != ColourChamber.Ball.None && colours.getTop() != ColourChamber.Ball.None);
        telemetry.addData("DONE", done);
        return done;
    }

    @Override
    public void end(boolean _) {
        telemetry.addLine("ENDING");
        this.intake.setState(Intake.State.Zero);
        this.transfer.setState(Transfer.State.Rest);
    }

}
