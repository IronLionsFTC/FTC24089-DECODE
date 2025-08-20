package com.core.systems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class SystemBase {
    /**
     * Idiomatic place to load hardware from the hardware map, run post initialisation
     * @param hardwareMap The hardware map post initialisation.
     */
    public abstract void loadHardware(HardwareMap hardwareMap);

    /**
     * Idiomatic place to create the initial state (servo positions, ect) post initialisation
     */
    public abstract void init();

    /**
     * Runs once per cycle, after task execution. Should be used to set motor powers and servo positions based on state
     */
    public abstract void update();
}