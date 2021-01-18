package beanbeanjuice.beancommands.commands.smitetimer;

import beanbeanjuice.beancommands.BeanCommands;
import beanbeanjuice.beancommands.utility.GeneralHelper;
import beanbeanjuice.beancommands.utility.filehelper.smitetimer.SmiteTimerConfig;
import beanbeanjuice.beancommands.utility.filehelper.smitetimer.SmiteTimerMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SmiteTimerCommands implements CommandExecutor {

    private BeanCommands plugin;
    public static boolean isEnabled = false;

    public SmiteTimerCommands(BeanCommands plugin) {
        this.plugin = plugin;
        plugin.getCommand("smitetimer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            MessageCheck(sender);
            isEnabled = !isEnabled;
            SmiteTimerCreator.toggleSmiteTimer();
            return true;
        }

        Player player = (Player) sender;
        String prefix = GeneralHelper.getPrefix() + " ";

        if (player.isOp() || player.hasPermission("beancommands.smitetimer")) {
            MessageCheck(player);
            isEnabled = !isEnabled;
            SmiteTimerCreator.toggleSmiteTimer();
            return true;
        } else {
            player.sendMessage(prefix + GeneralHelper.getNoPermission());
            return false;
        }
    }

    void MessageCheck(CommandSender sender) {
        boolean allBroadcast = SmiteTimerConfig.getConfig().getBoolean("server-broadcast");

        String message;

        if (isEnabled) {
            message = GeneralHelper.translateColors(SmiteTimerMessages.getConfig().getString("timer-disabled"));
        } else {
            message = GeneralHelper.translateColors(SmiteTimerMessages.getConfig().getString("timer-enabled"));
        }

        if (allBroadcast) {
            Bukkit.broadcastMessage(GeneralHelper.getPrefix() + " " + message);
        } else {
            sender.sendMessage(GeneralHelper.getConsolePrefix() + message);
        }
    }
}
