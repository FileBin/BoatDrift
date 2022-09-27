package org.mineice.mc.boatdrift.util;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ICommand {
    @NotNull String getName();
    boolean isName(String name);
    @NotNull Set<ICommand> getChildCommands();

    void execute(CommandSender sender, String label, List<String> args);

    @Nullable List<String> tabComplete(@NotNull ArrayList<String> args);
}
