package org.firstinspires.ftc.teamcode.lioncore.hardware;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColourSensor {
    private RevColorSensorV3 hardware;

    private ColourSensor(HardwareMap hwmp, String name) {
        this.hardware = hwmp.get(RevColorSensorV3.class, name);
    }

    public static ColourSensor load(HardwareMap hwmp, String name) {
        return new ColourSensor(hwmp, name);
    }

    /**
     * Get the HSV values.
     * @return Hue (0-360), Saturation (0-1), Value (0-1)
     */
    public float[] getHSV() {
        int r = this.hardware.red();
        int g = this.hardware.green();
        int b = this.hardware.blue();

        float[] hsv = new float[3];
        Color.RGBToHSV(r, g, b, hsv);

        return hsv;
    }

    /**
     * Matches the current colour detected to a HSV filter.
     * @param minH Minimum hue (0-360)
     * @param maxH Maximum hue (0-360)
     * @param minS Minimum saturation (0-1)
     * @param maxS Maximum saturation (0-1)
     * @param minV Minimum value (0-1)
     * @param maxV Maximum value (0-1)
     * @return Is the colour a match
     */
    public boolean matchFilter(double minH, double maxH, double minS, double maxS, double minV, double maxV) {
        float[] hsv = getHSV();

        float h = hsv[0];
        float s = hsv[1];
        float v = hsv[2];

        boolean hm = false;
        boolean sm = false;
        boolean vm = false;

        if (minH < maxH) {
            hm = minH <= h && h <= maxH;
        } else {
            hm = h <= minH || h >= maxH;
        }

        sm = minS <= s && s <= maxS;
        vm = minV <= v && v <= maxV;

        return hm && sm && vm;
    }

    /**
     * Get the proximity of the nearest object to the sensor
     * @return The distance in MM
     */
    public double getDistance() {
        return this.hardware.getDistance(DistanceUnit.MM);
    }

    /**
     * Is there an object within the threshold
     * @param threshold Maximum distance away (mm)
     * @return
     */
    public boolean detect(double threshold) {
        return this.hardware.getDistance(DistanceUnit.MM) <= threshold;
    }
}
