package beanbeanjuice.beancommands.commands.generalcommands;

import beanbeanjuice.beancommands.BeanCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class GeneralCommandsTabCompletion implements TabCompleter {

    private BeanCommands plugin;

    public GeneralCommandsTabCompletion(BeanCommands plugin) {
        this.plugin = plugin;
        plugin.getCommand("beancommands").setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            ArrayList<String> tabCompleteList = new ArrayList<>();
            tabCompleteList.add("reload");
            tabCompleteList.add("help");

            return tabCompleteList;
        }

        return null;
    }
}
