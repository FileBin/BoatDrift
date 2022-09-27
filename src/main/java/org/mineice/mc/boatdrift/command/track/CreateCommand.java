package org.mineice.mc.boatdrift.command.track;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.util.SubCommand;

import java.util.List;

public class CreateCommand extends SubCommand {

    @Override
    public @NotNull String getName() {
        return "create";
    }
    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        if(args.size() != 1) throw new CommandException();
        var name = args.get(0);
        if(sender instanceof Player player) {
            BoatDrift.getTrackManager().create(name, player.getWorld());
        } else {
            sender.sendMessage("Server can't execute this command");
            return;
        }
        sender.sendMessage("Track " + name + " created!");
    }
}
