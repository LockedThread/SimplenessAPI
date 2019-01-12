package rip.simpleness.simplenessapi.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.simpleness.simplenessapi.commands.arguments.Argument;
import rip.simpleness.simplenessapi.commands.arguments.ArgumentType;
import rip.simpleness.simplenessapi.item.CustomItem;

public class CommandSimplenessGive extends Command {

    public CommandSimplenessGive() {
        super("give", "simpleness.admin", new ArgumentType[]{ArgumentType.ARGUMENT_PLAYER, ArgumentType.ARGUMENT_CUSTOM_ITEM}, new Command[0]);
    }

    @Override
    public void execute(CommandSender sender, Argument[] arguments) {
        ((CustomItem) arguments[1].getValue()).givePlayer((Player) arguments[0].getValue(), true);
    }
}
