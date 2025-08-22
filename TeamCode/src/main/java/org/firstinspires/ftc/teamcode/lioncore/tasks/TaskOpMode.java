package org.firstinspires.ftc.teamcode.lioncore.tasks;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.lioncore.control.Controller;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskOpMode extends OpMode {
    private TaskBase task;
    public Controller controller1;
    public Controller controller2;

    private List<LynxModule> hubs;
    private ArrayList<SystemBase> systems;

    public class Jobs {
        public TaskBase task;
        public ArrayList<SystemBase> systems;
    }

    /**
     * Create all systems and tasks and return them. Do not initialise the systems.
     * @return Return a "Jobs" item containing task and systems
     */
    abstract Jobs spawn();

    /**
     * Empty by default, allows users to override with something they want to happen once all commands in this iteration have been executed.
     * This could be used for something like bulk reading, updating a counter, ect.
     */
    void mainloop() {

    }

    @Override
    public void init() {

        Jobs jobs = this.spawn();
        this.task = jobs.task;
        this.systems = jobs.systems;

        for (SystemBase system : this.systems) {
            system.loadHardware(this.hardwareMap);
            system.init();
        }

        // Bulk hardware operations
        this.hubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : this.hubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
            hub.clearBulkCache();
        }

        this.controller1 = new Controller(gamepad1);
        this.controller2 = new Controller(gamepad2);
    }

    @Override
    public void loop() {

        task.init();
        this.controller1.update();
        this.controller2.update();

        // Fully update all sensor values, motor positions, ect, once per loop cycle.
        for (LynxModule hub : this.hubs) {
            hub.clearBulkCache();
        }

        this.task.run();
        if (this.task.finished()) {
            task.end(false);
        }

        for (SystemBase system : this.systems) {
            system.update();
        }

        this.mainloop();
    }
}
