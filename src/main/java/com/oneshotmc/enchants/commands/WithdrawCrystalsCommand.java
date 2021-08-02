package com.oneshotmc.enchants.commands;

import java.util.ListIterator;

import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.crystals.ItemUtils;
import com.oneshotmc.enchants.crystals.Tracker;
import com.oneshotmc.enchants.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WithdrawCrystalsCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player p = (Player)sender;
        if (args.length != 1) {
            sender.sendMessage("");
            sender.sendMessage(Utils.color("&3&lCrystal Commands "));
                    sender.sendMessage("");
            sender.sendMessage(Utils.color("&3/withdrawcrystals <amount>"));
            sender.sendMessage(Utils.color("&8&l&7Withdraw harvested crystals from the item in your hand."));
            sender.sendMessage("");
            sender.sendMessage(Utils.color("&3/splitcrystals <amount>"));
            sender.sendMessage(Utils.color("&8&lharvested souls from a Crystal  in your hand."));
            sender.sendMessage("");
            return true;
        }
        if (!StringUtils.isNumeric(args[0])) {
            p.sendMessage(Utils.color("&c&l(!) &cPlease enter a valid number!"));
            return true;
        }
        int amount = Integer.parseInt(args[0]);
        if (amount <= 0) {
            p.sendMessage(Utils.color("&c&l(!) &cPlease enter a valid amount."));
            return true;
        }
        if (p.getItemInHand() == null || !ItemUtils.isTrackable(p.getItemInHand())) {
            if (p.getItemInHand() == null || !CrystalAPI.isMythicCrystal(p.getItemInHand())) {
                p.sendMessage(Utils.color("&c&l(!) &cYou must have a valid Mythic Crystal in your hand to withdraw crystals from."));
                return true;
            }
            if (p.getItemInHand().getAmount() > 1) {
                p.sendMessage(Utils.color("&c&l(!) &cYou cannot split stacked Mythic Crystals."));
                return true;
            }
            int souls = CrystalAPI.getCrystals(p.getItemInHand());
            if (souls <= amount) {
                p.sendMessage(Utils.color("&c&l(!) &cYou do not have enough crystals on this item."));
                p.sendMessage(Utils.color("&8&l&7Item Crystals: " + souls));
                return true;
            }
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage(Utils.color("You do not have enough inventory space to withdraw crystals!"));
                return true;
            }
            int newSouls = souls - amount;
            if (newSouls > 0) {
                CrystalAPI.setCrystals(p.getItemInHand(), newSouls);
            } else {
                p.getInventory().removeItem(new ItemStack[] { p.getItemInHand() });
                p.updateInventory();
            }
            ItemStack newSoulItem = CrystalAPI.createCrystal(amount);
            p.getInventory().addItem(new ItemStack[] { newSoulItem });
            p.sendMessage(Utils.color("&a&l(!) &7Your crystal has been split into two items successfully."));
            p.sendMessage(Utils.color("&a+ " + amount + " Crystal(s)"));
            p.playSound(p.getLocation(), Sound.ANVIL_USE, 1.0F, 1.4F);
            p.updateInventory();
            return true;
        }
        ItemStack is = p.getItemInHand();
        int kills = ItemUtils.getTrackedKills(is);
        Tracker tracker = Tracker.getTrackerType(is);
        if (kills == -1) {
            p.sendMessage(Utils.color("&c&l(!) &cThis item has no harvested crystals."));
            p.sendMessage(Utils.color("&7Equip this item with a Crystal Tracker to collect crystals."));
            return true;
        }
        if (tracker == null) {
            p.sendMessage(Utils.color("&c&l(!) &cThere is no valid crystal tracker on this item."));
            return true;
        }
        if (tracker.trackMobKills()) {
            if (amount > kills / 100) {
                p.sendMessage(Utils.color("&c&l(!) &cYou do not have enough mob crystals (" + (amount * 100) + ") on this item."));
                p.sendMessage(Utils.color("&3Mob Crystals: &7" + kills));
                p.sendMessage(Utils.color("&3Exchange: &7100 mob crystal = 1 player crystal"));
                return true;
            }
        } else if (amount > kills) {
            p.sendMessage(Utils.color("&c&l(!) &cYou do not have enough player crystals on this item."));
            p.sendMessage(Utils.color("Player Crystals: &7" + kills));
            return true;
        }
        int shards = tracker.trackMobKills() ? amount : (amount * tracker.getRank() * 5);
        if (p.getInventory().firstEmpty() == -1) {
            p.sendMessage(Utils.color("&c&l(!) &cYou do not have enough inventory space for the Mythic Crystal."));
            return true;
        }
        ItemStack shard = CrystalAPI.createCrystal(shards);
        ItemUtils.setTrackedKills(is, tracker, tracker.trackMobKills() ? (kills - amount * 100) : (kills - amount));
        p.getInventory().addItem(new ItemStack[] { shard });
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0F, 1.9F);
        p.sendMessage(tracker.getColor() + ChatColor.BOLD.toString() + "- " + (tracker.trackMobKills() ? (amount * 100) : amount) + " " + tracker.getName() + " Crystals");
        p.sendMessage(Utils.color("&a+ " + shards + " Crystal(s)"));
        p.updateInventory();
        return true;
    }

    public int getEmptyInventorySpace(Player p) {
        int num = 0;
        for (ListIterator<ItemStack> listIterator = p.getInventory().iterator(); listIterator.hasNext(); ) {
            ItemStack is = listIterator.next();
            if (is == null || is.getType() == Material.AIR)
                num++;
        }
        return num;
    }
}
