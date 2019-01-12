package rip.simpleness.simplenessapi.guis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.ClickType;

@Getter
@AllArgsConstructor
public class MenuClickEvent implements Cancellable {

    private Menu menu;
    private MenuIcon menuIcon;
    private int slot;
    private Player player;
    private ClickType clickType;
    private boolean cancelled;

    public void setMenuIcon(MenuIcon menuIcon) {
        this.menuIcon = menuIcon;
        this.menu.getInventoryContents().remove(slot);
        this.menu.getInventoryContents().put(slot, menuIcon);
    }

    public void updateMenuIconName(String name) {
        menuIcon.setDisplayName(name);

        //TODO See if this is needed.
        //this.menu.getInventoryContents().put(slot, menuIcon);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
