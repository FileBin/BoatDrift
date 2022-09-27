package org.mineice.mc.boatdrift;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.HashMap;

public class EventListener implements Listener {

    @EventHandler
    public void onMount(EntityMountEvent event) {
        //if passenger is not player return
        if (!(event.getEntity() instanceof Player p)) return;
        //if vehicle is not boat return
        if (!(event.getMount() instanceof Boat boat)) return;
        var driftHandler = new DriftHandler(BoatDrift.getInstance(), p);
        driftHandler.enable();
        BoatDrift.addDriftHandler(p, driftHandler);
        p.sendMessage("Mounted " + p.getName() + " to " + boat.getType());
    }

    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        //if passenger is not player return
        if (!(event.getEntity() instanceof Player p)) return;
        BoatDrift.removeDriftHandler(p);
        p.sendMessage("Dismounted " + p.getName());
    }

    private void broadcastMessage(@NotNull String msg) {
        var players = BoatDrift.getInstance().getServer().getOnlinePlayers();
        for (var player : players) {
            player.sendMessage(msg);
        }
    }
}
