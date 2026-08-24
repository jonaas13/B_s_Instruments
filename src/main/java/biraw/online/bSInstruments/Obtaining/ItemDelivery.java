package biraw.online.bSInstruments.Obtaining;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemDelivery {
    private ItemDelivery() {
    }

    public static boolean giveToInventory(Player player, ItemStack item) {
        return player.getInventory().addItem(item).isEmpty();
    }
}
