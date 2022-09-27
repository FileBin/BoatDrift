package org.mineice.mc.boatdrift.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class SubCommand implements ICommand {
    private final Set<ICommand> childCommands = new HashSet<>();

    protected void addChildCommand(ICommand command) {
        childCommands.add(command);
    }

    protected void removeChildCommand(ICommand command) {
        childCommands.remove(command);
    }

    @Override
    public boolean isName(String name) {
        return name.equals(getName());
    }

    @Override
    public @NotNull Set<ICommand> getChildCommands() {
        return childCommands;
    }

    @Override
    public @Nullable List<String> tabComplete(@NotNull ArrayList<String> args) {
        if (args.isEmpty()) return getChildCommands().stream().map(ICommand::getName).toList();
        var filtered = getChildCommands().stream()
                .filter(c -> c.getName().toLowerCase().startsWith(args.get(0).toLowerCase()))
                .map(ICommand::getName).toList();
        if (filtered.isEmpty()) {
            //TODO args complete
            return null;
        }
        return filtered;
    }
}
