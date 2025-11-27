package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.cache.Ordering;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;

public class IntakeUntilFull extends Task {

    private Intake intake;
    private Transfer transfer;
    private ColourChamber colours;
    private Ordering ordering;

    public IntakeUntilFull(Intake intake, Transfer transfer, ColourChamber colours, Ordering ordering) {
        this.intake = intake;
        this.transfer = transfer;
        this.colours = colours;
        this.ordering = ordering;
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
        boolean done = (colours.getBottom() != ColourChamber.Ball.None && colours.getMiddle() != ColourChamber.Ball.None && colours.getTop() != ColourChamber.Ball.None);
        if (done) {
            this.ordering.set(
                    colours.getBottom(),
                    colours.getMiddle(),
                    colours.getTop()
            );
        }
        return done;
    }

    @Override
    public void end(boolean _) {
        this.intake.setState(Intake.State.Idle);
        this.transfer.setState(Transfer.State.Rest);
    }

}
