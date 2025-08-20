package org.firstinspires.ftc.teamcode.lioncore.tasks;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskOpMode extends LinearOpMode {
    private TaskBase task;

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
    public void runOpMode() {

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

        if (this.isStopRequested()) return;
        this.waitForStart();

        boolean running = true;
        task.init();

        while (running) {

            // Fully update all sensor values, motor positions, ect, once per loop cycle.
            for (LynxModule hub : this.hubs) {
                hub.clearBulkCache();
            }

            this.task.run();
            if (this.task.finished()) {
                running = false;
                task.end(false);
            }
            if (!this.opModeIsActive()) {
                running = false;
                task.end(true);
            }

            for (SystemBase system : this.systems) {
                system.update();
            }

            this.mainloop();
        }
    }
}
