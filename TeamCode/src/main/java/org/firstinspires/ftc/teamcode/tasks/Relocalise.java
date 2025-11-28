package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Turret;

public class Relocalise extends Task {
    private Turret turret;

    public Relocalise(Turret turret) {
        this.turret = turret;
    }

    @Override
    public void init() {
        this.turret.relocalise();
    }

    @Override
    public boolean finished() {
        return true;
    }
}
