package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Intake;

public class ToggleIntake extends Task {
    private Intake intake;

    public ToggleIntake(Intake intake) {
        this.intake = intake;
    }

    @Override
    public void init() {
        this.intake.setRunning(!this.intake.isRunning());
    }

    @Override
    public boolean finished() {
         return true;
    }
}
