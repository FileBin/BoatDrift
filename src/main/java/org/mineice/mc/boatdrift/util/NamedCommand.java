package org.mineice.mc.boatdrift.util;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NamedCommand extends SubCommand {
    String name;
    public NamedCommand(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        sender.sendMessage("Command " + name + " does nothing!");
    }

    @Override
    public void addChildCommand(ICommand command) {
        super.addChildCommand(command);
    }

    @Override
    public void removeChildCommand(ICommand command) {
        super.removeChildCommand(command);
    }
}
