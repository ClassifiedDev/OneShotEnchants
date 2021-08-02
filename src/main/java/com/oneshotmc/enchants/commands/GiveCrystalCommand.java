package com.oneshotmc.enchants.commands;

import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GiveCrystalCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("givecrystal")) {
            if (!sender.hasPermission("oneshotenchants.give")) {
                sender.sendMessage(Utils.color("&c&l(!) &cYou do not have permission to use that command."));
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage(Utils.color("&c&l(!) &cUsage: &7/givecrystal <player> <amount> <crystal amount>"));
                return false;
            }
            if (args.length == 3) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.color("&c&l(!) &cThe specified player could not be found, please try again."));
                    return false;
                }
                PlayerInventory ti = target.getInventory();
                int crystals = Integer.parseInt(args[2]);
                int amount = Integer.parseInt(args[1]);
                for (int i = 0; i < amount; i++) {
                    if (target.getInventory().firstEmpty() != -1) {
                        target.getInventory().addItem(new ItemStack[] { CrystalAPI.createCrystal(crystals) });
                        target.updateInventory();
                    } else {
                        target.getWorld().dropItem(target.getLocation(), CrystalAPI.createCrystal(crystals));
                        target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the Mythic Crystal has been dropped on the ground."));
                    }
                }
            }
        }
        return false;
    }
}
