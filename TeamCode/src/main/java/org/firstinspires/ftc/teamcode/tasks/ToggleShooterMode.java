package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Turret;

public class ToggleShooterMode extends Task {

    private Turret turret;

    public ToggleShooterMode(Turret turret) {
        this.turret = turret;
    }

    @Override
    public void init() {
        switch (turret.getState()) {
            case Tracking:
                this.turret.setState(Turret.State.Shooting);
                break;
            default:
                this.turret.setState(Turret.State.Tracking);
                break;
        }
    }

    @Override
    public boolean finished() {
        return true;
    }
}
