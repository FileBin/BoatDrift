package org.mineice.mc.boatdrift.track;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.mineice.mc.boatdrift.BoatDrift;

public class TrackEditor implements AutoCloseable {

    int taskID = -1;

    String activeTrack;
    int checkpointID = 0;

    PathRecorder recorder = null;
    public TrackEditor() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BoatDrift.getInstance(), new Runnable() {
            @Override
            public void run() {
                var track = getTrack();
                if(track == null) return;
                if(recorder != null) {
                    recorder.run();
                }
                track.getPath().draw(track.getWorld(), Particle.REDSTONE, Color.AQUA);
                var list = track.getCheckPoints();
                for (var i = list.size()-1; i>=0; i--) {
                    var cp = list.get(i);
                    var color = Color.PURPLE;
                    if(i == checkpointID) {
                        color = Color.GREEN;
                    }
                    cp.draw(color);
                }
            }
        }, 0 ,2);
    }

    public @Nullable Track getTrack() {
        return BoatDrift.getTrackManager().get(activeTrack);
    }

    public void setActiveTrack(String name) {
        activeTrack = name;
    }

    public void startRecord(Boat boat) {
        recorder = new PathRecorder(boat, this::stopRecord);
    }

    public void stopRecord() {
        recorder = null;
    }

    @Override
    public void close() throws RuntimeException {
        if(taskID != -1) {
            Bukkit.getScheduler().cancelTask(taskID);
            taskID = -1;
        }
    }
}
