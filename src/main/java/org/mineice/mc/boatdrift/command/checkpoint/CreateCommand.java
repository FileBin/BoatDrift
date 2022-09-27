package org.mineice.mc.boatdrift.command.checkpoint;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.util.SubCommand;
import org.mineice.mc.boatdrift.util.Util;
import org.mineice.mc.boatdrift.util.math.Quaternion;
import org.mineice.mc.boatdrift.util.math.Vector3;

import java.util.List;

public class CreateCommand extends SubCommand {

    @Override
    public @NotNull String getName() {
        return "create";
    }
    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        if(args.size() > 6) throw new CommandException();
        if(sender instanceof Player player) {
            var loc = player.getLocation();
            if(args.size() == 1) {
                Vector3 pos = Vector3.fromBukkit(Util.toVector(loc));
                var size = Double.parseDouble(args.get(0));
                int id = BoatDrift.getTrackEditor().getTrack().addCheckPoint(
                        pos, Quaternion.IDENTITY, Vector3.mul(Vector3.ONE, size));
                sender.sendMessage("Checkpoint " + id + " added!");
                return;
            }
        }
        sender.sendMessage("Command failed!");
    }
}
