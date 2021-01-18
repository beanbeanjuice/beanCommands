package beanbeanjuice.beancommands.commands.playervault;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerVaultInventoryHandler {

    private static Map<String, PlayerVaultInventory> vault;

    public static void createMap() {
        vault = new HashMap<>();
    }

    public static ItemStack[] getInventory(Player player, String vaultnum) {
        ItemStack[] temp = vault.get(player.getUniqueId().toString()).getVault(Integer.parseInt(vaultnum));
        int vaultsize = PlayerVaultCommand.getVaultSize(player);
        if (temp.length > vaultsize) {
            ItemStack[] itemsInVault = new ItemStack[vaultsize];
            for (int i = 0; i < vaultsize; i++) {
                itemsInVault[i] = temp[i];
            }
            return itemsInVault;
        }
        return temp;
    }

    public static void setVault(Player player, int vaultNum, ItemStack[] contents) {
        if (!hasVault(player)) {
            makeVault(player);
        }
        vault.get(player.getUniqueId().toString()).setVault(vaultNum, contents);
    }

    public static Map<String, PlayerVaultInventory> getMap() {
        return vault;
    }

    public static boolean hasVault(Player player) {
        return vault.containsKey(player.getUniqueId().toString());
    }

    public static void makeVault(Player player) {
        vault.put(player.getUniqueId().toString(), new PlayerVaultInventory(player.getUniqueId()));
    }
}
