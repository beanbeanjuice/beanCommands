package beanbeanjuice.beancommands.commands.playervault;

import beanbeanjuice.beancommands.utility.GeneralHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class RefreshInventories {

    private static HashMap<Player, String> openInventories;

    public static void setupOpenInventories() {
        openInventories = new HashMap<>();
    }

    public static void addPlayer(Player player, String title) {
        openInventories.put(player, title);
    }

    public static void removePlayer(Player player) {
        openInventories.remove(player);
    }

    public static HashMap<Player, String> getOpenInventories() {
        return openInventories;
    }

    public static void refreshInventory(Player player, String inventorynum) {
        // NOT DONE
    }

    private boolean isSame(Player player1, Player player2) {
        return openInventories.get(player1).equals(openInventories.get(player2));
    }

}
