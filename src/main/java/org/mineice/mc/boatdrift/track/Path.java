package org.mineice.mc.boatdrift.track;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.mineice.mc.boatdrift.util.Util;
import org.mineice.mc.boatdrift.util.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<Vector3> points = new ArrayList<>();

    public void clear() {
        points.clear();
    }

    public int addPoint(Vector3 pos) {
        int i = points.size();
        points.add(pos);
        return i;
    }

    public void draw(World world, Particle particle, Color color) {
        var data = new Particle.DustOptions(color, 1.0f);
        for (int i = points.size()-2; i>=0; i--) {
            Util.drawLine(points.get(i), points.get(i+1), 2, particle, world, data);
        }
    }

    public double getPathPosition(Vector3 wp) {
        var n = points.size();
        for(int i = 1; i < n; i++) {
            var p0 = points.get(i-1);
            var vp = points.get(i).clone();
            vp.sub(p0);
            var vw = wp.clone();
            vw.sub(p0);
            var l = Vector3.dot(vw,vp.normalized());
            if((l <= vp.length()) && (l >= 0)) {
                return (double)i/n;
            }
        }
        return 0;
    }

    public List<Vector3> getPoints() {
        return points;
    }

    public int getPointsCount() {
        return points.size();
    }

    public void removePoint(int i) {
        points.remove(i);
    }
}
