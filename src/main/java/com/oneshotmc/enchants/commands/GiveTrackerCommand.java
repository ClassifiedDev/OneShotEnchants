package com.oneshotmc.enchants.commands;

import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.crystals.Tracker;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveTrackerCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!sender.hasPermission("oneshotenchants.give"))
            return true;
        if (args.length == 2) {
            String name = args[0];
            String tracker = args[1].toUpperCase();
            try {
                Tracker tr = Tracker.valueOf(tracker);
                Player p = Bukkit.getPlayer(name);
                if (p == null) {
                    sender.sendMessage(Utils.color("&c&l(!) &cThe specified player could not be found, please try again."));
                    return true;
                }
                ItemStack is = CrystalAPI.buildTracker(tr);
                if (p.getInventory().firstEmpty() == -1) {
                    p.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so your tracker was dropped on the ground."));
                    p.getWorld().dropItem(p.getLocation(), is);
                } else {
                    p.getInventory().addItem(new ItemStack[] { is });
                }
                sender.sendMessage(Utils.color("&a&l(!) &7You have given &a" + p.getName() + "&7 a Crystal Tracker!"));
            } catch (Exception e) {
                sender.sendMessage(Utils.color("&c&l(!) Invalid Tracker Type, valid trackers include..."));
                byte b = 0;
                Tracker[] arrayOfTracker;
                for (int i = (arrayOfTracker = Tracker.values()).length; b < i; ) {
                    Tracker t = arrayOfTracker[b];
                    sender.sendMessage(ChatColor.GRAY + t.name());
                    b = (byte)(b + 1);
                }
            }
        } else {
            sender.sendMessage(Utils.color("&c&l(!) &cUsage: &7/givetracker <name> <trackertype>"));
        }
        return false;
    }
}
