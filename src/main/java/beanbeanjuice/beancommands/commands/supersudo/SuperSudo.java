package beanbeanjuice.beancommands.commands.supersudo;

import beanbeanjuice.beancommands.BeanCommands;
import beanbeanjuice.beancommands.utility.GeneralHelper;
import beanbeanjuice.beancommands.utility.filehelper.supersudo.SuperSudoConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuperSudo implements CommandExecutor {
    private BeanCommands plugin;

    public SuperSudo(BeanCommands plugin) {
        this.plugin = plugin;
        plugin.getCommand("supersudo").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(GeneralHelper.getConsolePrefix() + "If using console, please use /sudo.");
            return false;
        }

        Player punisher = (Player) sender;
        String prefix = GeneralHelper.getPrefix() + " ";

        if (SuperSudoConfig.getConfig().getStringList("allowed-players").contains(punisher.getName())) {
            if (args.length < 2) {
                punisher.sendMessage(prefix + GeneralHelper.translateColors(SuperSudoConfig.getConfig().getString("incorrect-usage")));
                return false;
            }

            if (Bukkit.getPlayer(args[0]) != null) {
                Player punishee = Bukkit.getPlayer(args[0]);
                StringBuilder command = new StringBuilder();

                for (int i = 1; i < args.length; i++) {
                    command.append(args[i]);
                    command.append(" ");
                }

                if (args[1].toLowerCase().startsWith("c:")) {
                    punishee.chat(command.toString().replace("c:", ""));
                } else {
                    punishee.performCommand(command.toString());
                }
                punisher.sendMessage(prefix + GeneralHelper.translateColors(SuperSudoConfig.getConfig().getString("successful-sudo").replace("{player}", punishee.getName())));
                return true;
            } else {
                punisher.sendMessage(prefix + GeneralHelper.translateColors(plugin.getConfig().getString("not-a-player").replace("{player}", args[0])));
                return false;
            }
        } else {
            punisher.sendMessage(prefix + GeneralHelper.getNoPermission());
            return false;
        }
    }
}
