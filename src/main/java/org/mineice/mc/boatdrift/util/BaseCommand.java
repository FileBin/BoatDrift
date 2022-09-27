package org.mineice.mc.boatdrift.util;

import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineice.mc.boatdrift.BoatDrift;

import java.util.*;

public abstract class BaseCommand implements CommandExecutor, TabCompleter, ICommand {
    private Set<ICommand> childCommands = new HashSet<>();

    protected BaseCommand() {
        PluginCommand pluginCommand = BoatDrift.getInstance().getCommand(getName());
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
    }

    protected void addChildCommand(ICommand command) {
        childCommands.add(command);
    }

    protected void removeChildCommand(ICommand command) {
        childCommands.remove(command);
    }

    public @NotNull ICommand filterCommand(@NotNull ArrayList<String> args) {
        Set<ICommand> collection = getChildCommands();
        ICommand last = this;
        while (!args.isEmpty()) {
            var current = args.get(0);
            BoatDrift.LOGGER.info("\"" + current + "\"");
            var list = collection.stream().filter(c -> c.isName(current)).toList();
            if (list.isEmpty()) break;
            args.remove(0);
            last = list.get(0);
            collection = last.getChildCommands();
        }
        return last;
    }

    private void executeCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        var arg_list = new ArrayList<>(Arrays.asList(args));
        var cmd = filterCommand(arg_list);
        cmd.execute(sender, label, arg_list);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        executeCommand(sender, command, label, args);
        return false;
    }

    @Nullable
    public List<String> tabComplete(@NotNull ArrayList<String> args) {
        var cmd = filterCommand(args);
        if (cmd == this) {
            if (args.isEmpty()) return getChildCommands().stream().map(ICommand::getName).toList();
            var filtered = getChildCommands().stream()
                    .map(ICommand::getName)
                    .filter(name -> name.toLowerCase().startsWith(args.get(0).toLowerCase()))
                    .toList();
            if (filtered.isEmpty()) {
                //TODO args complete
                return null;
            }
            return filtered;
        }
        return cmd.tabComplete(args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        var arg_list = new ArrayList<>(Arrays.asList(args));
        return tabComplete(arg_list);
    }

    @Override
    public @NotNull Set<ICommand> getChildCommands() {
        return childCommands;
    }
}
