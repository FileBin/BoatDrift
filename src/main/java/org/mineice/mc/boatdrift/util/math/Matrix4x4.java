package org.mineice.mc.boatdrift.util.math;

public class Matrix4x4 {
    public double
            c11, c12, c13, c14,
            c21, c22, c23, c24,
            c31, c32, c33, c34,
            c41, c42, c43, c44;

    public Matrix4x4(double c11, double c12, double c13, double c14,
                     double c21, double c22, double c23, double c24,
                     double c31, double c32, double c33, double c34,
                     double c41, double c42, double c43, double c44) {
        this.c11 = c11;
        this.c12 = c12;
        this.c13 = c13;
        this.c14 = c14;
        this.c21 = c21;
        this.c22 = c22;
        this.c23 = c23;
        this.c24 = c24;
        this.c31 = c31;
        this.c32 = c32;
        this.c33 = c33;
        this.c34 = c34;
        this.c41 = c41;
        this.c42 = c42;
        this.c43 = c43;
        this.c44 = c44;
    }

    public void invert() {
        //yay that works stack overflow senpai
        double[] inv = new double[16];
        double det;
        double[] m = new double[] {
                c11, c12, c13, c14,
                c21, c22, c23, c24,
                c31, c32, c33, c34,
                c41, c42, c43, c44
        };

        inv[0] = m[5] * m[10] * m[15] -
                m[5] * m[11] * m[14] -
                m[9] * m[6] * m[15] +
                m[9] * m[7] * m[14] +
                m[13] * m[6] * m[11] -
                m[13] * m[7] * m[10];

        inv[4] = -m[4] * m[10] * m[15] +
                m[4] * m[11] * m[14] +
                m[8] * m[6] * m[15] -
                m[8] * m[7] * m[14] -
                m[12] * m[6] * m[11] +
                m[12] * m[7] * m[10];

        inv[8] = m[4] * m[9] * m[15] -
                m[4] * m[11] * m[13] -
                m[8] * m[5] * m[15] +
                m[8] * m[7] * m[13] +
                m[12] * m[5] * m[11] -
                m[12] * m[7] * m[9];

        inv[12] = -m[4] * m[9] * m[14] +
                m[4] * m[10] * m[13] +
                m[8] * m[5] * m[14] -
                m[8] * m[6] * m[13] -
                m[12] * m[5] * m[10] +
                m[12] * m[6] * m[9];

        inv[1] = -m[1] * m[10] * m[15] +
                m[1] * m[11] * m[14] +
                m[9] * m[2] * m[15] -
                m[9] * m[3] * m[14] -
                m[13] * m[2] * m[11] +
                m[13] * m[3] * m[10];

        inv[5] = m[0] * m[10] * m[15] -
                m[0] * m[11] * m[14] -
                m[8] * m[2] * m[15] +
                m[8] * m[3] * m[14] +
                m[12] * m[2] * m[11] -
                m[12] * m[3] * m[10];

        inv[9] = -m[0] * m[9] * m[15] +
                m[0] * m[11] * m[13] +
                m[8] * m[1] * m[15] -
                m[8] * m[3] * m[13] -
                m[12] * m[1] * m[11] +
                m[12] * m[3] * m[9];

        inv[13] = m[0] * m[9] * m[14] -
                m[0] * m[10] * m[13] -
                m[8] * m[1] * m[14] +
                m[8] * m[2] * m[13] +
                m[12] * m[1] * m[10] -
                m[12] * m[2] * m[9];

        inv[2] = m[1] * m[6] * m[15] -
                m[1] * m[7] * m[14] -
                m[5] * m[2] * m[15] +
                m[5] * m[3] * m[14] +
                m[13] * m[2] * m[7] -
                m[13] * m[3] * m[6];

        inv[6] = -m[0] * m[6] * m[15] +
                m[0] * m[7] * m[14] +
                m[4] * m[2] * m[15] -
                m[4] * m[3] * m[14] -
                m[12] * m[2] * m[7] +
                m[12] * m[3] * m[6];

        inv[10] = m[0] * m[5] * m[15] -
                m[0] * m[7] * m[13] -
                m[4] * m[1] * m[15] +
                m[4] * m[3] * m[13] +
                m[12] * m[1] * m[7] -
                m[12] * m[3] * m[5];

        inv[14] = -m[0] * m[5] * m[14] +
                m[0] * m[6] * m[13] +
                m[4] * m[1] * m[14] -
                m[4] * m[2] * m[13] -
                m[12] * m[1] * m[6] +
                m[12] * m[2] * m[5];

        inv[3] = -m[1] * m[6] * m[11] +
                m[1] * m[7] * m[10] +
                m[5] * m[2] * m[11] -
                m[5] * m[3] * m[10] -
                m[9] * m[2] * m[7] +
                m[9] * m[3] * m[6];

        inv[7] = m[0] * m[6] * m[11] -
                m[0] * m[7] * m[10] -
                m[4] * m[2] * m[11] +
                m[4] * m[3] * m[10] +
                m[8] * m[2] * m[7] -
                m[8] * m[3] * m[6];

        inv[11] = -m[0] * m[5] * m[11] +
                m[0] * m[7] * m[9] +
                m[4] * m[1] * m[11] -
                m[4] * m[3] * m[9] -
                m[8] * m[1] * m[7] +
                m[8] * m[3] * m[5];

        inv[15] = m[0] * m[5] * m[10] -
                m[0] * m[6] * m[9] -
                m[4] * m[1] * m[10] +
                m[4] * m[2] * m[9] +
                m[8] * m[1] * m[6] -
                m[8] * m[2] * m[5];

        det = m[0] * inv[0] + m[1] * inv[4] + m[2] * inv[8] + m[3] * inv[12];

        det = 1.0 / det;
        for (int i = 0; i < 16; i++)
            inv[i] *= det;

        c11 = inv[0];
        c12 = inv[1];
        c13 = inv[2];
        c14 = inv[3];
        c21 = inv[4];
        c22 = inv[5];
        c23 = inv[6];
        c24 = inv[7];
        c31 = inv[8];
        c32 = inv[9];
        c33 = inv[10];
        c34 = inv[11];
        c41 = inv[12];
        c42 = inv[13];
        c43 = inv[14];
        c44 = inv[15];
    }

