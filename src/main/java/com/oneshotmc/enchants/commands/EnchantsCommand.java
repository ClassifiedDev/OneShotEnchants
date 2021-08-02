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
import org.bukkit.inventory.ItemStack;

public class EnchantsCommand implements CommandExecutor {
    public static void openEnchantsInv(Player p) {
        p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
        Inventory inv = Bukkit.createInventory(null, Config.enchantsSize, Utils.color(Config.enchantsTitle));
        ItemStack spacer = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)0), " ", null, Boolean.valueOf(false));
        for (int i = 0; i < Config.enchantsSize; i++)
            inv.setItem(i, spacer);
        ItemStack tier1 = Utils.itemBuilder(Config.enchantsBook1Material, Short.valueOf(Config.enchantsBook1Data), 1, Config.enchantsBook1Name, Config.enchantsBook1Lore, Config.enchantsBook1Glowing);
        ItemStack tier2 = Utils.itemBuilder(Config.enchantsBook2Material, Short.valueOf(Config.enchantsBook2Data), 1, Config.enchantsBook2Name, Config.enchantsBook2Lore, Config.enchantsBook2Glowing);
        ItemStack tier3 = Utils.itemBuilder(Config.enchantsBook3Material, Short.valueOf(Config.enchantsBook3Data), 1, Config.enchantsBook3Name, Config.enchantsBook3Lore, Config.enchantsBook3Glowing);
        ItemStack tier4 = Utils.itemBuilder(Config.enchantsBook4Material, Short.valueOf(Config.enchantsBook4Data), 1, Config.enchantsBook4Name, Config.enchantsBook4Lore, Config.enchantsBook4Glowing);
        ItemStack tier5 = Utils.itemBuilder(Config.enchantsBook5Material, Short.valueOf(Config.enchantsBook5Data), 1, Config.enchantsBook5Name, Config.enchantsBook5Lore, Config.enchantsBook5Glowing);
        ItemStack tier6 = Utils.itemBuilder(Config.enchantsBook6Material, Short.valueOf(Config.enchantsBook6Data), 1, Config.enchantsBook6Name, Config.enchantsBook6Lore, Config.enchantsBook6Glowing);
        inv.setItem(Config.enchantsBook1InvSlot, tier1);
        inv.setItem(Config.enchantsBook2InvSlot, tier2);
        inv.setItem(Config.enchantsBook3InvSlot, tier3);
        inv.setItem(Config.enchantsBook4InvSlot, tier4);
        inv.setItem(Config.enchantsBook5InvSlot, tier5);
        p.openInventory(inv);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.color("&c&(!) &cYou must be a player to use this command."));
            return false;
        }
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("enchants"))
            openEnchantsInv(p);
        return false;
    }
}
