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

    /**
     * Create an empty Jobs item.
     * @return
     */
    public static Jobs create() {
        return new Jobs(new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Add a System to the Jobs. The system will be continually updated.
     * @param system A System object
     * @return
     */
    public Jobs registerSystem(SystemBase system) {
        this.systems.add(system);
        return this;
    }

    /**
     * Add a task onto the parallel stack for this opmode. This will be run immediately on start, in parallel with every other task.
     * @param task
     * @return
     */
    public Jobs addTask(Task task) {
        this.tasks.add(task);
        return this;
    }

    /**
     * Add multiple Systems to the Jobs. The systems will be continually updated.
     * @param systems Multiple System objects
     * @return
     */
    public Jobs registerSystems(SystemBase... systems) {
        this.systems.addAll(Arrays.asList(systems));
        return this;
    }

    /**
     * Add multiple tasks onto the parallel stack for this opmode. These will be run immediately on start, in parallel with every other task.
     * @param tasks
     * @return
     */
    public Jobs addTasks(Task... tasks) {
        this.tasks.addAll(Arrays.asList(tasks));
        return this;
    }

    /**
     * Add multiple tasks as a single Series task onto the stack. These will be run one after another, but in parallel with every other task.
     * This is the idiomatic method to schedule the runtime of the opmode.
     * @param tasks
     * @return
     */
    public Jobs addSeries(Task... tasks) {
        this.tasks.add(new Series(tasks));
        return this;
    }

    /**
     * Produces a parallel task containing all scheduled tasks.
     * @return
     */
    public Task compileTask() {
        return new Parallel(this.tasks);
    }

    /**
     * Produces a list of all scheduled systems for this opmode.
     * @return
     */
    public List<SystemBase> getSystems() {
        return this.systems;
    }
}