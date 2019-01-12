package rip.simpleness.simplenessapi.modules;

import de.dustplanet.util.SilkUtil;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import rip.simpleness.simplenessapi.SimplenessAPI;
import rip.simpleness.simplenessapi.commands.Command;
import rip.simpleness.simplenessapi.managers.CommandManager;
import rip.simpleness.simplenessapi.managers.CustomItemManager;

public abstract class Module extends JavaPlugin {

    public void unregisterListeners() {
        HandlerList.unregisterAll(this);
    }

    public void registerCommands(Module module, Command... commands) {
        for (Command command : commands) {
            getCommandManager().register(module, command);
        }
    }

    public void setup() {
        getLogger().info("Enabling " + getDescription().getName());
    }

    public void disableCommands() {
        getCommandManager().unregister(this);
    }

    public void reload() {
        onDisable();
        onEnable();
    }

    public CommandManager getCommandManager() {
        return SimplenessAPI.getInstance().getCommandManager();
    }

    public CustomItemManager getCustomItemManager() {
        return SimplenessAPI.getInstance().getCustomItemManager();
    }

    public SilkUtil getSilkUtil() {
        return SimplenessAPI.getInstance().getSilkUtil();
    }
}
