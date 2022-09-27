package org.mineice.mc.boatdrift.util.math;

public class Quaternion {
    public double x, y, z, w;

    public Quaternion(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static Quaternion mul(Quaternion q1, Quaternion q2) {
        return new Quaternion(
                q1.x * q2.w + q1.y * q2.z - q1.z * q2.y + q1.w * q2.x,
                -q1.x * q2.z + q1.y * q2.w + q1.z * q2.x + q1.w * q2.y,
                q1.x * q2.y - q1.y * q2.x + q1.z * q2.w + q1.w * q2.z,
                -q1.x * q2.x - q1.y * q2.y - q1.z * q2.z + q1.w * q2.w
        );
    }

    public static Quaternion fromAxisAngle(Vector3 axis, double angle) {
        var halfAngle = angle * .5;
        if (Math.abs(halfAngle) < .001)
            return new Quaternion(0, 0, 0, 1);
        var s = Math.sin(halfAngle);
        return new Quaternion(
                axis.x * s,
                axis.y * s,
                axis.z * s,
                Math.cos(halfAngle));
    }

    public Quaternion lookAt(Vector3 sourcePoint, Vector3 destPoint) {
        Vector3 forwardVector = Vector3.sub(destPoint, sourcePoint);
        forwardVector.normalize();

        var dot = Vector3.dot(Vector3.FWD, forwardVector);

        if (Math.abs(dot + 1.0) < 0.000001) {
            return new Quaternion(0, 1, 0, 3.1415926535897932);
        }
        if (Math.abs(dot - 1.0) < 0.000001) {
            return IDENTITY;
        }

        var rotAngle = Math.acos(dot);
        Vector3 rotAxis = Vector3.cross(Vector3.FWD, forwardVector);
        rotAxis.normalize();
        return Quaternion.fromAxisAngle(rotAxis, rotAngle);
    }

    public Quaternion fromEulerAngles(double x, double y, double z) {
        double cy = Math.cos(z * 0.5);
        double sy = Math.sin(z * 0.5);
        double cp = Math.cos(y * 0.5);
        double sp = Math.sin(y * 0.5);
        double cr = Math.cos(x * 0.5);
        double sr = Math.sin(x * 0.5);

        return new Quaternion(
                cr * cp * cy + sr * sp * sy,
                sr * cp * cy - cr * sp * sy,
                cr * sp * cy + sr * cp * sy,
                cr * cp * sy - sr * sp * cy
        );
    }

    public Vector3 toEulerAngles() {
        Vector3 angles = new Vector3(0, 0, 0);
        // roll (x-axis rotation)
        double sinr_cosp = 2 * (w * x + y * z);
        double cosr_cosp = 1 - 2 * (x * x + y * y);
        angles.x = Math.atan2(sinr_cosp, cosr_cosp);

        // yaw (y-axis rotation)
        double sinp = 2 * (w * y - z * x);
        if (Math.abs(sinp) >= 1)
            angles.y = Math.copySign(Math.PI / 2, sinp); // use 90 degrees if out of range
        else
            angles.y = Math.asin(sinp);

        // pitch (z-axis rotation)
        double siny_cosp = 2 * (w * z + x * y);
        double cosy_cosp = 1 - 2 * (y * y + z * z);
        angles.z = Math.atan2(siny_cosp, cosy_cosp);

        return angles;
    }

    public static final Quaternion IDENTITY = new Quaternion(0, 0, 0, 1);
}
