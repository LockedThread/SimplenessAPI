package rip.simpleness.simplenessapi.commands;

import org.bukkit.command.CommandSender;
import rip.simpleness.simplenessapi.commands.arguments.Argument;
import rip.simpleness.simplenessapi.commands.arguments.ArgumentType;

public class CommandSimpleness extends Command {

    public CommandSimpleness() {
        super("simpleness", "simpleness.admin", new ArgumentType[0], new Command[]{new CommandSimplenessGive(), new CommandSimplenessList()}, "simple");
    }

    @Override
    public void execute(CommandSender sender, Argument[] arguments) {
        sender.sendMessage("You have execute /simpleness");
    }
}
