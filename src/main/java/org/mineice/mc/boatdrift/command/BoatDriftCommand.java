package org.mineice.mc.boatdrift.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.util.BaseCommand;

import java.util.List;

public class BoatDriftCommand extends BaseCommand {
    static public final String HELP_MESSAGE = """
            - bd checkpoint show
              Shows current checkpoint.
            - bd checkpoint hide
              Hides current checkpoint
            """;

    public BoatDriftCommand() {
        super();
        addChildCommand(new TrackCommand());
        addChildCommand(new EditorCommand());
        addChildCommand(new CheckpointCommand());
    }

    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        sender.sendMessage(HELP_MESSAGE);
    }

    @Override
    public @NotNull String getName() {
        return "boatdrift";
    }

    @Override
    public boolean isName(String name) {
        return name.equals(getName()) || name.equals("bd");
    }


}
