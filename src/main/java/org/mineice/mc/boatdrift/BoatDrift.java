package org.mineice.mc.boatdrift;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineice.mc.boatdrift.command.BoatDriftCommand;
import org.mineice.mc.boatdrift.track.*;

import java.util.HashMap;
import java.util.logging.Logger;

public final class BoatDrift extends JavaPlugin {
    private final HashMap<Player, DriftHandler> handlers = new HashMap<>();
    public static final Logger LOGGER = Logger.getLogger("BoatDrift");
    private static BoatDrift instance;
    TrackManager trackManager;
    TrackEditor trackEditor;
    @Override
    public void onEnable() {
        instance = this;
        trackManager = new TrackManager();
        trackEditor = new TrackEditor();

        //register commands
        new BoatDriftCommand();
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        LOGGER.info("sussy plugin enabled");
    }

    @Override
    public void onDisable() {
        trackEditor.close();
        trackEditor = null;
        trackManager = null;
        instance = null;
        LOGGER.info("sussy plugin disabled");
    }

    public static BoatDrift getInstance() {
        return instance;
    }
    public static TrackManager getTrackManager() {
        return instance.trackManager;
    }
    public static TrackEditor getTrackEditor() {
        return instance.trackEditor;
    }

    public static @Nullable DriftHandler getDriftHandler(Player p) {
        return getInstance().handlers.get(p);
    }

    public static void addDriftHandler(Player p, @NotNull DriftHandler handler) {
        getInstance().handlers.put(p, handler);
    }

    public static void removeDriftHandler(Player p) {
        getInstance().handlers.remove(p).disable();
    }
}
