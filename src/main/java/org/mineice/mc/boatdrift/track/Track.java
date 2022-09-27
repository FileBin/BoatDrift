package org.mineice.mc.boatdrift.track;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.util.math.Quaternion;
import org.mineice.mc.boatdrift.util.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Track {
    private final List<CheckPoint> checkPoints = new ArrayList<>();
    private final List<Vector3> startPositions = new ArrayList<>();
    private final Path path = new Path();
    private final World world;

    public RaceManager raceManager = null;

    public Track(World world) {
        this.world = world;
    }


    public int addCheckPoint(@NotNull Vector3 pos, @NotNull Quaternion rotation, @NotNull Vector3 size) {
        int i = checkPoints.size();
        var cp = new CheckPoint(world);
        cp.setBoxPosition(pos);
        cp.setBoxRotation(rotation);
        cp.setBoxSize(size);
        checkPoints.add(cp);
        return i;
    }

    public int addStartPosition(Vector3 pos) {
        var i = startPositions.size();
        startPositions.add(pos);
        return i;
    }

    public void removeStartPosition(int i) {
        startPositions.remove(i);
    }

    public List<Vector3> getStartPositions() {
        return startPositions;
    }

    public RaceManager getRaceManager() {
        return raceManager;
    }

    public void setRaceManager(RaceManager raceManager) {
        this.raceManager = raceManager;
    }

    public void removeCheckPoint(int i) {
        checkPoints.remove(i);
    }

    public void clearCheckpoints() {
        checkPoints.clear();
    }

    public CheckPoint getCheckPoint(int i) {
        return checkPoints.get(i);
    }
    public int getCheckPointCount() {
        return checkPoints.size();
    }
    public List<CheckPoint> getCheckPoints() {
        return checkPoints;
    }
    public World getWorld() {
        return world;
    }

    public Path getPath() {
        return path;
    }

    public void clearPath() {
        path.clear();
    }
}
