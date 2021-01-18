package beanbeanjuice.beancommands.commands.autoannouncer;

import beanbeanjuice.beancommands.BeanCommands;
import beanbeanjuice.beancommands.utility.GeneralHelper;
import beanbeanjuice.beancommands.utility.filehelper.autoannouncer.AutoAnnouncerConfig;
import beanbeanjuice.beancommands.utility.filehelper.autoannouncer.AutoAnnouncerMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoAnnouncerCommands implements CommandExecutor {

    private BeanCommands plugin;
    public static boolean isEnabled = true;

    public AutoAnnouncerCommands(BeanCommands plugin) {
        this.plugin = plugin;
        plugin.getCommand("autoannouncer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return logic(sender, args);
        }

        Player player = (Player) sender;
        String prefix = GeneralHelper.getPrefix() + " ";

        if (player.hasPermission("beancommands.autoannouncer") || player.isOp()) {
            return logic(sender, args);
        } else {
            player.sendMessage(prefix + GeneralHelper.getNoPermission());
            return false;
        }
    }

    boolean logic(CommandSender sender, String[] args) {
        if (args.length < 2) {
            if (args[0].equalsIgnoreCase("list")) {
                listBroadcasts(sender);
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {
                listHelp(sender);
                return true;
            } else {
                sender.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors("&cIncorrect usage. For help, do /autoannouncer help"));
                return false;
            }
        } else if (args[0].equalsIgnoreCase("addbroadcast")) {
            String string = addBroadcast(args);
            sender.sendMessage(GeneralHelper.getPrefix() + " " + GeneralHelper.translateColors(AutoAnnouncerConfig.getConfig().getString("successful-add").replace("{broadcast}", string)));
            return true;
        } else if (args[0].equalsIgnoreCase("removebroadcast")) {
            if (AutoAnnouncerMessages.getConfig().getStringList("messages").size() >= Integer.parseInt(args[1]) && Integer.parseInt(args[1]) >= 1) {
                AutoAnnouncerMessages.removeBroadcast(Integer.parseInt(args[1]));
                sender.sendMessage(GeneralHelper.getPrefix() + " " + GeneralHelper.translateColors("&cSuccessfully removed broadcast."));
                return true;
            } else {
                sender.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(("&cYou must input a number between 1 and %s.").replace("%s", Integer.toString(AutoAnnouncerMessages.getConfig().getStringList("messages").size()))));
                return false;
            }
        } else {
            sender.sendMessage(GeneralHelper.getPrefix() + plugin.getConfig().getStringList("incorrect-usage"));
            listHelp(sender);
            return false;
        }
    }

    String addBroadcast(String[] args) {
        StringBuilder string = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            string.append(args[i]);
        }
        AutoAnnouncerMessages.addBroadcast(string.toString());
        return string.toString();
    }

    void listBroadcasts(CommandSender sender) {
        sender.sendMessage(GeneralHelper.getPrefix() + " " + GeneralHelper.translateColors(AutoAnnouncerConfig.getConfig().getString("list-broadcasts")));
        int count = 1;
        for (String string : AutoAnnouncerMessages.getConfig().getStringList("messages")) {
            sender.sendMessage(count++ + ". " + GeneralHelper.translateColors(string));
        }
    }

    void listHelp(CommandSender sender) {
        sender.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(AutoAnnouncerConfig.getConfig().getString("help-message")));
        int count = 1;
        for (String string : AutoAnnouncerConfig.getConfig().getStringList("help-messages")) {
            sender.sendMessage(count++ + " " + GeneralHelper.translateColors(string));
        }
    }
}
