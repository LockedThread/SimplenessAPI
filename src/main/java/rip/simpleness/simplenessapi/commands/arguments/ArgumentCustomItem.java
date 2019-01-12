package rip.simpleness.simplenessapi.commands.arguments;

import rip.simpleness.simplenessapi.SimplenessAPI;
import rip.simpleness.simplenessapi.item.CustomItem;

import java.util.Map;

public class ArgumentCustomItem extends Argument<CustomItem> {

    public ArgumentCustomItem(boolean optional, String check) {
        super(optional, check);
    }

    public ArgumentCustomItem(String check) {
        super(check);
    }

    @Override
    public CustomItem getValue() {
        return value;
    }

    @Override
    public boolean isArgumentType() {
        for (Map.Entry<String, CustomItem> entry : SimplenessAPI.getInstance().getCustomItemManager().getCustomItemHashMap().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(check)) {
                setValue(entry.getValue());
                return true;
            }
        }
        return false;
    }
}
