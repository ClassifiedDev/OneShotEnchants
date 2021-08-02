package com.oneshotmc.enchants.commands;

import java.util.ArrayList;
import java.util.UUID;

import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand implements CommandExecutor {
    private ArrayList<UUID> debugEnabled = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (!p.hasPermission("oneshotenchants.debug")) {
                p.sendMessage(Utils.color("&c&l(!) &cYou do not have permission to use that command."));
                return false;
            }
            boolean op = p.isOp();
            if (!this.debugEnabled.contains(p.getUniqueId())) {
                p.sendMessage(Utils.color("&a&l(!) &7Enchantment &3&l&nDEBUG&r&7 messages have been &a&lENABLED&7."));
                this.debugEnabled.add(p.getUniqueId());
                return false;
            }
            p.sendMessage(Utils.color("&c&l(!) &7Enchantment &3&l&nDEBUG&r&7 messages have been &c&lDISABLED&7."));
            this.debugEnabled.remove(p.getUniqueId());
            return false;
        }
        return true;
    }
}
