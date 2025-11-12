package org.firstinspires.ftc.teamcode.lioncore.tasks;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.gamepad.PanelsGamepad;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.lioncore.control.Controller;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;

import java.util.List;

public abstract class TaskOpMode extends OpMode {
    private Task task;
    private boolean endWhenTasksFinished;
    private boolean taskHasFinished;
    public Controller controller1;
    public Controller controller2;

    private List<LynxModule> hubs;
    private List<SystemBase> systems;
    public final TelemetryManager panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

    /**
     * Create all systems and tasks and return them. Do not initialise the systems.
     * @return Return a "Jobs" item containing task and systems
     */
    public abstract Jobs spawn();

    /**
     * Empty by default, allows users to override with something they want to happen once all commands in this iteration have been executed.
     * This could be used for something like bulk reading, updating a counter, etc.
     */
    public void mainloop() {

    }

    @Override
    public void init() {

        this.telemetry = new MultipleTelemetry(
                telemetry,
                FtcDashboard.getInstance().getTelemetry()
        );

        this.controller1 = new Controller(
                PanelsGamepad.INSTANCE.getFirstManager().asCombinedFTCGamepad(gamepad1)
        );

        this.controller2 = new Controller(
                PanelsGamepad.INSTANCE.getSecondManager().asCombinedFTCGamepad(gamepad2)
        );

        Jobs jobs = this.spawn();
        this.task = jobs.compileTask();
        this.systems = jobs.getSystems();
        this.endWhenTasksFinished = jobs.getEndWhenTasksFinished();
        this.taskHasFinished = false;

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

        task.init();
    }

    @Override
    public void loop() {

        this.controller1.update(PanelsGamepad.INSTANCE.getFirstManager().asCombinedFTCGamepad(gamepad1));
        this.controller2.update(PanelsGamepad.INSTANCE.getSecondManager().asCombinedFTCGamepad(gamepad2));

        if (!this.taskHasFinished) this.task.run();
        if (this.task.finished() && !this.taskHasFinished) {
            task.end(false);
            this.taskHasFinished = true;
            if (this.endWhenTasksFinished) this.requestOpModeStop();
        }

        for (SystemBase system : this.systems) {
            system.update(panelsTelemetry);
        }

        this.mainloop();
        this.panelsTelemetry.update(telemetry);

        // Fully update all sensor values, motor positions, ect, once per loop cycle.
        for (LynxModule hub : this.hubs) {
           hub.clearBulkCache();
        }
    }
}
