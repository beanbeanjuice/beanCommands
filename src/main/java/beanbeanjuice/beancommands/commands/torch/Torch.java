package beanbeanjuice.beancommands.commands.torch;

import beanbeanjuice.beancommands.BeanCommands;
import beanbeanjuice.beancommands.utility.GeneralHelper;
import beanbeanjuice.beancommands.utility.filehelper.torch.TorchMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Torch implements CommandExecutor {

    private BeanCommands plugin;

    public Torch(BeanCommands plugin) {
        this.plugin = plugin;
        plugin.getCommand("torch").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player receiver = Bukkit.getPlayer(args[0]);
                    if (hasGlow(receiver)) {
                        receiver.removePotionEffect(PotionEffectType.GLOWING);
                        receiver.sendMessage(GeneralHelper.getPrefix() + " " + GeneralHelper.translateColors(TorchMessages.getConfig().getString("torch-disabled")));
                        sender.sendMessage(GeneralHelper.getConsolePrefix() + GeneralHelper.translateColors(TorchMessages.getConfig().getString("torch-disabled-others").replace("{player}", receiver.getDisplayName())));
                    } else {
                        receiver.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 10000000, 1));
                        receiver.sendMessage(GeneralHelper.getPrefix() + " " + GeneralHelper.translateColors(TorchMessages.getConfig().getString("torch-enabled")));
                        sender.sendMessage(GeneralHelper.getConsolePrefix() + GeneralHelper.translateColors(TorchMessages.getConfig().getString("torch-enabled-others").replace("{player}", receiver.getDisplayName())));
                    }
                    return true;
                } else {
                    sender.sendMessage(GeneralHelper.getConsolePrefix() + GeneralHelper.translateColors("%s is not a player.").replace("%s", args[0]));
                    return false;
                }
            } else {
                sender.sendMessage(GeneralHelper.getConsolePrefix() + "Sorry, this command can only be run by players.");
            }
        }

        String prefix = GeneralHelper.getPrefix() + " ";

        if (args.length == 0) {
            Player receiver = (Player) sender;
            if (hasGlow(receiver)) {
                receiver.removePotionEffect(PotionEffectType.GLOWING);
                receiver.sendMessage(prefix + GeneralHelper.translateColors(TorchMessages.getConfig().getString("torch-disabled")));
            } else {
                receiver.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 10000000, 1));
                receiver.sendMessage(prefix + GeneralHelper.translateColors(TorchMessages.getConfig().getString("torch-enabled")));
            }
            return true;
        }

        if (args.length == 1) {
            if (sender.isOp() || sender.hasPermission("beancommands.torch.others")) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player receiver = Bukkit.getPlayer(args[0]);
                    if (hasGlow(receiver)) {
                        receiver.removePotionEffect(PotionEffectType.GLOWING);
                        receiver.sendMessage(GeneralHelper.getPrefix() + " " + GeneralHelper.translateColors(TorchMessages.getConfig().getString("torch-disabled")));
                        sender.sendMessage(prefix + GeneralHelper.translateColors(TorchMessages.getConfig().getString("torch-disabled-others").replace("{player}", receiver.getDisplayName())));
                    } else {
                        receiver.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 10000000, 1));
                        receiver.sendMessage(GeneralHelper.getPrefix() + " " + GeneralHelper.translateColors(TorchMessages.getConfig().getString("torch-enabled")));
                        sender.sendMessage(prefix + GeneralHelper.translateColors(TorchMessages.getConfig().getString("torch-enabled-others").replace("{player}", receiver.getDisplayName())));
                    }
                    return true;
                } else {
                    sender.sendMessage(prefix + GeneralHelper.translateColors("%s &cis not a player.").replace("%s", args[0]));
                    return false;
                }
            } else {
                sender.sendMessage(prefix + GeneralHelper.getNoPermission());
                return false;
            }
        } else {
            sender.sendMessage(prefix + GeneralHelper.translateColors("&cIncorrect usage. Do &d/torch &cor &d/torch (name)&c."));
            return false;
        }
    }

    boolean hasGlow(Player player) {
        return player.hasPotionEffect(PotionEffectType.GLOWING);
    }
}
