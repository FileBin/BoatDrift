package org.mineice.mc.boatdrift.track;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.DriftHandler;

import javax.annotation.*;
import java.util.ArrayList;
import java.util.List;

public class RaceRunner implements Runnable {
    private final List<PlayerInfo> infos;
    private final Track track;

    public RaceRunner(Track track, @NotNull List<Player> players) {
        this.track = track;
        List<PlayerInfo> array = new ArrayList<>();
        for (var p : players) {
            var pi = new PlayerInfo(p);
            pi.getHandler().setRaceMode(true);
            array.add(pi);
        }
        infos = array;
    }

    @Override
    public void run() {
        for (var p : infos) {
            playerLoop(p);
        }
    }

    void playerLoop(PlayerInfo p) {
        var boat = p.getBoat();
        if (boat == null) {
            p.getPlayer().sendMessage("You left the race.");
            infos.remove(p);
            return;
        }
        var handler = p.getHandler();
        if (handler == null) throw new RuntimeException();

    }
}

class PlayerInfo {
    final Player player;
    int checkpointNum = 0;

    public PlayerInfo(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCheckpointNum() {
        return checkpointNum;
    }

    public void setCheckpointNum(int checkpointNum) {
        this.checkpointNum = checkpointNum;
    }

    public @Nullable Boat getBoat() {
        if (player.getVehicle() instanceof Boat boat) {
            return boat;
        }
        return null;
    }

    public @Nullable DriftHandler getHandler() {
        return BoatDrift.getDriftHandler(player);
    }
}
