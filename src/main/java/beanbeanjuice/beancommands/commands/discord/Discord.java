package beanbeanjuice.beancommands.commands.discord;

import beanbeanjuice.beancommands.BeanCommands;
import beanbeanjuice.beancommands.utility.GeneralHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Discord implements CommandExecutor {

    private BeanCommands plugin;

    public Discord(BeanCommands plugin) {
        this.plugin = plugin;
        plugin.getCommand("discord").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(GeneralHelper.getPrefix() + " " + GeneralHelper.translateColors(plugin.getConfig().getString("discord")));
        return true;
    }
}
