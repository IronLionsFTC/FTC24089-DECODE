package org.firstinspires.ftc.teamcode.tasks;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.lioncore.tasks.Task;
import org.firstinspires.ftc.teamcode.systems.Shooter;

public class IndicatorLightColour extends Task {

    private Shooter target;
    private Shooter rpm;
    private Servo light;

    public IndicatorLightColour(Shooter target, Shooter rpm, Servo light) {
        this.target = target;
        this.rpm = rpm;
        this.light = light;

    }

    @Override
    public void init() {

    }

    @Override
    public void run() {
        if (this.target.getTargetRPM() < this.rpm.getRPM()*0.9)
            this.light.setPosition(0.444);
        else if (this.rpm.getState() == Shooter.State.Target)
            this.light.setPosition(0.277);
        else
            this.light.setPosition(0.0);
    }

    @Override
    public boolean finished() {
        return false;
    }
}
