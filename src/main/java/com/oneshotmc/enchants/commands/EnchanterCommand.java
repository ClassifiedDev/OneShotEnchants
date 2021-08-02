package com.oneshotmc.enchants.commands;

import com.oneshotmc.enchants.utils.Config;
import com.oneshotmc.enchants.utils.ExpUtils;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchanterCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("(!) You must be a player to use this command.");
            return false;
        }
        Player p = (Player)sender;
        ItemStack spacer = Utils.itemBuilder(Config.enchanterSpacerMaterial, Short.valueOf(Config.enchanterSpacerData), 1, Config.enchanterSpacerName, new String[] { "&8&l&7Your EXP: &f" + ExpUtils.getTotalExperience(p) }, Config.enchanterSpacerGlowing);
        ItemStack tier1Book = Utils.itemBuilder(Config.enchanterBook1Material, Short.valueOf(Config.enchanterBook1Data), 1, Config.enchanterBook1Name, Config.enchanterBook1Lore, Config.enchanterBook1Glowing);
        ItemStack tier2Book = Utils.itemBuilder(Config.enchanterBook2Material, Short.valueOf(Config.enchanterBook2Data), 1, Config.enchanterBook2Name, Config.enchanterBook2Lore, Config.enchanterBook2Glowing);
        ItemStack tier3Book = Utils.itemBuilder(Config.enchanterBook3Material, Short.valueOf(Config.enchanterBook3Data), 1, Config.enchanterBook3Name, Config.enchanterBook3Lore, Config.enchanterBook3Glowing);
        ItemStack tier4Book = Utils.itemBuilder(Config.enchanterBook4Material, Short.valueOf(Config.enchanterBook4Data), 1, Config.enchanterBook4Name, Config.enchanterBook4Lore, Config.enchanterBook4Glowing);
        ItemStack tier5Book = Utils.itemBuilder(Config.enchanterBook5Material, Short.valueOf(Config.enchanterBook5Data), 1, Config.enchanterBook5Name, Config.enchanterBook5Lore, Config.enchanterBook5Glowing);
        String enchanterName = Utils.color(Config.enchanterTitle);
        int enchanterSize = Config.enchanterSize;
        Inventory enchanter = Bukkit.getServer().createInventory(null, enchanterSize, enchanterName);
        for (int i = 0; i < enchanterSize; i++)
            enchanter.setItem(i, spacer);
        enchanter.setItem(Config.enchanterBook1InvSlot, tier1Book);
        enchanter.setItem(Config.enchanterBook2InvSlot, tier2Book);
        enchanter.setItem(Config.enchanterBook3InvSlot, tier3Book);
        enchanter.setItem(Config.enchanterBook4InvSlot, tier4Book);
        enchanter.setItem(Config.enchanterBook5InvSlot, tier5Book);
        p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
        p.openInventory(enchanter);
        return false;
    }
}
