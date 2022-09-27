package org.mineice.mc.boatdrift.track;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.util.Box;
import org.mineice.mc.boatdrift.util.Util;
import org.mineice.mc.boatdrift.util.math.Quaternion;
import org.mineice.mc.boatdrift.util.math.Vector3;

public class CheckPoint {
    Box box = new Box();
    private final World world;

    public CheckPoint(World world) {
        this.world = world;
    }

    public void setBoxPosition(Vector3 position) {
        box.setPosition(position);
    }
    public void setBoxRotation(@NotNull Quaternion rotation) {
        box.setRotation(rotation);
    }
    public void setBoxSize(Vector3 size) {
        box.setSize(size);
    }

    public Vector3 getBoxPosition() {
        return box.getPosition();
    }

    public Vector3 getBoxSize() {
        return box.getSize();
    }

    public Quaternion getBoxRotation() {
        return box.getRotation();
    }

    public void expandByPos(Vector3 pos) {
        box.expandByPos(pos);
    }

    public boolean contains(Vector3 pos) {
        return box.isPointInBounds(pos);
    }

    public void draw(Color color) {
        final var options = new Particle.DustOptions(color, 1.0f);
        final var particle = Particle.REDSTONE;
        final var vert = box.getCubeVertices();
        final int count = 3;
        //-Z plane
        Util.drawLine(vert[0], vert[1], count, particle, world, options);
        Util.drawLine(vert[1], vert[3], count, particle, world, options);
        Util.drawLine(vert[2], vert[3], count, particle, world, options);
        Util.drawLine(vert[2], vert[0], count, particle, world, options);

        Util.drawLine(vert[4], vert[5], count, particle, world, options);
        Util.drawLine(vert[5], vert[7], count, particle, world, options);
        Util.drawLine(vert[6], vert[7], count, particle, world, options);
        Util.drawLine(vert[6], vert[4], count, particle, world, options);

        Util.drawLine(vert[0], vert[4], count, particle, world, options);
        Util.drawLine(vert[1], vert[5], count, particle, world, options);
        Util.drawLine(vert[2], vert[6], count, particle, world, options);
        Util.drawLine(vert[3], vert[7], count, particle, world, options);
    }
}
