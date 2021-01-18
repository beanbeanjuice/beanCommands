package beanbeanjuice.beancommands.utility;

import beanbeanjuice.beancommands.BeanCommands;

public class GeneralHelper {

    private static String prefix;
    private static String nopermission;

    public GeneralHelper(BeanCommands plugin) {
        prefix = translateColors(plugin.getConfig().getString("prefix"));
        nopermission = translateColors(plugin.getConfig().getString("no-permission"));
    }

    public static String translateColors(String string) {
        return string.replaceAll("&", "§");
    }

    public static String getConsolePrefix() {
        return "[beanCommands] ";
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getNoPermission() {
        return nopermission;
    }
}
