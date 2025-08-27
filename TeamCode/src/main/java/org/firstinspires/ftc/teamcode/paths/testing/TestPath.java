package org.firstinspires.ftc.teamcode.paths.testing;

import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;

import org.firstinspires.ftc.teamcode.paths.core.Core;

public class TestPath {
    public static Point start = new Point(0, 0, Point.CARTESIAN);
    public static Point corner = new Point(10, 0, Point.CARTESIAN);
    public static Point end = new Point(10, -20, Point.CARTESIAN);
    public static PathChain firstSegment = Core.chain(Core.simpleLine(start, corner, Math.toRadians(45)));
    public static PathChain secondSegment = Core.chain(Core.simpleLine(corner, end, Math.toRadians(-45)));
}
