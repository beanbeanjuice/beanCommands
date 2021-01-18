package beanbeanjuice.beancommands.utility.filehelper.playervault;

import beanbeanjuice.beancommands.BeanCommands;
import beanbeanjuice.beancommands.commands.playervault.PlayerVaultInventory;
import beanbeanjuice.beancommands.commands.playervault.PlayerVaultInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VaultData {

    private static File configFile;
    private static FileConfiguration config;

    public static void createConfig(BeanCommands plugin) {
        configFile = new File(plugin.getDataFolder(), "vaultdata.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("vaultdata.yml", false);
        }

        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    /*
    public static void reloadConfig() {
        try {
            config.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     */

    public static void saveInventories() {
        for (Map.Entry<String, PlayerVaultInventory> playervaults : PlayerVaultInventoryHandler.getMap().entrySet()) {
            for (Map.Entry<Integer, ItemStack[]> vault : playervaults.getValue().getVaults().entrySet()) {
                VaultData.getConfig().set("data." + playervaults.getKey() + "." + vault.getKey().toString(), vault.getValue());
            }
        }
        save();
    }

    public static void restoreInventories() {
        VaultData.getConfig().getConfigurationSection("data").getKeys(false).forEach(key -> {
            VaultData.getConfig().getConfigurationSection("data." + key).getKeys(false).forEach(vaultNum -> {
                ItemStack[] content = ((List<ItemStack>) VaultData.getConfig().get("data." + key + "." + vaultNum)).toArray(new ItemStack[0]);
                if (PlayerVaultInventoryHandler.getMap().containsKey(key)) {
                    PlayerVaultInventoryHandler.getMap().get(key).getVaults().put(Integer.parseInt(vaultNum), content);
                }
                if (!PlayerVaultInventoryHandler.getMap().containsKey(key)) {
                    PlayerVaultInventory p = new PlayerVaultInventory(UUID.fromString(key));
                    p.getVaults().put(Integer.parseInt(vaultNum), content);
                    PlayerVaultInventoryHandler.getMap().put(key, p);
                }
            });
        });
    }

    public static void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {

        }
    }
}
