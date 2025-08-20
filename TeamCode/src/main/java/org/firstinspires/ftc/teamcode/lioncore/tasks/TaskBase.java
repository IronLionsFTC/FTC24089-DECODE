package com.core.tasks;

public abstract class TaskBase {
    void init() {}
    void run() {}
    boolean finished() { return false; }
    void end(boolean interrupted) { }
}