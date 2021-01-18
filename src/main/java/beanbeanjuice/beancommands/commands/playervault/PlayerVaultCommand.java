package beanbeanjuice.beancommands.commands.playervault;

import beanbeanjuice.beancommands.BeanCommands;
import beanbeanjuice.beancommands.utility.GeneralHelper;
import beanbeanjuice.beancommands.utility.filehelper.playervault.PlayerVaultConfig;
import beanbeanjuice.beancommands.utility.filehelper.playervault.PlayerVaultMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class PlayerVaultCommand implements CommandExecutor {
    private BeanCommands plugin;

    public PlayerVaultCommand(BeanCommands plugin) {
        this.plugin = plugin;
        plugin.getCommand("playervault").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(GeneralHelper.getConsolePrefix() + PlayerVaultMessages.getConfig().getString("is-console"));
            return false;
        }

        Player player = (Player) sender;
        String prefix = GeneralHelper.translateColors(PlayerVaultConfig.getConfig().getString("prefix")) + " ";

        if (args.length >= 1 && args.length <= 2) {
            if (!isInteger(args[0])) {
                if (args[1] == null) {
                    player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(PlayerVaultMessages.getConfig().getString("incorrect-usage-others")));
                    return false;
                }
                if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0])) && Bukkit.getOfflinePlayer(args[0]) != null) {
                    if (player.hasPermission("beancommands.playervault.others") || player.isOp()) {
                        Player receiver;
                        player.sendMessage(prefix + GeneralHelper.translateColors("&cIf you see this, this function does not work properly yet."));
                        if (Bukkit.getPlayer(args[0]) != null) {
                            Bukkit.broadcastMessage("1");
                            receiver = Bukkit.getPlayer(args[0]);
                        } else if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                            Bukkit.broadcastMessage("2");
                            receiver = (Player) Bukkit.getOfflinePlayer(args[0]);
                        } else {
                            Bukkit.broadcastMessage("3");
                            player.sendMessage(prefix + GeneralHelper.translateColors(PlayerVaultMessages.getConfig().getString("not-a-player").replace("{player}", args[0])));
                            return false;
                        }

                        String title = GeneralHelper.translateColors("&c{player}'s Vault - {num}").replace("{player}", receiver.getName()).replace("{num}", args[1]);
                        Inventory vault = Bukkit.createInventory(player, getVaultSize(receiver), title);
                        Bukkit.broadcastMessage("4");
                        if (PlayerVaultInventoryHandler.getMap().containsKey(receiver.getUniqueId().toString())) {
                            Bukkit.broadcastMessage("5");
                            vault.setContents(PlayerVaultInventoryHandler.getInventory(receiver, args[1]));
                            Bukkit.broadcastMessage("6");
                        }
                        player.openInventory(vault);
                        RefreshInventories.addPlayer(player, title);
                        Bukkit.broadcastMessage("7");
                        return true;
                    }

                    player.sendMessage(prefix + GeneralHelper.getNoPermission());
                    return false;
                }

                player.sendMessage(prefix + GeneralHelper.translateColors(PlayerVaultMessages.getConfig().getString("not-a-player").replace("{player}", args[0])));
                return false;
            }

            int highestVault = getHighestPermission(player);

            if (Integer.parseInt(args[0]) < 1 && !player.isOp()) {
                player.sendMessage(prefix + GeneralHelper.translateColors(PlayerVaultMessages.getConfig().getString("not-0")));
                return false;
            }

            if (Integer.parseInt(args[0]) > highestVault && !player.isOp()) {
                player.sendMessage(prefix + GeneralHelper.translateColors(PlayerVaultMessages.getConfig().getString("vault-length").replace("{max-length}", Integer.toString(highestVault))));
                return false;
            }

            if (highestVault >= 1 || player.isOp()) {
                warningMessage(player);
                String title = GeneralHelper.translateColors("&c{player}'s Vault - {num}").replace("{player}", player.getName()).replace("{num}", args[0]);
                Inventory vault = Bukkit.createInventory(player, getVaultSize(player), title);
                if (PlayerVaultInventoryHandler.getMap().containsKey(player.getUniqueId().toString())) {
                    vault.setContents(PlayerVaultInventoryHandler.getInventory(player, args[0]));
                }
                player.openInventory(vault);
                RefreshInventories.addPlayer(player, title);
                return true;
            }

            player.sendMessage(prefix + GeneralHelper.translateColors(PlayerVaultMessages.getConfig().getString("max-vault").replace("{vault-size}", Integer.toString(highestVault))));
            return false;

        }

        player.sendMessage(prefix + GeneralHelper.translateColors(PlayerVaultMessages.getConfig().getString("incorrect-usage")));
        return false;
    }

    public static int getHighestPermission(Player player) {
        int highest = 0;
        String temp;
        int tempNum;
        for (PermissionAttachmentInfo permissionAttachment : player.getEffectivePermissions()) {
            String permission = permissionAttachment.getPermission();
            if (permission.startsWith("beancommands.playervault.")) {
                temp = permission.replace("beancommands.playervault.", "");
                if (isInteger(temp)) {
                    tempNum = Integer.parseInt(temp);
                    if (highest < tempNum) {
                        highest = tempNum;
                    }
                }
            }
        }
        return highest;
    }

    static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static int getVaultSize(Player player) {
        int highest = 9;
        String temp;
        int tempNum;
        for (PermissionAttachmentInfo permissionAttachment : player.getEffectivePermissions()) {
            String permission = permissionAttachment.getPermission();
            if (permission.startsWith("beancommands.playervault.size.")) {
                temp = permission.replace("beancommands.playervault.size.", "");
                if (isInteger(temp)) {
                    tempNum = Integer.parseInt(temp);
                    if (highest < tempNum) {
                        highest = tempNum;
                    }
                }
            }
        }

        if (player.isOp()) {
            highest = 54;
        }
        return highest;
    }

    private void warningMessage(Player player) {
        for (String string : PlayerVaultMessages.getConfig().getStringList("warning-message")) {
            player.sendMessage(GeneralHelper.translateColors(string));
        }
    }
}
