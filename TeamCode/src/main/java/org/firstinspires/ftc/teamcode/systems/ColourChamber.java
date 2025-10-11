package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.ColourSensor;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.parameters.Software;

public class ColourChamber extends SystemBase {

    public enum Ball {
        Purple,
        Green,
        None
    }

    private ColourSensor firstLeft;
    private ColourSensor secondLeft;
    private ColourSensor thirdLeft;

    private Ball top = Ball.None;
    private Ball middle = Ball.None;
    private Ball bottom = Ball.None;

    public ColourChamber() {

    }

    @Override
    public void loadHardware(HardwareMap hwmp) {
        this.firstLeft = ColourSensor.load(hwmp, Hardware.ColourSensors.Names.firstLeft);
        this.secondLeft = ColourSensor.load(hwmp, Hardware.ColourSensors.Names.secondLeft);
        this.thirdLeft = ColourSensor.load(hwmp, Hardware.ColourSensors.Names.thirdLeft);
    }

    @Override
    public void init() {}

    @Override
    public void update(TelemetryManager telemetry) {
        float[] firstHSV = this.firstLeft.getHSV();
        float[] secondHSV = this.secondLeft.getHSV();
        float[] thirdHSV = this.thirdLeft.getHSV();

        double firstDistance = this.firstLeft.getDistance();
        double secondDistance = this.secondLeft.getDistance();
        double thirdDistance = this.thirdLeft.getDistance();

        String firstHSVString = String.format("H: %.1f  S: %.2f  V: %.2f", firstHSV[0], firstHSV[1], firstHSV[2]);
        String secondHSVString = String.format("H: %.1f  S: %.2f  V: %.2f", secondHSV[0], secondHSV[1], secondHSV[2]);
        String thirdHSVString = String.format("H: %.1f  S: %.2f  V: %.2f", thirdHSV[0], thirdHSV[1], thirdHSV[2]);

        if (this.firstLeft.matchFilter(
                Software.HSVFilter.Purple.H.min,
                Software.HSVFilter.Purple.H.max,
                Software.HSVFilter.Purple.S.min,
                Software.HSVFilter.Purple.S.max,
                Software.HSVFilter.Purple.V.min,
                Software.HSVFilter.Purple.V.max
        )) {
            this.bottom = Ball.Purple;
        } else if (this.firstLeft.matchFilter(
                Software.HSVFilter.Green.H.min,
                Software.HSVFilter.Green.H.max,
                Software.HSVFilter.Green.S.min,
                Software.HSVFilter.Green.S.max,
                Software.HSVFilter.Green.V.min,
                Software.HSVFilter.Green.V.max
        )) {
            this.bottom = Ball.Green;
        } else {
            this.bottom = Ball.None;
        }

        if (this.secondLeft.matchFilter(
                Software.HSVFilter.Purple.H.min,
                Software.HSVFilter.Purple.H.max,
                Software.HSVFilter.Purple.S.min,
                Software.HSVFilter.Purple.S.max,
                Software.HSVFilter.Purple.V.min,
                Software.HSVFilter.Purple.V.max
        )) {
            this.middle = Ball.Purple;
        } else if (this.secondLeft.matchFilter(
                Software.HSVFilter.Green.H.min,
                Software.HSVFilter.Green.H.max,
                Software.HSVFilter.Green.S.min,
                Software.HSVFilter.Green.S.max,
                Software.HSVFilter.Green.V.min,
                Software.HSVFilter.Green.V.max
        )) {
            this.middle = Ball.Green;
        } else {
            this.middle = Ball.None;
        }

        if (this.thirdLeft.matchFilter(
                Software.HSVFilter.Purple.H.min,
                Software.HSVFilter.Purple.H.max,
                Software.HSVFilter.Purple.S.min,
                Software.HSVFilter.Purple.S.max,
                Software.HSVFilter.Purple.V.min,
                Software.HSVFilter.Purple.V.max
        )) {
            this.top = Ball.Purple;
        } else if (this.thirdLeft.matchFilter(
                Software.HSVFilter.Green.H.min,
                Software.HSVFilter.Green.H.max,
                Software.HSVFilter.Green.S.min,
                Software.HSVFilter.Green.S.max,
                Software.HSVFilter.Green.V.min,
                Software.HSVFilter.Green.V.max
        )) {
            this.top = Ball.Green;
        } else {
            this.top = Ball.None;
        }

        if (Software.HSVFilter.showRaw) {
            telemetry.addData("First HSV", firstHSVString);
            telemetry.addData("Second HSV", secondHSVString);
            telemetry.addData("Third HSV", thirdHSVString);
            telemetry.addData("FIRST DISTANCE", firstDistance);
            telemetry.addData("SECOND DISTANCE", secondDistance);
            telemetry.addData("THIRD DISTANCE", thirdDistance);
        }

        telemetry.addData("BOTTOM", this.bottom);
        telemetry.addData("MIDDLE", this.middle);
        telemetry.addData("TOp", this.top);
    }

    public Ball getBottom() { return this.bottom; }
    public Ball getMiddle() { return this.middle; }
    public Ball getTop() { return this.top; }
}
