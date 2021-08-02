package com.oneshotmc.enchants.commands;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchants.axes.Blessed;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class BlessCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (!p.hasPermission("oneshotenchants.bless")) {
                p.sendMessage(Utils.color("&c&l(!) &cYou do not have permission to use that command."));
                return false;
            }
            boolean op = p.isOp();
            if (!op && p.hasMetadata("lastBlessCommand") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastBlessCommand").get(0)).asLong() < 5000L) {
                p.sendMessage(Utils.color("&c&l(!) &cYou can only use /bless once every 5 seconds."));
                return true;
            }
            p.setMetadata("lastBlessCommand", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
            Blessed.blessPlayer(p);
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.6F);
        }
        return true;
    }
}
