package beanbeanjuice.beancommands.commands.playervault;

import beanbeanjuice.beancommands.utility.GeneralHelper;
import beanbeanjuice.beancommands.utility.filehelper.playervault.PlayerVaultConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PlayerVaultListener implements Listener {

    @EventHandler
    public void onGUIClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().contains(GeneralHelper.translateColors("&c{player}'s Vault - ").replace("{player}", event.getPlayer().getName()))) {
            Player player = Bukkit.getPlayer(getPlayerClose(event));
            if (Bukkit.getPlayer(getPlayerClose(event)) == null) {
                player = (Player) Bukkit.getOfflinePlayer(getPlayerClose(event));
            }
            
            PlayerVaultInventoryHandler.setVault(player, getVaultNumClose(event), event.getInventory().getContents());
            Bukkit.getServer().getLogger().info((GeneralHelper.translateColors(PlayerVaultConfig.getConfig().getString("prefix")) + "Successfully saved {player}'s playervault.").replace("{player}", player.getName()));
        }
    }

    @EventHandler
    public void onGUIClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains(GeneralHelper.translateColors("&c{player}'s Vault - ").replace("{player}", event.getWhoClicked().getName()))) {
            Player player = Bukkit.getPlayer(getPlayerClick(event));
            if (Bukkit.getPlayer(getPlayerClick(event)) == null) {
                player = (Player) Bukkit.getOfflinePlayer(getPlayerClick(event));
            }
            if (event.isLeftClick() || event.isRightClick() || event.isShiftClick()) {
                Bukkit.broadcastMessage("other clicked hell yeah");

                PlayerVaultInventoryHandler.setVault(player, getVaultNumClick(event), event.getClickedInventory().getContents());
                Bukkit.getServer().getLogger().info((GeneralHelper.translateColors(PlayerVaultConfig.getConfig().getString("prefix")) + "Successfully saved {player}'s playervault.").replace("{player}", player.getName()));
                RefreshInventories.refreshInventory(player, getPlayerClick(event));
            }
        }
    }

    private int getVaultNumClose(InventoryCloseEvent event) {
        String title = event.getView().getTitle();
        String replaceTitle = GeneralHelper.translateColors("&c{player}'s Vault - ").replace("{player}", event.getPlayer().getName());
        title = title.replace(replaceTitle, "");
        return Integer.parseInt(title);
    }

    private int getVaultNumClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        String replaceTitle = GeneralHelper.translateColors("&c{player}'s Vault - ").replace("{player}", event.getWhoClicked().getName());
        title = title.replace(replaceTitle, "");
        return Integer.parseInt(title);
    }

    private String getPlayerClose(InventoryCloseEvent event) {
        String title = event.getView().getTitle();
        title = title.replace(GeneralHelper.translateColors("&c"), "");
        title = title.replace(GeneralHelper.translateColors(("'s Vault - %s").replace("%s", Integer.toString(getVaultNumClose(event)))), "");
        return title;
    }

    private String getPlayerClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        title = title.replace(GeneralHelper.translateColors("&c"), "");
        title = title.replace(GeneralHelper.translateColors(("'s Vault - %s").replace("%s", Integer.toString(getVaultNumClick(event)))), "");
        return title;
    }
}
