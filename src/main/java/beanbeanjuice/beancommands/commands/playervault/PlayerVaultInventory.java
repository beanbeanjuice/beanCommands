package beanbeanjuice.beancommands.commands.playervault;

import beanbeanjuice.beancommands.BeanCommands;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerVaultInventory {

    private UUID uuid;
    private HashMap<Integer, ItemStack[]> vaults = new HashMap<>();

    public PlayerVaultInventory(UUID uuid) {
        this.uuid = uuid;
    }

    public ItemStack[] getVault(int vaultNum) {
        if (!vaultExists(vaultNum)) {
            createVault(vaultNum);
        }
        return vaults.get(vaultNum);
    }

    public Map<Integer, ItemStack[]> getVaults() {
        return vaults;
    }

    public void setVault(int vaultNum, ItemStack[] vault) {
        vaults.put(vaultNum, vault);
    }

    private boolean vaultExists(int vaultNum) {
        return vaults.containsKey(vaultNum);
    }

    private void createVault(int vaultNum) {
        vaults.put(vaultNum, new ItemStack[0]);
    }

}
