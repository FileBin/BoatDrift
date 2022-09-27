package org.mineice.mc.boatdrift.track;

import org.bukkit.Bukkit;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.BoatDrift;

import java.util.ArrayList;
import java.util.List;

public class RaceManager implements AutoCloseable {
    Track track;
    List<Player> players;
    List<Boat> boats;

    int startTaskID, runTaskID = -1, counterTaskID = -1;

    public RaceManager(@NotNull Track track, @NotNull List<Player> players) {
        this.players = players;
        boats = players.stream()
                .map(player -> {
                    if (player.getVehicle() instanceof Boat b) {
                        return b;
                    }
                    throw new RuntimeException();
                }).toList();
        this.track = track;
        startTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BoatDrift.getInstance(), () -> {
            var startPositions = track.getStartPositions().stream()
                    .map(pos -> pos.toLocation(track.getWorld())).toList();

            for(int i = startPositions.size()-1; i>=0; i--) {
                boats.get(i).teleport(startPositions.get(i));
            }
        }, 0, 2);
        counterTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BoatDrift.getInstance(),
                new Runnable() {
            int counter = 5;
                    @Override
                    public void run() {
                        if(counter == 0) {
                            Bukkit.getScheduler().cancelTask(startTaskID);
                            Bukkit.getScheduler().scheduleSyncRepeatingTask(BoatDrift.getInstance(),
                                    new RaceRunner(track, players), 0, 1);
                            Bukkit.getScheduler().cancelTask(counterTaskID);
                            return;
                        }
                        for(var p : players) {
                            p.sendMessage("Starting in " + counter + "...");
                        }
                        counter--;
                    }
                }, 0, 20);
    }


    @Override
    public void close() {
        if (startTaskID != -1) {
            Bukkit.getScheduler().cancelTask(startTaskID);
            startTaskID = -1;
        }
    }
}
