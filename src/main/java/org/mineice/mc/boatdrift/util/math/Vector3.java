package org.mineice.mc.boatdrift.util.math;

import org.bukkit.World;

import java.util.function.Function;
import java.util.stream.Stream;

public class Vector3 {
    public double x, y, z;

    public Vector3(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 clone() {
        return new Vector3(x, y, z);
    }

    public void set(Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public void add(Vector3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void sub(Vector3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public void mul(double a) {
        x *= a;
        y *= a;
        z *= a;
    }

    public void div(double a) {
        x /= a;
        y /= a;
        z /= a;
    }

    public void transform(Matrix4x4 mat) {
        double newX, newY, newZ;
        newX = x * mat.c11 + y * mat.c12 + z * mat.c13 + mat.c14;
        newY = x * mat.c21 + y * mat.c22 + z * mat.c23 + mat.c24;
        newZ = x * mat.c31 + y * mat.c32 + z * mat.c33 + mat.c34;
        x = newX;
        y = newY;
        z = newZ;
    }

    public void rot(Quaternion q) {
        transform(Matrix4x4.rotation(q));
    }

    public double element(int idx) {
        return switch (idx) {
            case 0 -> x;
            case 1 -> y;
            case 2 -> z;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    public Vector3 opposite() {
        return new Vector3(-x, -y, -z);
    }

    public Vector3 normalized() {
        Vector3 v = clone();
        v.normalize();
        return v;
    }

    public void normalize() {
        var l = 1 / length();
        x *= l;
        y *= l;
        z *= l;
    }

    public static double dot(Vector3 a, Vector3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static Vector3 cross(Vector3 left, Vector3 right) {
        return new Vector3(
                left.y * right.z - left.z * right.y,
                left.z * right.x - left.x * right.z,
                left.x * right.y - left.y * right.x
        );
    }

    public static Vector3 lerp(Vector3 a, Vector3 b, double t) {
        Vector3 res = mul(a, 1 - t);
        res.add(mul(b, t));
        return res;
    }

    public Stream<Double> stream() {
        return Stream.of(x,y,z);
    }

    public void operate(Function2<Integer, Double, Double> function) {
        x = function.apply(0, x);
        y = function.apply(1, y);
        z = function.apply(2, z);
    }

    public void operate(Function<Double, Double> function) {
        x = function.apply(x);
        y = function.apply(y);
        z = function.apply(z);
    }

    public static Vector3 projectOnPlane(Vector3 vec, Vector3 planeNormal) {
        return sub(vec, mul(planeNormal, dot(vec, planeNormal)));
    }

    public double sqrLength() {
        return x * x + y * y + z * z;
    }

    public double length() {
        return Math.sqrt(sqrLength());
    }

    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    public static final Vector3 ONE = new Vector3(1, 1, 1);
    public static final Vector3 LEFT = new Vector3(-1, 0, 0);
    public static final Vector3 RIGHT = new Vector3(1, 0, 0);
    public static final Vector3 UP = new Vector3(0, 1, 0);
    public static final Vector3 DOWN = new Vector3(0, -1, 0);
    public static final Vector3 FWD = new Vector3(0, 0, 1);
    public static final Vector3 BWD = new Vector3(0, 0, -1);

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector3 b) {
            return (x == b.x) && (y == b.y) && (z == b.z);
        }
        return false;
    }

    public static Vector3 add(Vector3 a, Vector3 b) {
        return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static Vector3 sub(Vector3 a, Vector3 b) {
        return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Vector3 mul(Vector3 a, double b) {
        return new Vector3(a.x * b, a.y * b, a.z * b);
    }

    public static Vector3 div(Vector3 a, double b) {
        return new Vector3(a.x / b, a.y / b, a.z / b);
    }

    public org.bukkit.util.Vector toBukkit() {
        return new org.bukkit.util.Vector(x, y, z);
    }

    public org.bukkit.Location toLocation(World world) {
        return new org.bukkit.Location(world, x, y, z);
    }

    public static Vector3 fromBukkit(org.bukkit.util.Vector v) {
        return new Vector3(v.getX(), v.getY(), v.getZ());
    }


    public Vector3 fromStream(Stream<Double> stream) {
        var v = Vector3.ZERO;
        var it = stream.iterator();
        v.x = it.next();
        v.y = it.next();
        v.z = it.next();
        return v;
    }
}
