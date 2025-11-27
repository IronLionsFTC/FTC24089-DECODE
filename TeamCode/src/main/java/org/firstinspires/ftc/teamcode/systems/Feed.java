package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;

public class Feed extends SystemBase {

    public enum State {
        Rest,
        Slow,
        Full,
        Eject
    }

    public Feed() {}

    @Override
    public void loadHardware(HardwareMap hardwareMap) {

    }
}
