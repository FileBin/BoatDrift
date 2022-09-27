package org.mineice.mc.boatdrift.command.track;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.util.SubCommand;

import java.util.List;

public class RemoveCommand extends SubCommand {

    @Override
    public @NotNull String getName() {
        return "remove";
    }
    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        if(args.size() != 1) throw new CommandException();
        var name = args.get(0);
        BoatDrift.getTrackManager().remove(name);
        sender.sendMessage("Track " + name + " removed!");
    }
}
