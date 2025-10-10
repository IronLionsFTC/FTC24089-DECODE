package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.ColourChamber;
import org.firstinspires.ftc.teamcode.systems.Intake;

public class SpinIntakeToKeepBallsIn extends Task {

    private Intake intake;
    private ColourChamber colours;

    public SpinIntakeToKeepBallsIn(Intake intake, ColourChamber colours) {
        this.intake = intake;
        this.colours = colours;
    }

    @Override
    public void run() {
        if (this.colours.getBottom() != ColourChamber.Ball.None && this.intake.getState() == Intake.State.Zero) {
            this.intake.setState(Intake.State.Idle);
        } else if (this.colours.getBottom() == ColourChamber.Ball.None && this.intake.getState() == Intake.State.Idle) {
            this.intake.setState(Intake.State.Zero);
        }
    }
}
