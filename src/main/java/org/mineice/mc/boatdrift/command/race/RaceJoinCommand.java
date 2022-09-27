package org.mineice.mc.boatdrift.command.race;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.track.RaceManager;
import org.mineice.mc.boatdrift.util.SubCommand;

import java.util.ArrayList;
import java.util.List;

public class RaceJoinCommand extends SubCommand {

    @Override
    public @NotNull String getName() {
        return "join";
    }

    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        if(args.size() != 1) throw new CommandException();
        if(!(sender instanceof Player p)) throw new CommandException();
        var trackName = args.get(0);
        var track = BoatDrift.getTrackManager().get(trackName);
        if(track == null) throw new RuntimeException();
        track.raceManager = new RaceManager(track, List.of(p));
        sender.sendMessage("You joined the race!");
    }
}
