package org.mineice.mc.boatdrift.command;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.mineice.mc.boatdrift.command.race.RaceJoinCommand;
import org.mineice.mc.boatdrift.command.track.AddStartPosition;
import org.mineice.mc.boatdrift.command.track.CreateCommand;
import org.mineice.mc.boatdrift.command.track.RemoveCommand;
import org.mineice.mc.boatdrift.command.track.RemoveStartPosition;
import org.mineice.mc.boatdrift.util.NamedCommand;
import org.mineice.mc.boatdrift.util.SubCommand;

import java.util.List;

public class TrackCommand  extends SubCommand {
    static public final String HELP_MESSAGE = """
            - bd track create <name>
            - bd track remove <name>
            """;

    public TrackCommand() {
        super();
        var positionCmd = new NamedCommand("pos");
        positionCmd.addChildCommand(new AddStartPosition());
        positionCmd.addChildCommand(new RemoveStartPosition());
        var startCmd = new NamedCommand("start");
        var raceCmd = new NamedCommand("race");
        raceCmd.addChildCommand(new RaceJoinCommand());
        startCmd.addChildCommand(positionCmd);
        addChildCommand(new CreateCommand());
        addChildCommand(new RemoveCommand());
        addChildCommand(raceCmd);
    }
    @Override
    public @NotNull String getName() {
        return "track";
    }

    @Override
    public void execute(CommandSender sender, String label, List<String> args) {
        if(!args.isEmpty()) throw new CommandException();
        sender.sendMessage(HELP_MESSAGE);
    }
}
