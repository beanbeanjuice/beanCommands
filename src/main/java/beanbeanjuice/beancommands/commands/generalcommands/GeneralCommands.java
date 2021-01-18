package beanbeanjuice.beancommands.commands.generalcommands;

import beanbeanjuice.beancommands.BeanCommands;
import beanbeanjuice.beancommands.utility.GeneralHelper;
import beanbeanjuice.beancommands.utility.filehelper.autoannouncer.AutoAnnouncerConfig;
import beanbeanjuice.beancommands.utility.filehelper.autoannouncer.AutoAnnouncerMessages;
import beanbeanjuice.beancommands.utility.filehelper.smitetimer.SmiteTimerConfig;
import beanbeanjuice.beancommands.utility.filehelper.smitetimer.SmiteTimerMessages;
import beanbeanjuice.beancommands.utility.filehelper.supersudo.SuperSudoConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GeneralCommands implements CommandExecutor {

    private BeanCommands plugin;

    public GeneralCommands(BeanCommands plugin) {
        this.plugin = plugin;
        plugin.getCommand("beancommands").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfigs(sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("help")) {
                    listHelp(sender);
                    return true;
                }
            }
            sender.sendMessage(GeneralHelper.getConsolePrefix() + plugin.getConfig().getString("incorrect-usage"));
        }

        else {
            Player player = (Player) sender;
            String prefix = GeneralHelper.getPrefix() + " ";

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (player.isOp() || player.hasPermission("beancommands.reload")) {
                        reloadConfigs(player);
                        return true;
                    }
                    player.sendMessage(prefix + GeneralHelper.getNoPermission());
                    return false;
                }

                if (args[0].equalsIgnoreCase("help")) {
                    listHelp(player);
                    return true;
                }
            }
            player.sendMessage(GeneralHelper.getPrefix() + plugin.getConfig().getStringList("incorrect-usage"));
        }
        return false;
    }

    public void reloadConfigs(CommandSender sender) {
        sender.sendMessage(GeneralHelper.getPrefix() + " " + GeneralHelper.translateColors(plugin.getConfig().getString("successful-reload")));
        plugin.reloadConfig();
        SmiteTimerConfig.reloadConfig();
        SmiteTimerMessages.reloadConfig();
        AutoAnnouncerConfig.reloadConfig();
        AutoAnnouncerMessages.reloadConfig();
        SuperSudoConfig.reloadConfig();
    }

    public void listHelp(CommandSender sender) {
        String prefix = GeneralHelper.getPrefix() + " ";
        sender.sendMessage(prefix + GeneralHelper.translateColors(plugin.getConfig().getString("help-message")));
        for (String string : plugin.getConfig().getStringList("help-messages")) {
            sender.sendMessage(GeneralHelper.translateColors(string));
        }
    }
}
