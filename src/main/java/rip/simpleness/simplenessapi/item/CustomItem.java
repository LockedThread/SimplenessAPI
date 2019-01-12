package rip.simpleness.simplenessapi.item;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rip.simpleness.simplenessapi.nbt.NBTItem;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode(of = {"identifier"})
@Getter
@Setter
public class CustomItem {

    private final String identifier;
    private ItemStack itemStack;

    public CustomItem(String identifier, ConfigurationSection section) {
        this.identifier = identifier;
        this.itemStack = new NBTItem(itemStackFromConfigurationSection(section))
                .set(identifier, true)
                .buildItemStack();
    }

    public void givePlayer(Player player, boolean dropIfInventoryFull) {
        if (dropIfInventoryFull && player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        } else {
            player.getInventory().addItem(itemStack);
        }
    }

    public void givePlayer(Player player, boolean dropIfInventoryFull, int amount) {
        for (int i = 0; i < amount; i++) {
            givePlayer(player, dropIfInventoryFull);
        }
    }

    public boolean isCustomItemStack(ItemStack itemStack) {
        return new NBTItem(itemStack).getBoolean(identifier);
    }

    @SuppressWarnings("deprecation")
    public static ItemStack itemStackFromConfigurationSection(ConfigurationSection section) {
        Material material;
        if (section.isString("material")) {
            material = Material.matchMaterial(section.getString("material"));
        } else if (section.isInt("material")) {
            material = Material.getMaterial(section.getInt("material"));
        } else {
            throw new NullPointerException("material is null in Custom#itemStackFromConfigurationSection");
        }
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();

        if (section.isSet("name") && section.isString("name")) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', section.getString("name")));
        }
        if (section.isSet("lore") && section.isList("lore")) {
            meta.setLore(section.getStringList("lore").stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
        }
        if (section.isSet("enchants") && section.isList("enchants")) {
            List<String> stringEnchants = section.getStringList("enchants");
            for (String stringEnchant : stringEnchants) {
                String[] split = stringEnchant.split("#");
                Enchantment enchantment = Enchantment.getByName(split[0]);
                int level = Integer.parseInt(split[1]);
                itemStack.addUnsafeEnchantment(enchantment, level);
            }
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
