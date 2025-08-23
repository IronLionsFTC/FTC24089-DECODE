package org.firstinspires.ftc.teamcode.lioncore.tasks;

public abstract class TaskBase {
    public void init() {}
    public void run() {}
    public boolean finished() { return false; }
    public void end(boolean interrupted) { }
}