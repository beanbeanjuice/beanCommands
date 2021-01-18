package beanbeanjuice.beancommands.commands.autoannouncer;

import beanbeanjuice.beancommands.utility.GeneralHelper;
import beanbeanjuice.beancommands.utility.filehelper.autoannouncer.AutoAnnouncerConfig;
import beanbeanjuice.beancommands.utility.filehelper.autoannouncer.AutoAnnouncerMessages;
import org.bukkit.Bukkit;

import java.util.Random;

public class AutoAnnouncer implements Runnable {

    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() == 0 || AutoAnnouncerMessages.getConfig().getStringList("messages").size() == 0) {
            if (AutoAnnouncerCommands.isEnabled) {
                Bukkit.getServer().getLogger().info(GeneralHelper.getConsolePrefix() + "Disabling the autoannouncer...");
                AutoAnnouncerCommands.isEnabled = false;
            }
        } else {
            if (!AutoAnnouncerCommands.isEnabled && AutoAnnouncerMessages.getConfig().getStringList("messages").size() > 0) {
                AutoAnnouncerCommands.isEnabled = true;
                Bukkit.getServer().getLogger().info(GeneralHelper.getConsolePrefix() + "Enabling the autoannouncer.");
            }
        }

        if (AutoAnnouncerCommands.isEnabled) {
            Random rand = new Random();
            int size = AutoAnnouncerMessages.getConfig().getStringList("messages").size();
            int num = rand.nextInt(size);
            String prefix = AutoAnnouncerConfig.getConfig().getString("prefix");

            if (prefix.equals("none")) {
                prefix = "";
            } else {
                prefix += " ";
            }
            Bukkit.broadcastMessage(GeneralHelper.translateColors(prefix) + GeneralHelper.translateColors(AutoAnnouncerMessages.getConfig().getStringList("messages").get(num)));
        }
    }
}
