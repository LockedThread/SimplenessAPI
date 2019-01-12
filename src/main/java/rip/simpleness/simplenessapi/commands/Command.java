package rip.simpleness.simplenessapi.commands;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import rip.simpleness.simplenessapi.commands.arguments.Argument;
import rip.simpleness.simplenessapi.commands.arguments.ArgumentType;

@Getter
public abstract class Command {

    private String commandName;
    private String permission;
    private boolean hasPermission;
    private ArgumentType[] argumentTypes;
    private String[] aliases;
    @Setter
    private Command[] subCommands;

    public Command(String commandName, String permission, ArgumentType[] argumentTypes, Command[] subCommands, String... aliases) {
        this.commandName = commandName;
        this.permission = permission;
        this.hasPermission = !permission.isEmpty();
        this.argumentTypes = argumentTypes;
        this.subCommands = subCommands;
        this.aliases = aliases;
    }

    public abstract void execute(CommandSender sender, Argument[] arguments);
}
