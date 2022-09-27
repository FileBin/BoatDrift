package org.mineice.mc.boatdrift.command.editor;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.util.SubCommand;

import java.util.List;

public class DisableCommand extends SubCommand {
    @Override
    public @NotNull String getName() {
        return "disable";
    }

    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        if(!args.isEmpty()) throw new CommandException();
        BoatDrift.getTrackEditor().setActiveTrack(null);
        sender.sendMessage("TrackEditor disabled!");
    }
}
