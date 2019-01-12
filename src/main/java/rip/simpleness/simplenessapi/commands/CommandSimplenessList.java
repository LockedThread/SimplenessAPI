package rip.simpleness.simplenessapi.commands;

import com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import rip.simpleness.simplenessapi.SimplenessAPI;
import rip.simpleness.simplenessapi.commands.arguments.Argument;
import rip.simpleness.simplenessapi.commands.arguments.ArgumentType;

public class CommandSimplenessList extends Command {

    public CommandSimplenessList() {
        super("list", "simpleness.admin", new ArgumentType[0], new Command[0]);
    }

    @Override
    public void execute(CommandSender sender, Argument[] arguments) {
        sender.sendMessage(ChatColor.DARK_RED + "Custom Items: " + ChatColor.YELLOW + Joiner.on(", ").skipNulls().join(SimplenessAPI.getInstance().getCustomItemManager().getCustomItemHashMap().keySet()));
    }
}
