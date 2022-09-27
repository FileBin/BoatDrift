package org.mineice.mc.boatdrift.command.editor;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.util.SubCommand;

import java.util.List;

public class RecordStartCommand extends SubCommand {

    @Override
    public @NotNull String getName() {
        return "start";
    }

    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        if(sender instanceof Player player) {
            if(player.getVehicle() instanceof Boat boat) {
                BoatDrift.getTrackEditor().startRecord(boat);
                sender.sendMessage("record started");
                return;
            }
            sender.sendMessage("You must be in boat!");
        }
        sender.sendMessage("You must be a player!");
    }
}
