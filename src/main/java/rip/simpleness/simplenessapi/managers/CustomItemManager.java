package rip.simpleness.simplenessapi.managers;

import lombok.Getter;
import rip.simpleness.simplenessapi.SimplenessAPI;
import rip.simpleness.simplenessapi.item.CustomItem;

import java.util.HashMap;

@Getter
public class CustomItemManager extends Manager<SimplenessAPI> {

    private HashMap<String, CustomItem> customItemHashMap;

    public CustomItemManager(SimplenessAPI instance) {
        super(instance);
        this.customItemHashMap = new HashMap<>();
    }

    public void add(CustomItem customItem) {
        customItemHashMap.put(customItem.getIdentifier(), customItem);
    }

    public CustomItem get(String identifier) {
        return customItemHashMap.get(identifier);
    }
}
