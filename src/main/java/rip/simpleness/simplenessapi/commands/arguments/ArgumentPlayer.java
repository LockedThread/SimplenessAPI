package rip.simpleness.simplenessapi.commands.arguments;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArgumentPlayer extends Argument<Player> {


    public ArgumentPlayer(boolean optional, String check) {
        super(optional, check);
    }

    public ArgumentPlayer(String check) {
        super(check);
    }

    @Override
    public Player getValue() {
        return value;
    }

    @Override
    public boolean isArgumentType() {
        Player player = Bukkit.getPlayer(check);
        setValue(player);
        return player != null;
    }
}
