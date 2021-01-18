package beanbeanjuice.beancommands;

import beanbeanjuice.beancommands.commands.autoannouncer.AutoAnnouncer;
import beanbeanjuice.beancommands.commands.autoannouncer.AutoAnnouncerCommands;
import beanbeanjuice.beancommands.commands.generalcommands.GeneralCommands;
import beanbeanjuice.beancommands.commands.autoannouncer.AutoAnnouncerTabCompletion;
import beanbeanjuice.beancommands.commands.discord.Discord;
import beanbeanjuice.beancommands.commands.generalcommands.GeneralCommandsTabCompletion;
import beanbeanjuice.beancommands.commands.playervault.PlayerVaultCommand;
import beanbeanjuice.beancommands.commands.playervault.PlayerVaultInventoryHandler;
import beanbeanjuice.beancommands.commands.smitetimer.SmiteTimerCommands;
import beanbeanjuice.beancommands.commands.supersudo.SuperSudo;
import beanbeanjuice.beancommands.commands.torch.Torch;
import beanbeanjuice.beancommands.utility.GeneralHelper;
import beanbeanjuice.beancommands.utility.filehelper.autoannouncer.AutoAnnouncerConfig;
import beanbeanjuice.beancommands.utility.filehelper.autoannouncer.AutoAnnouncerMessages;
import beanbeanjuice.beancommands.utility.filehelper.playervault.PlayerVaultConfig;
import beanbeanjuice.beancommands.commands.playervault.PlayerVaultListener;
import beanbeanjuice.beancommands.utility.filehelper.playervault.PlayerVaultMessages;
import beanbeanjuice.beancommands.utility.filehelper.playervault.VaultData;
import beanbeanjuice.beancommands.utility.filehelper.smitetimer.SmiteTimerConfig;
import beanbeanjuice.beancommands.commands.smitetimer.SmiteTimerCreator;
import beanbeanjuice.beancommands.utility.filehelper.smitetimer.SmiteTimerMessages;
import beanbeanjuice.beancommands.utility.filehelper.supersudo.SuperSudoConfig;
import beanbeanjuice.beancommands.utility.filehelper.torch.TorchMessages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanCommands extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("BeanCommands.jar has been enabled...");
        setupConfigs();
        setupSmiteTimer();
        setupAutoAnnouncer();
        setupGeneralCommands();
        setupPlayerVault();
        new SuperSudo(this);
        new Discord(this);
        new Torch(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("BeanCommands.jar has been disabled...");
        if (!PlayerVaultInventoryHandler.getMap().isEmpty()) {
            VaultData.saveInventories();
        }
    }

    void setupConfigs() {
        saveDefaultConfig();
        SmiteTimerConfig.createConfig(this);
        SmiteTimerMessages.createConfig(this);
        AutoAnnouncerConfig.createConfig(this);
        AutoAnnouncerMessages.createConfig(this);
        TorchMessages.createConfig(this);
        SuperSudoConfig.createConfig(this);
        PlayerVaultMessages.createConfig(this);
        PlayerVaultConfig.createConfig(this);
        VaultData.createConfig(this);
    }

    void setupPlayerVault() {
        new PlayerVaultCommand(this);
        PlayerVaultInventoryHandler.createMap();
        this.getServer().getPluginManager().registerEvents(new PlayerVaultListener(), this);

        if (VaultData.getConfig().contains("data")) {
            VaultData.restoreInventories();
        }
    }

    void setupGeneralCommands() {
        new GeneralHelper(this);
        new GeneralCommands(this);
        new GeneralCommandsTabCompletion(this);
    }

    void setupSmiteTimer() {
        new SmiteTimerCommands(this);
        new SmiteTimerCreator(this);
    }

    void setupAutoAnnouncer() {
        new AutoAnnouncerCommands(this);
        new AutoAnnouncerTabCompletion(this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new AutoAnnouncer(), 20L, (AutoAnnouncerConfig.getConfig().getInt("time") * 20));
    }
}
