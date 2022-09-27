package org.mineice.mc.boatdrift.track;

import org.bukkit.Bukkit;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.util.Util;
import org.mineice.mc.boatdrift.util.math.Quaternion;
import org.mineice.mc.boatdrift.util.math.Vector3;

import java.util.function.Supplier;

public class PathRecorder implements Runnable {
    Boat vehicle;
    Track track;

    int tickCounter = 0;

    Runnable onExit;

    Vector3 checkpointSize = Vector3.mul(Vector3.ONE, 3);

    boolean recordStarted = false;

    public PathRecorder(Boat boat, Runnable onExit) {
        this.onExit = onExit;
        vehicle = boat;
        track = BoatDrift.getTrackEditor().getTrack();

        var loc = vehicle.getLocation();
        var pos = Util.toVector3(loc);
        var rot = Quaternion.fromAxisAngle(Vector3.UP, Math.toRadians(loc.getYaw()));
        track.clearCheckpoints();
        track.clearPath();
        track.addCheckPoint(pos, rot, checkpointSize);
        track.getPath().addPoint(pos);
    }

    @Override
    public void run() {
        var loc = vehicle.getLocation();
        var pos = Util.toVector3(loc);
        var rot = Quaternion.fromAxisAngle(Vector3.UP, Math.toRadians(loc.getYaw()));
        if (!recordStarted) {
            if(!track.getCheckPoint(0).box.isPointInBounds(pos)) {
                recordStarted = true;
            }
            BoatDrift.LOGGER.info("Record not started");
        } else {
            BoatDrift.LOGGER.info("Recording...");
            if(track.getCheckPoint(0).box.isPointInBounds(pos)) {
                recordStarted = false;
                if(onExit != null) onExit.run();
                return;
            }
            tickCounter++;
            if(tickCounter > 50) {
                tickCounter = 0;
                addCheckPoint(pos, rot);
            }

            track.getPath().addPoint(pos);
        }
    }

    void addCheckPoint(Vector3 pos, Quaternion rot) {
        track.addCheckPoint(pos, rot, checkpointSize);
    }
}
