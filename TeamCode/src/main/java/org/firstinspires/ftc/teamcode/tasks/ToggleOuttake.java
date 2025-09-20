package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Outtake;

public class ToggleOuttake extends Task {
    private Outtake outtake;

    public ToggleOuttake(Outtake outtake) {
        this.outtake = outtake;
    }

    @Override
    public void init() {
        this.outtake.setRunning(!this.outtake.isRunning());
    }

    @Override
    public boolean finished() {
         return true;
    }
}
