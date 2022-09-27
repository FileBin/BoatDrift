package org.mineice.mc.boatdrift.track;

import org.bukkit.World;
import org.jetbrains.annotations.*;

import java.util.HashMap;
import java.util.Map;

public class TrackManager {
    private final Map<String,Track> tracksMap = new HashMap<>();

    public void create(@NotNull String name, World world) {
        tracksMap.put(name, new Track(world));
    }

    public @Nullable Track get(@NotNull String name) {
        return tracksMap.get(name);
    }

    public void remove(@NotNull String name) {
        tracksMap.remove(name);
    }
}
