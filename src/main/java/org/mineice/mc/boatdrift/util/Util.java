package org.mineice.mc.boatdrift.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import org.mineice.mc.boatdrift.util.math.Vector3;

public final class Util {
    public static Vector toVector(Location loc) {
        return new Vector(loc.getX(), loc.getY(), loc.getZ());
    }

    public static Vector3 toVector3(Location loc) {
        return new Vector3(loc.getX(), loc.getY(), loc.getZ());
    }

    public static void drawLine(Vector3 pos1, Vector3 pos2, int particleCount, Particle particle, World world, @Nullable Object data) {
        for (int i = 0; i < particleCount; i++) {
            double factor = (double) i / particleCount;

            var pos = Vector3.lerp(pos1, pos2, factor);

            world.spawnParticle(particle,
                    pos.x, pos.y, pos.z,
                    1, 0., 0., 0., 0.,
                    data, true);
        }
    }
}