    public static Matrix4x4 translation(Vector3 v) {
        return new Matrix4x4(
                1, 0, 0, v.x,
                0, 1, 0, v.y,
                0, 0, 1, v.z,
                0, 0, 0, 1

        );
    }

    public static Matrix4x4 rotation(Quaternion q) {
        return new Matrix4x4(
                1 - 2 * (q.y * q.y + q.z * q.z),
                2 * (q.x * q.y - q.w * q.z),
                2 * (q.x * q.z + q.w * q.y),
                0,

                2 * (q.x * q.y + q.w * q.z),
                1 - 2 * (q.x * q.x + q.z * q.z),
                2 * (q.y * q.z - q.w * q.x),
                0,

                2 * (q.x * q.z - q.w * q.y),
                2 * (q.y * q.z + q.w * q.x),
                1 - 2 * (q.x * q.x + q.y * q.y),
                0,

                0,
                0,
                0,
                1
        );
    }

    public static Matrix4x4 scale(Vector3 v) {
        return new Matrix4x4(
                v.x, 0, 0, 0,
                0, v.y, 0, 0,
                0, 0, v.z, 0,
                0, 0, 0, 1

        );
    }

    public static Matrix4x4 TRS(Vector3 t, Quaternion r, Vector3 s) {
        return mul(scale(s),mul(rotation(r),translation(t)));
    }

    public Matrix4x4 transpose() {
        return new Matrix4x4(
                c11, c21, c31, c41,
                c12, c22, c32, c42,
                c13, c23, c33, c43,
                c14, c42, c43, c44
        );
    }

    public static final Matrix4x4 IDENTITY = new Matrix4x4(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    );

    public static Matrix4x4 mul(Matrix4x4 a, Matrix4x4 b) {
        return new Matrix4x4(
                a.c11 * b.c11 + a.c21 * b.c12 + a.c31 * b.c13 + a.c41 * b.c14,
                a.c12 * b.c11 + a.c22 * b.c12 + a.c32 * b.c13 + a.c42 * b.c14,
                a.c13 * b.c11 + a.c23 * b.c12 + a.c33 * b.c13 + a.c43 * b.c14,
                a.c14 * b.c11 + a.c24 * b.c12 + a.c34 * b.c13 + a.c44 * b.c14,

                a.c11 * b.c21 + a.c21 * b.c22 + a.c31 * b.c23 + a.c41 * b.c24,
                a.c12 * b.c21 + a.c22 * b.c22 + a.c32 * b.c23 + a.c42 * b.c24,
                a.c13 * b.c21 + a.c23 * b.c22 + a.c33 * b.c23 + a.c43 * b.c24,
                a.c14 * b.c21 + a.c24 * b.c22 + a.c34 * b.c23 + a.c44 * b.c24,

                a.c11 * b.c31 + a.c21 * b.c32 + a.c31 * b.c33 + a.c41 * b.c34,
                a.c12 * b.c31 + a.c22 * b.c32 + a.c32 * b.c33 + a.c42 * b.c34,
                a.c13 * b.c31 + a.c23 * b.c32 + a.c33 * b.c33 + a.c43 * b.c34,
                a.c14 * b.c31 + a.c24 * b.c32 + a.c34 * b.c33 + a.c44 * b.c34,

                a.c11 * b.c41 + a.c21 * b.c42 + a.c31 * b.c43 + a.c41 * b.c44,
                a.c12 * b.c41 + a.c22 * b.c42 + a.c32 * b.c43 + a.c42 * b.c44,
                a.c13 * b.c41 + a.c23 * b.c42 + a.c33 * b.c43 + a.c43 * b.c44,
                a.c14 * b.c41 + a.c24 * b.c42 + a.c34 * b.c43 + a.c44 * b.c44
        );
    }
}
