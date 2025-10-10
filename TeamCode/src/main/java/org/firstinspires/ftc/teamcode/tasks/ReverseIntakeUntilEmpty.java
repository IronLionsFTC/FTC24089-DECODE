package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.Intake;
import org.firstinspires.ftc.teamcode.systems.Transfer;

public class ReverseIntakeUntilEmpty extends Task {

    private Intake intake;
    private Transfer transfer;
    private ColourChamber colours;

    public ReverseIntakeUntilEmpty(Intake intake, Transfer transfer, ColourChamber colours) {
        this.intake = intake;
        this.transfer = transfer;
        this.colours = colours;
    }

    @Override
    public void init() {
        this.intake.setState(Intake.State.Negative);
        this.transfer.setState(Transfer.State.Reverse);
    }

    @Override
    public boolean finished() {
        return (colours.getBottom() == ColourChamber.Ball.None && colours.getMiddle() == ColourChamber.Ball.None && colours.getTop() == ColourChamber.Ball.None);
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Zero);
        this.transfer.setState(Transfer.State.Rest);
    }

}
