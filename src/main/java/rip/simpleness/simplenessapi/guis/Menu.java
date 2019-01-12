package rip.simpleness.simplenessapi.guis;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public abstract class Menu implements InventoryHolder {

    private final String name;
    private final int slots;
    private HashSet<UUID> viewers = new HashSet<>();
    private HashMap<Integer, MenuIcon> inventoryContents = new HashMap<>();

    public void openInventory(Player player) {
        viewers.add(player.getUniqueId());
        player.openInventory(getInventory());
    }

    public abstract void onOpenInventory(InventoryOpenEvent event);

    public abstract void onCloseInventory(InventoryCloseEvent event);

    public Menu setMenuIcon(int slot, MenuIcon menuIcon) {
        inventoryContents.put(slot, menuIcon);
        return this;
    }

    public void removeMenuIcon(int slot) {
        inventoryContents.remove(slot);
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, slots, ChatColor.translateAlternateColorCodes('&', name));
        inventoryContents.forEach((key, value) -> inventory.setItem(key, value.getItemStack()));
        return inventory;
    }
}
