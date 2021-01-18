package beanbeanjuice.beancommands.utility.filehelper.supersudo;

import beanbeanjuice.beancommands.BeanCommands;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SuperSudoConfig {

    private static File configFile;
    private static FileConfiguration config;

    public static void createConfig(BeanCommands plugin) {
        configFile = new File(plugin.getDataFolder(), "supersudo.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("supersudo.yml", false);
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

    public static void reloadConfig() {
        try {
            config.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
