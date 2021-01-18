package beanbeanjuice.beancommands.commands.smitetimer;

import beanbeanjuice.beancommands.utility.GeneralHelper;
import beanbeanjuice.beancommands.utility.filehelper.smitetimer.SmiteTimerConfig;
import beanbeanjuice.beancommands.utility.filehelper.smitetimer.SmiteTimerMessages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SmiteTimer implements Runnable {
    public static int ID;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!SmiteTimerConfig.getConfig().getStringList("exempt-players").contains(player.getName())) {
                player.getWorld().strikeLightning(player.getLocation());
                player.sendMessage(GeneralHelper.translateColors(SmiteTimerMessages.getConfig().getString("smite-message")));
            }
        }
    }
}
