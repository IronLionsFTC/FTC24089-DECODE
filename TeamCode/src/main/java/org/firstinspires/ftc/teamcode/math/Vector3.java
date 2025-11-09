package org.firstinspires.ftc.teamcode.math;

public class Vector3 {
    public double x, y, z;
    public Vector3() { this(0,0,0); }
    public Vector3(double x, double y, double z) { this.x=x; this.y=y; this.z=z; }
    public Vector3 add(Vector3 o) { return new Vector3(x+o.x, y+o.y, z+o.z); }
    public Vector3 sub(Vector3 o) { return new Vector3(x-o.x, y-o.y, z-o.z); }
    public Vector3 scale(double s) { return new Vector3(x*s, y*s, z*s); }
    public double dot(Vector3 o) { return x*o.x + y*o.y + z*o.z; }
    public double length() { return Math.sqrt(x*x + y*y + z*z); }
    public Vector3 normalize() {
        double L = length();
        if (L == 0) return new Vector3(0,0,0);
        return new Vector3(x/L, y/L, z/L);
    }
    public String toString() { return String.format("Vector3(%.4f, %.4f, %.4f)", x,y,z); }

    @FunctionalInterface
    public interface Vector3Supplier {
        Vector3 get();
    }
    public Vector3 multiply(double scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3 cross(Vector3 other) {
        return new Vector3(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }
}
