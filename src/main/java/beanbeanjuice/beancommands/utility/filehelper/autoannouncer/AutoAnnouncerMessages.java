package beanbeanjuice.beancommands.utility.filehelper.autoannouncer;

import beanbeanjuice.beancommands.BeanCommands;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AutoAnnouncerMessages {

    private static File configFile;
    private static FileConfiguration config;
    private static String filepath = ("plugins/BeanCommands");

    public static void createConfig(BeanCommands plugin) {
        configFile = new File(plugin.getDataFolder(), "AOmessages.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("AOmessages.yml", false);
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

    public static void addBroadcast(String broadcast) {
        FileConfiguration config = getConfig();
        List<String> stringList = config.getStringList("messages");
        stringList.add(broadcast);
        config.set("messages", stringList);

        try {
            config.save(new File(filepath + "/AOmessages.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeBroadcast(int position) {
        FileConfiguration config = getConfig();
        List<String> stringList = config.getStringList("messages");
        stringList.remove(position-1);
        config.set("messages", stringList);

        try {
            config.save(new File(filepath + "/AOmessages.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig() {
        try {
            config.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
