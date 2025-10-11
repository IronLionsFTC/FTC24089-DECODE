package org.firstinspires.ftc.teamcode.systems;

import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.lioncore.hardware.ColourSensor;
import org.firstinspires.ftc.teamcode.lioncore.systems.SystemBase;
import org.firstinspires.ftc.teamcode.parameters.Hardware;
import org.firstinspires.ftc.teamcode.parameters.Software;

import java.util.ArrayList;

public class ColourChamber extends SystemBase {

    public enum Ball {
        Purple,
        Green,
        None
    }

    private static final int BUFFER_SIZE = 5;

    private ColourSensor firstLeft;
    private ColourSensor secondLeft;
    private ColourSensor thirdLeft;

    private final ArrayList<Ball> bottomBuffer = new ArrayList<>();
    private final ArrayList<Ball> middleBuffer = new ArrayList<>();
    private final ArrayList<Ball> topBuffer = new ArrayList<>();

    private Ball bottom = Ball.None;
    private Ball middle = Ball.None;
    private Ball top = Ball.None;

    public ColourChamber() {}

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
        // Read HSV and distance data
        float[] firstHSV = this.firstLeft.getHSV();
        float[] secondHSV = this.secondLeft.getHSV();
        float[] thirdHSV = this.thirdLeft.getHSV();

        double firstDistance = this.firstLeft.getDistance();
        double secondDistance = this.secondLeft.getDistance();
        double thirdDistance = this.thirdLeft.getDistance();

        // Determine detected color for each sensor
        Ball bottomReading = detectColor(this.firstLeft);
        Ball middleReading = detectColor(this.secondLeft);
        Ball topReading = detectColor(this.thirdLeft);

        // Update buffers
        updateBuffer(bottomBuffer, bottomReading);
        updateBuffer(middleBuffer, middleReading);
        updateBuffer(topBuffer, topReading);

        // Compute stable color for each position
        this.bottom = computeStableColor(bottomBuffer);
        this.middle = computeStableColor(middleBuffer);
        this.top = computeStableColor(topBuffer);

        // Telemetry
        if (Software.HSVFilter.showRaw) {
            telemetry.addData("First HSV", formatHSV(firstHSV));
            telemetry.addData("Second HSV", formatHSV(secondHSV));
            telemetry.addData("Third HSV", formatHSV(thirdHSV));
            telemetry.addData("FIRST DISTANCE", firstDistance);
            telemetry.addData("SECOND DISTANCE", secondDistance);
            telemetry.addData("THIRD DISTANCE", thirdDistance);
        }

        telemetry.addData("BOTTOM", this.bottom);
        telemetry.addData("MIDDLE", this.middle);
        telemetry.addData("TOP", this.top);
    }

    /** Detects a single instantaneous color reading from a sensor */
    private Ball detectColor(ColourSensor sensor) {
        if (sensor.matchFilter(
                Software.HSVFilter.Purple.H.min,
                Software.HSVFilter.Purple.H.max,
                Software.HSVFilter.Purple.S.min,
                Software.HSVFilter.Purple.S.max,
                Software.HSVFilter.Purple.V.min,
                Software.HSVFilter.Purple.V.max
        )) {
            return Ball.Purple;
        } else if (sensor.matchFilter(
                Software.HSVFilter.Green.H.min,
                Software.HSVFilter.Green.H.max,
                Software.HSVFilter.Green.S.min,
                Software.HSVFilter.Green.S.max,
                Software.HSVFilter.Green.V.min,
                Software.HSVFilter.Green.V.max
        )) {
            return Ball.Green;
        } else {
            return Ball.None;
        }
    }

    /** Keeps buffer size within limit */
    private void updateBuffer(ArrayList<Ball> buffer, Ball newReading) {
        if (buffer.size() >= BUFFER_SIZE) {
            buffer.remove(0);
        }
        buffer.add(newReading);
    }

    /** Returns Purple/Green only if the entire buffer matches that color */
    private Ball computeStableColor(ArrayList<Ball> buffer) {
        if (buffer.size() < BUFFER_SIZE) {
            return Ball.None; // not enough samples yet
        }

        boolean allPurple = buffer.stream().allMatch(b -> b == Ball.Purple);
        boolean allGreen = buffer.stream().allMatch(b -> b == Ball.Green);

        if (allPurple) return Ball.Purple;
        if (allGreen) return Ball.Green;
        return Ball.None;
    }

    private String formatHSV(float[] hsv) {
        return String.format("H: %.1f  S: %.2f  V: %.2f", hsv[0], hsv[1], hsv[2]);
    }

    public Ball getBottom() { return this.bottom; }
    public Ball getMiddle() { return this.middle; }
    public Ball getTop() { return this.top; }
}
