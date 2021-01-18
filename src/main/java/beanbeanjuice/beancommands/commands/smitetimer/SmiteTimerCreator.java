package beanbeanjuice.beancommands.commands.smitetimer;

import beanbeanjuice.beancommands.BeanCommands;
import beanbeanjuice.beancommands.utility.filehelper.smitetimer.SmiteTimerConfig;
import org.bukkit.Bukkit;

public class SmiteTimerCreator {

    private static BeanCommands plugin;

    public SmiteTimerCreator(BeanCommands plugin) {
        this.plugin = plugin;
    }

    public static void toggleSmiteTimer() {
        if (!SmiteTimerCommands.isEnabled) {
            Bukkit.getScheduler().cancelTask(SmiteTimer.ID);
        } else {
            SmiteTimer.ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new SmiteTimer(), 20L, (SmiteTimerConfig.getConfig().getInt("time") * 20));
        }
    }
}
