package org.mineice.mc.boatdrift.command;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.command.editor.DisableCommand;
import org.mineice.mc.boatdrift.command.editor.EnableCommand;
import org.mineice.mc.boatdrift.command.editor.RecordStartCommand;
import org.mineice.mc.boatdrift.command.editor.RecordStopCommand;
import org.mineice.mc.boatdrift.util.NamedCommand;
import org.mineice.mc.boatdrift.util.SubCommand;

import java.util.List;

public class EditorCommand extends SubCommand {
    static public final String HELP_MESSAGE = """
            - bd editor enable <name>
            - bd editor disable
            """;

    public EditorCommand() {
        super();
        var record = new NamedCommand("record");
        record.addChildCommand(new RecordStartCommand());
        record.addChildCommand(new RecordStopCommand());
        addChildCommand(new EnableCommand());
        addChildCommand(new DisableCommand());
        addChildCommand(record);
    }
    @Override
    public @NotNull String getName() {
        return "editor";
    }

    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        if(!args.isEmpty()) throw new CommandException();
        sender.sendMessage(HELP_MESSAGE);
    }
}
