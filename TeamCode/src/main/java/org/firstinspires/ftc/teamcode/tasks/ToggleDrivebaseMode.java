package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Drivebase;

public class ToggleDrivebaseMode extends Task {

    private final Drivebase drivebase;

    public ToggleDrivebaseMode(Drivebase drivebase) {
        this.drivebase = drivebase;
    }

    @Override
    public void init() {
        switch (this.drivebase.getState()) {
            case Manual:
                this.drivebase.setState(Drivebase.State.AutoAlign);
                break;
            case AutoAlign:
                this.drivebase.setState(Drivebase.State.Manual);
                break;
        }
    }

    @Override
    public boolean finished() {
        return true;
    }
}
