package rip.simpleness.simplenessapi;

import de.dustplanet.util.SilkUtil;
import lombok.Getter;
import lombok.Setter;
import rip.simpleness.simplenessapi.commands.CommandSimpleness;
import rip.simpleness.simplenessapi.managers.CommandManager;
import rip.simpleness.simplenessapi.managers.CustomItemManager;
import rip.simpleness.simplenessapi.modules.Module;

@Getter
@Setter
public class SimplenessAPI extends Module {

    public static SimplenessAPI getInstance() {
        return instance;
    }

    private static SimplenessAPI instance;

    private CustomItemManager customItemManager;
    private CommandManager commandManager;
    private SilkUtil silkUtil;

    @Override
    public void onEnable() {
        instance = this;
        setup();
        setCustomItemManager(new CustomItemManager(this));
        setCommandManager(new CommandManager(this));
        setSilkUtil(SilkUtil.hookIntoSilkSpanwers());
        registerCommands(this, new CommandSimpleness());
    }

    @Override
    public void onDisable() {
        instance = null;
        disableCommands();
        unregisterListeners();
    }

    @Override
    public void reload() {
        super.reload();
        getLogger().info("Successfully reloaded SimplenessAPI");
    }
}
