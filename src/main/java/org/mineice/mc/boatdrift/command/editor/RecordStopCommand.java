package org.mineice.mc.boatdrift.command.editor;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.BoatDrift;
import org.mineice.mc.boatdrift.util.SubCommand;

import java.util.List;

public class RecordStopCommand extends SubCommand {
    @Override
    public @NotNull String getName() {
        return "stop";
    }

    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        BoatDrift.getTrackEditor().stopRecord();
        sender.sendMessage("record stopped");
    }
}
