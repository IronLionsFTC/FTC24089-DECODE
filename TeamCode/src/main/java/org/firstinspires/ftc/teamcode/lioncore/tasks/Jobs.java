package org.firstinspires.ftc.teamcode.lioncore.tasks;

import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jobs {
    private List<Task> tasks;
    private List<SystemBase> systems;

    private Jobs(List<Task> tasks, List<SystemBase> systems) {
        this.tasks = tasks;
        this.systems = systems;
    }

    public static Jobs create() {
        return new Jobs(new ArrayList<>(), new ArrayList<>());
    }

    public Jobs registerSystem(SystemBase system) {
        this.systems.add(system);
        return this;
    }

    public Jobs addTask(Task task) {
        this.tasks.add(task);
        return this;
    }

    public Jobs registerSystems(SystemBase... systems) {
        this.systems.addAll(Arrays.asList(systems));
        return this;
    }

    public Jobs addTasks(Task... tasks) {
        this.tasks.addAll(Arrays.asList(tasks));
        return this;
    }

    public Jobs addSeries(Task... tasks) {
        this.tasks.add(new Series(tasks));
        return this;
    }

    public Task compileTask() {
        return new Parallel(this.tasks);
    }

    public List<SystemBase> getSystems() {
        return this.systems;
    }
}