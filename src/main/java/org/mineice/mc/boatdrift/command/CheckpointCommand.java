package org.mineice.mc.boatdrift.command;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineice.mc.boatdrift.command.checkpoint.CreateCommand;
import org.mineice.mc.boatdrift.command.checkpoint.RemoveCommand;
import org.mineice.mc.boatdrift.track.CheckPoint;
import org.mineice.mc.boatdrift.util.SubCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckpointCommand extends SubCommand {
    static public final String HELP_MESSAGE = """
            - bd checkpoint create
            - bd checkpoint use <id>
            - bd checkpoint set [ppp|nnn] x y z
            """;

    public CheckpointCommand() {
        super();
        addChildCommand(new CreateCommand());
        addChildCommand(new RemoveCommand());
    }

    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        if (!args.isEmpty()) throw new CommandException();
        sender.sendMessage(HELP_MESSAGE);
    }

    @Override
    public @NotNull String getName() {
        return "checkpoint";
    }
}
