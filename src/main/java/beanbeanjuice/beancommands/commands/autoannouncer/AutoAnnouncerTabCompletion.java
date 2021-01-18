package beanbeanjuice.beancommands.commands.autoannouncer;

import beanbeanjuice.beancommands.BeanCommands;
import beanbeanjuice.beancommands.utility.filehelper.autoannouncer.AutoAnnouncerMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AutoAnnouncerTabCompletion implements TabCompleter {

    private BeanCommands plugin;

    public AutoAnnouncerTabCompletion(BeanCommands plugin) {
        this.plugin = plugin;
        plugin.getCommand("autoannouncer").setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            ArrayList<String> tabCompleteList = new ArrayList<>();
            tabCompleteList.add("addbroadcast");
            tabCompleteList.add("removebroadcast");
            tabCompleteList.add("list");
            tabCompleteList.add("help");

            return tabCompleteList;
        }

        if (args[0].equalsIgnoreCase("removebroadcast")) {
            ArrayList<String> tabCompleteList = new ArrayList<>();

            for (int i = 1; i <= AutoAnnouncerMessages.getConfig().getStringList("messages").size(); i++) {
                tabCompleteList.add(Integer.toString(i));
            }

            return tabCompleteList;
        }

        return null;
    }
}
