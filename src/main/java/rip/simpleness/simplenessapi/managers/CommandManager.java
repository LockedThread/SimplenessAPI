package rip.simpleness.simplenessapi.managers;

import com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import rip.simpleness.simplenessapi.SimplenessAPI;
import rip.simpleness.simplenessapi.commands.Command;
import rip.simpleness.simplenessapi.commands.arguments.Argument;
import rip.simpleness.simplenessapi.commands.arguments.ArgumentType;
import rip.simpleness.simplenessapi.modules.Module;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager extends Manager<SimplenessAPI> implements CommandExecutor {

    private HashMap<Module, Command[]> commands = new HashMap<>();

    public CommandManager(SimplenessAPI instance) {
        super(instance);
    }

    public void register(Module module, Command command) {
        CommandMap commandMap = getCommandMap();
        PluginCommand pluginCommand = getPluginCommand(command);
        if (commandMap != null && pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setAliases(Arrays.asList(command.getAliases()));
            commandMap.register(instance.getDescription().getName(), pluginCommand);
            if (commands.containsKey(module)) {
                ArrayList<Command> cmds = new ArrayList<>(Arrays.asList(commands.get(module)));
                cmds.add(command);
                commands.put(module, cmds.toArray(new Command[]{}));
            }
            commands.putIfAbsent(module, new Command[]{command});
            instance.getLogger().info("Registered command: " + command.getCommandName());
        } else {
            throw new RuntimeException("Unable to register command /" + command.getCommandName() + ".");
        }
    }

    public void unregister(Module module) {
        for (Command command : this.commands.get(module)) {
            unregister(command);
        }
    }

    @SuppressWarnings("unchecked")
    public void unregister(Command command) {
        if (getCommandMap() instanceof Map) {
            ((Map<String, org.bukkit.command.Command>) getCommandMap()).remove(command.getCommandName());
        }
    }

    private CommandMap getCommandMap() {
        try {
            if (instance.getServer().getPluginManager() instanceof SimplePluginManager) {
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);
                return (CommandMap) field.get(instance.getServer().getPluginManager());
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            instance.getLogger().info("Unable to get the Bukkit CommandMap.");
            e.printStackTrace();
        }
        return null;
    }

    private PluginCommand getPluginCommand(Command command) {
        try {
            Constructor<PluginCommand> commandConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            commandConstructor.setAccessible(true);
            return commandConstructor.newInstance(command.getCommandName(), instance);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Command getCommand(Class<? extends Command> aClass) {
        for (Map.Entry<Module, Command[]> entry : commands.entrySet()) {
            for (Command command : entry.getValue()) {
                if (command.getClass().getName().equals(aClass.getName())) {
                    return command;
                }
            }
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String s, String[] args) {
        String rootCommand = bukkitCommand.getName();
        for (Map.Entry<Module, Command[]> entry : commands.entrySet()) {
            for (Command command : entry.getValue()) {
                if (command.getCommandName().equalsIgnoreCase(rootCommand) || check(command.getAliases(), rootCommand)) {
                    Command[] subCommands = command.getSubCommands();
                    if (args.length == 0) {
                        command.execute(sender, new Argument[]{});
                        return false;
                    }
                    if (subCommands.length > 0) {
                        final String[] copiedArgs = Arrays.copyOfRange(args, 1, args.length);
                        for (Command subCommand : subCommands) {
                            if (subCommand.getCommandName().equalsIgnoreCase(args[0]) || check(subCommand.getAliases(), args[0])) {
                                if (!sender.hasPermission(subCommand.getPermission())) {
                                    sender.sendMessage(ChatColor.DARK_RED + "No permission.");
                                    return false;
                                }
                                if (subCommand.getArgumentTypes().length == 0) {
                                    subCommand.execute(sender, new Argument[0]);
                                    return false;
                                }
                                ArgumentType[] argumentTypes = subCommand.getArgumentTypes();
                                ArrayList<Argument> arguments = new ArrayList<>();
                                for (int i = 0; i < argumentTypes.length; i++) {
                                    if (i + 1 > copiedArgs.length) {
                                        sender.sendMessage(ChatColor.DARK_RED + "Unable to execute command, not enough Arguments!");
                                        return false;
                                    }
                                    if (argumentTypes[i] == ArgumentType.ARGUMENT_STRING_ARRAY) {
                                        subCommand.execute(sender, new Argument[]{argumentTypes[i].build(Joiner.on(" ").skipNulls().join(Arrays.copyOfRange(args, i, args.length)))});
                                        return false;
                                    }
                                    Argument argument = argumentTypes[i].build(copiedArgs[i]);
                                    if (argument == null) {
                                        sender.sendMessage("argument == null");
                                        return false;
                                    }
                                    if (!argument.isArgumentType()) {
                                        sender.sendMessage(ChatColor.DARK_RED + "Incorrect usage of /" + command.getCommandName() + " " + subCommand.getCommandName());
                                        return false;
                                    }
                                    arguments.add(argument);
                                }
                                subCommand.execute(sender, arguments.toArray(new Argument[0]));
                            }
                        }
                    } else if (command.getArgumentTypes().length == 0) {
                        command.execute(sender, new Argument[0]);
                    } else {
                        if (!sender.hasPermission(command.getPermission())) {
                            sender.sendMessage(ChatColor.DARK_RED + "No permission.");
                            return false;
                        }
                        if (command.getArgumentTypes().length == 0) {
                            command.execute(sender, new Argument[0]);
                            return false;
                        }
                        ArgumentType[] argumentTypes = command.getArgumentTypes();
                        ArrayList<Argument> arguments = new ArrayList<>();
                        final String[] copiedArgs = Arrays.copyOfRange(args, 1, args.length);
                        for (int i = 0; i < argumentTypes.length; i++) {
                            if (argumentTypes[i] == ArgumentType.ARGUMENT_STRING_ARRAY) {
                                command.execute(sender, new Argument[]{argumentTypes[i].build(Joiner.on(" ").skipNulls().join(Arrays.copyOfRange(args, i, args.length)))});
                                return false;
                            }
                            Argument argument = argumentTypes[i].build(copiedArgs[i]);
                            if (argument == null) {
                                sender.sendMessage("argument == null");
                                return false;
                            }
                            if (!argument.isArgumentType()) {
                                sender.sendMessage(ChatColor.DARK_RED + "Incorrect usage of /" + command.getCommandName());
                                return false;
                            }
                            arguments.add(argument);
                        }
                        command.execute(sender, arguments.toArray(new Argument[0]));
                    }
                }
            }
        }
        return true;
    }

    private boolean check(String[] aliases, String check) {
        return Arrays.stream(aliases).anyMatch(alias -> alias.equalsIgnoreCase(check));
    }
}
