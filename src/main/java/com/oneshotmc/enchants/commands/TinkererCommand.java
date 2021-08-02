package com.oneshotmc.enchants.commands;

import com.oneshotmc.enchants.utils.Config;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class TinkererCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("(!) You must be a player to use this command.");
            return false;
        }
        Player p = (Player)sender;
        if (!p.hasPermission("oneshotenchants.tinkerer")) {
            p.sendMessage(Utils.color("&c&l(!) &cYou do not have permission to use this command."));
            return false;
        }
        String tinkererName = Utils.color(Config.tinkererTitle);
        Inventory tinkerer = Bukkit.createInventory((InventoryHolder)p, 36, tinkererName);
        ItemStack spacer = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)0), " ", null, Boolean.valueOf(false));
        ItemStack acceptButton = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)5), 1, "&8&a&lCLICK TO TINKER ITEMS &8", new String[] { "&7Click to tinker your unused enchantment", "&7orbs and turn them into mystery dust pouches." }, false);
                tinkerer.setItem(27, spacer);
        tinkerer.setItem(28, spacer);
        tinkerer.setItem(29, spacer);
        tinkerer.setItem(30, spacer);
        tinkerer.setItem(31, acceptButton);
        tinkerer.setItem(32, spacer);
        tinkerer.setItem(33, spacer);
        tinkerer.setItem(34, spacer);
        tinkerer.setItem(35, spacer);
        p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1.0F, 1.0F);
        p.openInventory(tinkerer);
        return false;
    }
}
