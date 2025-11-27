package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.LionServo;
import org.firstinspires.ftc.teamcode.parameters.Hardware;

public class Limelight {

    private Limelight3A limelight;
    private LionServo servo;

    public Limelight(HardwareMap hwmp) {
        this.limelight = hwmp.get(Limelight3A.class, "limelight");
        // this.servo = LionServo.single(hwmp, Hardware.Servos.Names.limelightServo, Hardware.Servos.ZeroPositions.limelight);
        this.limelight.pipelineSwitch(1);
    }

    public void start() { this.limelight.start(); }
    public void stop() { this.limelight.stop(); }

    public Double getBall() {
        LLResult result = limelight.getLatestResult();
        if (result == null) return null;
        double[] arr = result.getPythonOutput();
        if (arr == null) return null;
        if (arr.length < 2) return null;
        if (arr[0] == -1) return null;
        return arr[0];
    }
}
