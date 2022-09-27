package org.mineice.mc.boatdrift.util;

import org.bukkit.util.Vector;
import org.mineice.mc.boatdrift.util.math.Matrix4x4;
import org.mineice.mc.boatdrift.util.math.Quaternion;
import org.mineice.mc.boatdrift.util.math.Vector3;

public class Box {
    Vector3 position = Vector3.ZERO, size = Vector3.ONE;
    Quaternion rotation = Quaternion.IDENTITY;

    public Vector3 getPosition() {
        return position;
    }
    public void setPosition(Vector3 position) {
        this.position = position;
    }
    public Quaternion getRotation() {
        return rotation;
    }
    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }
    public Vector3 getSize() {
        return size;
    }
    public void setSize(Vector3 size) {
        this.size = size;
    }

    public void expandByPos(Vector3 p) {
        var matrix = Matrix4x4.rotation(rotation);
        matrix.invert();
        p.sub(position);
        p.transform(matrix);

        var min = getMin();
        var max = getMax();

        min.operate((i, x) -> Math.min(x, p.element(i)));
        max.operate((i, x) -> Math.max(x, p.element(i)));

        position = Vector3.mul(Vector3.add(min, max), .5);
        size = Vector3.mul(Vector3.sub(max, min), 2);
    }

    public Vector3 getMin() {
        return Vector3.add(position, Vector3.mul(size, .5));
    }

    public Vector3 getMax() {
        return Vector3.sub(position, Vector3.mul(size,.5));
    }

    public boolean isPointInBounds(Vector3 point) {
        Matrix4x4 trs = Matrix4x4.TRS(position, rotation, size);
        trs.invert();
        var p = point.clone();
        p.transform(trs);
        return sdBox(p) <= 0;
    }

    public Vector3[] getCubeVertices() {
        Vector3 min = getMin();
        Vector3 max = getMax();
        Vector3[] points = new Vector3[]{
                new Vector3(-.5,-.5,-.5),
                new Vector3(.5,-.5,-.5),
                new Vector3(-.5, .5, -.5),
                new Vector3(.5, .5, -.5),
                new Vector3(-.5, -.5, .5),
                new Vector3(.5,-.5, .5),
                new Vector3(-.5, .5, .5),
                new Vector3(.5, .5, .5)
        };
        for (int i = points.length - 1;i>=0;i--) {
            points[i].transform(Matrix4x4.TRS(position, rotation, size));
        }
        return points;
    }

    private double sdBox(Vector3 p) {
        Vector3 d = p.clone();
        d.operate(Math::abs);
        d.sub(Vector3.ONE);

        Vector3 v = d.clone();
        v.operate(x -> Math.max(x,0));
        return Math.min(Math.max(d.x,Math.max(d.y,d.z)),0.0) + v.length();
    }
}
