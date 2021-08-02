package com.oneshotmc.enchants.commands;

import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.crystals.Tracker;
import com.oneshotmc.enchants.utils.Config;
import com.oneshotmc.enchants.utils.CustomItems;
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

public class EnchantAdminCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("enchantadmin")) {
            if (p.hasPermission("oneshotenchants.admin")) {
                Inventory inv = Bukkit.createInventory(null, 45, Utils.color(Config.enchantAdminTitle));
                ItemStack spacer = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)0), 1, " ", new String[] { "" }, false);
                for (int i = 0; i < 45; i++)
                    inv.setItem(i, spacer);
                inv.setItem(0, CustomItems.tier1MysteryEnchantmentItem);
                inv.setItem(1, CustomItems.tier2MysteryEnchantmentItem);
                inv.setItem(2, CustomItems.tier3MysteryEnchantmentItem);
                inv.setItem(3, CustomItems.tier4MysteryEnchantmentItem);
                inv.setItem(4, CustomItems.tier5MysteryEnchantmentItem);
                inv.setItem(5, CustomItems.tier6MysteryEnchantmentItem);
                inv.setItem(6, CustomItems.tier1MysteryDustItem);
                inv.setItem(7, CustomItems.tier2MysteryDustItem);
                inv.setItem(8, CustomItems.tier3MysteryDustItem);
                inv.setItem(9, CustomItems.tier4MysteryDustItem);
                inv.setItem(10, CustomItems.tier5MysteryDustItem);
                inv.setItem(11, CustomItems.tier6MysteryDustItem);
                inv.setItem(12, CustomItems.dustItem("tier1", 10));
                inv.setItem(13, CustomItems.dustItem("tier2", 10));
                inv.setItem(14, CustomItems.dustItem("tier3", 10));
                inv.setItem(15, CustomItems.dustItem("tier4", 10));
                inv.setItem(16, CustomItems.dustItem("tier5", 10));
                inv.setItem(17, CustomItems.dustItem("tier6", 10));
                inv.setItem(18, CustomItems.protectionItem);
                inv.setItem(19, CustomItems.organizeScrollItem);
                inv.setItem(20, CustomItems.renameScrollItem);
                inv.setItem(21, CustomItems.disenchantItem(Utils.randomInt(1, 100)));
                inv.setItem(22, CustomItems.disenchantItem(100));
                inv.setItem(23, CustomItems.lootingIV);
                inv.setItem(24, CustomItems.lootingV);
                inv.setItem(25, CustomItems.depthStriderI);
                inv.setItem(26, CustomItems.depthStriderII);
                inv.setItem(27, CustomItems.depthStriderIII);
                ItemStack allTier1 = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)8), 1, "&7&lALL COMMON ENCHANTMENTS", new String[] { "&7Click to receive ALL &7&nCommon&r", "&7enchantments.", " ", "&c&lTIP: &7Have an empty inventory." }, false);
                ItemStack allTier2 = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)5), 1, "&a&lALL UNCOMMON ENCHANTMENTS", new String[] { "&7Click to receive ALL &a&nUncommon&r", "&7enchantments.", " ", "&c&lTIP: &7Have an empty inventory." }, false);
                ItemStack allTier3 = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)3), 1, "&b&lALL RARE ENCHANTMENTS", new String[] { "&7Click to receive ALL &b&nRare&r", "&7enchantments.", " ", "&c&lTIP: &7Have an empty inventory." }, false);
                ItemStack allTier4 = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)4), 1, "&e&lALL ULTIMATE ENCHANTMENTS", new String[] { "&7Click to receive ALL &e&nUltimate&r", "&7enchantments.", " ", "&c&lTIP: &7Have an empty inventory." }, false);
                ItemStack allTier5 = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)1), 1, "&6&lALL LEGENDARY ENCHANTMENTS", new String[] { "&7Click to receive ALL &6&nLegendary&r", "&7enchantments.", " ", "&c&lTIP: &7Have an empty inventory." }, false);
                ItemStack allTier6 = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)14), 1, "&c&lALL MYTHIC ENCHANTMENTS", new String[] { "&7Click to receive ALL &c&nMythic&r", "&7enchantments.", " ", "&c&lTIP: &7Have an empty inventory." }, false);
                inv.setItem(28, allTier1);
                inv.setItem(29, allTier2);
                inv.setItem(30, allTier3);
                inv.setItem(31, allTier4);
                inv.setItem(32, allTier5);
                inv.setItem(33, allTier6);
                inv.setItem(34, CrystalAPI.createCrystal(1000));
                inv.setItem(35, CrystalAPI.buildTracker(Tracker.COMMON));
                inv.setItem(36, CrystalAPI.buildTracker(Tracker.UNCOMMON));
                inv.setItem(37, CrystalAPI.buildTracker(Tracker.RARE));
                inv.setItem(38, CrystalAPI.buildTracker(Tracker.ULTIMATE));
                inv.setItem(39, CrystalAPI.buildTracker(Tracker.LEGENDARY));
                inv.setItem(39, CrystalAPI.buildTracker(Tracker.MYTHIC));
                p.openInventory(inv);
                p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
            }
            return false;
        }
        return false;
    }
}
