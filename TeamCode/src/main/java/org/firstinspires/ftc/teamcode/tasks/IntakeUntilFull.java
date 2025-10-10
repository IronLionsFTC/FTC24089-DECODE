package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Transfer;

public class IntakeUntilFull extends Task {

    private Intake intake;
    private Transfer transfer;
    private ColourChamber colours;

    public IntakeUntilFull(Intake intake, Transfer transfer, ColourChamber colours) {
        this.intake = intake;
        this.transfer = transfer;
        this.colours = colours;
    }

    @Override
    public void init() {
        this.intake.setState(Intake.State.Positive);
        this.transfer.setState(Transfer.State.Intaking);
    }

    @Override
    public void run() {
        if (colours.getTop() != ColourChamber.Ball.None) {
            this.transfer.setState(Transfer.State.Rest);
        }
    }

    @Override
    public boolean finished() {
        return (colours.getBottom() != ColourChamber.Ball.None && colours.getMiddle() != ColourChamber.Ball.None && colours.getTop() != ColourChamber.Ball.None);
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Zero);
        this.transfer.setState(Transfer.State.Rest);
    }

}
