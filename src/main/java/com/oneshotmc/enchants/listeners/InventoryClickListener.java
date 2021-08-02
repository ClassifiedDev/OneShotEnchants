package com.oneshotmc.enchants.listeners;

import com.oneshotmc.enchants.commands.EnchantsCommand;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.EnchantmentBook;
import com.oneshotmc.enchants.enchantmentapi.TieredEnchantments;
import com.oneshotmc.enchants.utils.Config;
import com.oneshotmc.enchants.utils.CustomItems;
import com.oneshotmc.enchants.utils.ExpUtils;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onEnchanterClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equals(Utils.color(Config.enchanterTitle))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (!e.getCurrentItem().hasItemMeta()) {
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchanterBook1Name))) {
                int tier1Cost = Config.enchanterBook1Price;
                int xp = ExpUtils.getTotalExperience(p);
                if (xp >= tier1Cost) {
                    p.getInventory().addItem(CustomItems.tier1MysteryEnchantmentItem);

                    //p.setExp(0.0F);
                    //p.setTotalExperience(0);
                    //p.setLevel(0);
                    //p.giveExp(xp - tier1Cost);
                    ExpUtils.setTotalExperience(p, xp-tier1Cost);
                    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 2.0F);
                    p.sendMessage(Utils.color("&c- " + tier1Cost + " XP"));
                    e.setCancelled(true);
                    p.closeInventory();
                    p.performCommand("enchanter");
                    return;
                }
                p.sendMessage(Utils.color("&c&l(!) &cYou do not have enough XP to buy this Mystery Enchantment."));
                p.sendMessage(Utils.color("&8&l&cEnchantment Cost: &7" + tier1Cost + " XP"));
                p.sendMessage(Utils.color("&8&l&aYour current XP: &7" + ExpUtils.getTotalExperience(p) + " XP"));
                p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1.0F, 2.0F);
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchanterBook2Name))) {
                int tier2Cost = Config.enchanterBook2Price;
                int xp = ExpUtils.getTotalExperience(p);
                if (xp >= tier2Cost) {
                    p.getInventory().addItem(CustomItems.tier2MysteryEnchantmentItem);

                    //p.setExp(0.0F);
                    //p.setTotalExperience(0);
                    //p.setLevel(0);
                    //p.giveExp(xp - tier2Cost);
                    ExpUtils.setTotalExperience(p, xp-tier2Cost);
                    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 2.0F);
                    p.sendMessage(Utils.color("&c- " + tier2Cost + " XP"));
                    e.setCancelled(true);
                    p.closeInventory();
                    p.performCommand("enchanter");
                    return;
                }
                p.sendMessage(Utils.color("&c&l(!) &cYou do not have enough XP to buy this Mystery Enchantment."));
                p.sendMessage(Utils.color("&8&l&cEnchantment Cost: &7" + tier2Cost + " XP"));
                p.sendMessage(Utils.color("&8&l&aYour current XP: &7" + ExpUtils.getTotalExperience(p) + " XP"));
                p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1.0F, 2.0F);
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchanterBook3Name))) {
                int tier3Cost = Config.enchanterBook3Price;
                int xp = ExpUtils.getTotalExperience(p);
                if (xp >= tier3Cost) {
                    p.getInventory().addItem(CustomItems.tier3MysteryEnchantmentItem);

                    //p.setExp(0.0F);
                    //p.setTotalExperience(0);
                    //p.setLevel(0);
                    //p.giveExp(xp - tier3Cost);
                    ExpUtils.setTotalExperience(p, xp-tier3Cost);
                    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 2.0F);
                    p.sendMessage(Utils.color("&c- " + tier3Cost + " XP"));
                    e.setCancelled(true);
                    p.closeInventory();
                    p.performCommand("enchanter");
                    return;
                }
                p.sendMessage(Utils.color("&c&l(!) &cYou do not have enough XP to buy this Mystery Enchantment."));
                p.sendMessage(Utils.color("&8&l&cEnchantment Cost: &7" + tier3Cost + " XP"));
                p.sendMessage(Utils.color("&8&l&aYour current XP: &7" + ExpUtils.getTotalExperience(p) + " XP"));
                p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1.0F, 2.0F);
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchanterBook4Name))) {
                int tier4Cost = Config.enchanterBook4Price;
                int xp = ExpUtils.getTotalExperience(p);
                if (xp >= tier4Cost) {
                    p.getInventory().addItem(CustomItems.tier4MysteryEnchantmentItem);

                    //p.setExp(0.0F);
                    //p.setTotalExperience(0);
                    //p.setLevel(0);
                    //p.giveExp(xp - tier4Cost);
                    ExpUtils.setTotalExperience(p, xp-tier4Cost);
                    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 2.0F);
                    p.sendMessage(Utils.color("&c- " + tier4Cost + " XP"));
                    e.setCancelled(true);
                    p.closeInventory();
                    p.performCommand("enchanter");
                    return;
                }
                p.sendMessage(Utils.color("&c&l(!) &cYou do not have enough XP to buy this Mystery Enchantment."));
                p.sendMessage(Utils.color("&8&l&cEnchantment Cost: &7" + tier4Cost + " XP"));
                p.sendMessage(Utils.color("&8&l&aYour current XP: &7" + ExpUtils.getTotalExperience(p) + " XP"));
                p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1.0F, 2.0F);
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchanterBook5Name))) {
                int tier5Cost = Config.enchanterBook5Price;
                //System.out.println(ExpUtils.getTotalExperience(p));
                int xp = ExpUtils.getTotalExperience(p);
                if (xp >= tier5Cost) {
                    p.getInventory().addItem(CustomItems.tier5MysteryEnchantmentItem);

                    //p.setExp(0.0F);
                    //p.setTotalExperience(0);
                    //p.setLevel(0);
                    //p.giveExp(xp - tier5Cost);
                    ExpUtils.setTotalExperience(p, xp-tier5Cost);
                    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1.0F, 2.0F);
                    p.sendMessage(Utils.color("&c- " + tier5Cost + " XP"));
                    e.setCancelled(true);
                    p.closeInventory();
                    p.performCommand("enchanter");
                    return;

                }
                p.sendMessage(Utils.color("&c&l(!) &cYou do not have enough XP to buy this Mystery Enchantment."));
                p.sendMessage(Utils.color("&8&l&cEnchantment Cost: &7" + tier5Cost + " XP"));
                p.sendMessage(Utils.color("&8&l&aYour current XP: &7" + ExpUtils.getTotalExperience(p) + " XP"));
                p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1.0F, 2.0F);
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            return;
        }
    }

    ItemStack back = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)14), 1, "&7&c&lBack &7", new String[] { "&7Click view enchant tiers." }, false);

    @EventHandler
    public void onEnchantsClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equals(Utils.color(Config.enchantsTitle))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (!e.getCurrentItem().hasItemMeta()) {
                e.setCancelled(true);
                return;
            }
            ItemStack spacer = Utils.itemBuilder(Material.STAINED_GLASS_PANE, Short.valueOf((short)0), " ", null, Boolean.valueOf(false));
            ItemStack cespacer = Utils.itemBuilder(Material.INK_SACK, Short.valueOf((short)8), "&8&l???", null, Boolean.valueOf(false));
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchantsBook1Name))) {
                Inventory commonInv = Bukkit.createInventory(null, 18, Utils.color(Config.enchants1Title));
                int i;
                for (i = 0; i < 18; i++)
                    commonInv.setItem(i, spacer);
                for (i = 0; i < TieredEnchantments.getAllEnchantmentsInTier(1).size(); i++)
                    commonInv.setItem(i, EnchantmentBook.getEnchantmentListItem(TieredEnchantments.getAllEnchantmentsInTier(1).get(i)));
                commonInv.setItem(13, this.back);
                p.openInventory(commonInv);
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchantsBook2Name))) {
                Inventory uncommonInv = Bukkit.createInventory(null, 18, Utils.color(Config.enchants2Title));
                int i;
                for (i = 0; i < 18; i++)
                    uncommonInv.setItem(i, spacer);
                for (i = 0; i < TieredEnchantments.getAllEnchantmentsInTier(2).size(); i++)
                    uncommonInv.setItem(i, EnchantmentBook.getEnchantmentListItem(TieredEnchantments.getAllEnchantmentsInTier(2).get(i)));
                uncommonInv.setItem(13, this.back);
                p.openInventory(uncommonInv);
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchantsBook3Name))) {
                Inventory rareInv = Bukkit.createInventory(null, 27, Utils.color(Config.enchants3Title));
                int i;
                for (i = 0; i < 27; i++)
                    rareInv.setItem(i, spacer);
                for (i = 0; i < TieredEnchantments.getAllEnchantmentsInTier(3).size(); i++)
                    rareInv.setItem(i, EnchantmentBook.getEnchantmentListItem(TieredEnchantments.getAllEnchantmentsInTier(3).get(i)));
                rareInv.setItem(22, this.back);
                p.openInventory(rareInv);
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchantsBook4Name))) {
                Inventory ultimateInv = Bukkit.createInventory(null, 27, Utils.color(Config.enchants4Title));
                int i;
                for (i = 0; i < 27; i++)
                    ultimateInv.setItem(i, spacer);
                for (i = 0; i < TieredEnchantments.getAllEnchantmentsInTier(4).size(); i++)
                    ultimateInv.setItem(i, EnchantmentBook.getEnchantmentListItem(TieredEnchantments.getAllEnchantmentsInTier(4).get(i)));
                ultimateInv.setItem(22, this.back);
                p.openInventory(ultimateInv);
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchantsBook5Name))) {
                Inventory legendaryInv = Bukkit.createInventory(null, 27, Utils.color(Config.enchants5Title));
                int i;
                for (i = 0; i < 27; i++)
                    legendaryInv.setItem(i, spacer);
                for (i = 0; i < TieredEnchantments.getAllEnchantmentsInTier(5).size(); i++)
                    legendaryInv.setItem(i, EnchantmentBook.getEnchantmentListItem(TieredEnchantments.getAllEnchantmentsInTier(5).get(i)));
                legendaryInv.setItem(22, this.back);
                p.openInventory(legendaryInv);
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color(Config.enchantsBook6Name))) {
                Inventory mythicInv = Bukkit.createInventory(null, 18, Utils.color(Config.enchants6Title));
                int i;
                for (i = 0; i < 18; i++)
                    mythicInv.setItem(i, spacer);
                for (i = 0; i < TieredEnchantments.getAllEnchantmentsInTier(6).size(); i++)
                    mythicInv.setItem(i, EnchantmentBook.getEnchantmentListItem(TieredEnchantments.getAllEnchantmentsInTier(6).get(i)));
                mythicInv.setItem(13, this.back);
                p.openInventory(mythicInv);
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            return;
        }
        if (p.getOpenInventory().getTitle().equals(Utils.color(Config.enchants1Title))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (!e.getCurrentItem().hasItemMeta()) {
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color("&7&c&lBack &7")))
                    EnchantsCommand.openEnchantsInv(p);
            e.setCancelled(true);
        }
        if (p.getOpenInventory().getTitle().equals(Utils.color(Config.enchants2Title))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (!e.getCurrentItem().hasItemMeta()) {
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color("&7&c&lBack &7")))
                    EnchantsCommand.openEnchantsInv(p);
            e.setCancelled(true);
        }
        if (p.getOpenInventory().getTitle().equals(Utils.color(Config.enchants3Title))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (!e.getCurrentItem().hasItemMeta()) {
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color("&7&c&lBack &7")))
                    EnchantsCommand.openEnchantsInv(p);
            e.setCancelled(true);
        }
        if (p.getOpenInventory().getTitle().equals(Utils.color(Config.enchants4Title))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (!e.getCurrentItem().hasItemMeta()) {
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color("&7&c&lBack &7")))
                    EnchantsCommand.openEnchantsInv(p);
            e.setCancelled(true);
        }
        if (p.getOpenInventory().getTitle().equals(Utils.color(Config.enchants5Title))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (!e.getCurrentItem().hasItemMeta()) {
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color("&7&c&lBack &7")))
                    EnchantsCommand.openEnchantsInv(p);
            e.setCancelled(true);
        }
        if (p.getOpenInventory().getTitle().equals(Utils.color(Config.enchants6Title))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (!e.getCurrentItem().hasItemMeta()) {
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color("&7&c&lBack &7")))
                    EnchantsCommand.openEnchantsInv(p);
            e.setCancelled(true);
        }
        if (p.getOpenInventory().getTitle().contains(Utils.color("&8OneShot Enchants"))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (!e.getCurrentItem().hasItemMeta()) {
                e.setCancelled(true);
                return;
            }
            if (p.isOp() &&
                    e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {
                CustomEnchantment ce = EnchantmentBook.getEnchantment(e.getCurrentItem());
                if (Utils.getAvaliableInvSlots(p) > 0) {
                    p.getInventory().addItem(EnchantmentBook.getEnchantmentBook(ce, ce.getMaxLevel(), 100, 0));
                } else {
                    Bukkit.getWorld(p.getName()).dropItem(p.getLocation(), EnchantmentBook.getEnchantmentBook(ce, ce.getMaxLevel(), 100, 0));
                    p.sendMessage(Utils.color("&c&l(!) &cYour inventory is full so the enchantment was dropped on the ground."));
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnchantAdminClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equals(Utils.color(Config.enchantAdminTitle))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (!e.getCurrentItem().hasItemMeta());
            if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
                if (Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName()).contains("ALL COMMON ENCHANTMENTS")) {
                    int count = 0;
                    for (CustomEnchantment customEnchantment : TieredEnchantments.getAllEnchantmentsInTier(1)) {
                        if (Utils.getAvaliableInvSlots(p) > 0) {
                            p.getInventory().addItem(EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        } else {
                            Bukkit.getWorld(p.getWorld().getName()).dropItem(p.getLocation(), EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        }
                        count++;
                    }
                    p.sendMessage(Utils.color("&a&l(!) &7You have been given all &f" + count + " Common &7 enchantments."));
                    e.setCancelled(true);
                    return;
                }
                if (Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName()).contains("ALL UNCOMMON ENCHANTMENTS")) {
                    int count = 0;
                    for (CustomEnchantment customEnchantment : TieredEnchantments.getAllEnchantmentsInTier(2)) {
                        if (Utils.getAvaliableInvSlots(p) > 0) {
                            p.getInventory().addItem(EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        } else {
                            Bukkit.getWorld(p.getWorld().getName()).dropItem(p.getLocation(), EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        }
                        count++;
                    }
                    p.sendMessage(Utils.color("&a&l(!) &7You have been given all &a" + count + " Uncommon &7enchantments."));
                    e.setCancelled(true);
                    return;
                }
                if (Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName()).contains("ALL RARE ENCHANTMENTS")) {
                    int count = 0;
                    for (CustomEnchantment customEnchantment : TieredEnchantments.getAllEnchantmentsInTier(3)) {
                        if (Utils.getAvaliableInvSlots(p) > 0) {
                            p.getInventory().addItem(EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        } else {
                            Bukkit.getWorld(p.getWorld().getName()).dropItem(p.getLocation(), EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        }
                        count++;
                    }
                    p.sendMessage(Utils.color("&a&l(!) &7You have been given all &b" + count + " Rare &7enchantments."));
                    e.setCancelled(true);
                    return;
                }
                if (Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName()).contains("ALL ULTIMATE ENCHANTMENTS")) {
                    int count = 0;
                    for (CustomEnchantment customEnchantment : TieredEnchantments.getAllEnchantmentsInTier(4)) {
                        if (Utils.getAvaliableInvSlots(p) > 0) {
                            p.getInventory().addItem(EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        } else {
                            Bukkit.getWorld(p.getWorld().getName()).dropItem(p.getLocation(), EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        }
                        count++;
                    }
                    p.sendMessage(Utils.color("&a&l(!) &7You have been given all &e" + count + " Ultimate &7enchantments."));
                    e.setCancelled(true);
                    return;
                }
                if (Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName()).contains("ALL LEGENDARY ENCHANTMENTS")) {
                    int count = 0;
                    for (CustomEnchantment customEnchantment : TieredEnchantments.getAllEnchantmentsInTier(5)) {
                        if (Utils.getAvaliableInvSlots(p) > 0) {
                            p.getInventory().addItem(EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        } else {
                            Bukkit.getWorld(p.getWorld().getName()).dropItem(p.getLocation(), EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        }
                        count++;
                    }
                    p.sendMessage(Utils.color("&a&l(!) &7You have been given all &6" + count + " Legendary &7enchantments."));
                    e.setCancelled(true);
                    return;
                }
                if (Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName()).contains("ALL MYTHIC ENCHANTMENTS")) {
                    int count = 0;
                    for (CustomEnchantment customEnchantment : TieredEnchantments.getAllEnchantmentsInTier(6)) {
                        if (Utils.getAvaliableInvSlots(p) > 0) {
                            p.getInventory().addItem(EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        } else {
                            Bukkit.getWorld(p.getWorld().getName()).dropItem(p.getLocation(), EnchantmentBook.getEnchantmentBook(customEnchantment, customEnchantment.getMaxLevel(), 100, 0));
                        }
                        count++;
                    }
                    p.sendMessage(Utils.color("&a&l(!) &7You have been given all &c" + count + " Mythic &7enchantments."));
                    e.setCancelled(true);
                    return;
                }
            }
            if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                e.setCancelled(true);
                return;
            }
            p.getInventory().addItem(e.getCurrentItem());
            e.setCancelled(true);
        }
    }
}
