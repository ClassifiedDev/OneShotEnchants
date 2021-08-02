package com.oneshotmc.enchants.listeners;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.EnchantmentBook;
import com.oneshotmc.enchants.enchantmentapi.RomanNumeral;
import com.oneshotmc.enchants.events.EnchantmentApplyEvent;
import com.oneshotmc.enchants.utils.Config;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class EnchantmentApplyListener implements Listener {
    int MAX_ENCHANTS_PER_ITEM = 10;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().getTitle().equals("container.crafting"))
            return;
        if (e.getCursor() != null && EnchantmentBook.isEnchantmentBook(e.getCursor())) {
            if (e.getRawSlot() < 9 && e.getRawSlot() >= 0) {
                Player pl = (Player)e.getWhoClicked();
                pl.sendMessage(Utils.color("&c&l(!) &cYou cannot apply enchantments to your armor while it is equipped."));
                return;
            }
            if (e.getCurrentItem() != null) {
                Player pl = (Player)e.getWhoClicked();
                CustomEnchantment ce = EnchantmentBook.getEnchantment(e.getCursor());
                if (ce == null)
                    return;
                if (!ce.canEnchantOnto(e.getCurrentItem()) || e.getCurrentItem().getMaxStackSize() != 1)
                    return;
                Material currentItemType = e.getCurrentItem().getType();
                ItemStack to_enchant = e.getCurrentItem();
                if (EnchantmentBook.getCustomEnchantmentCount(to_enchant) > EnchantmentBook.getMaxEnchantmentCountAllowed(pl) &&
                        !OneShotEnchants.itemHasEnchantment(to_enchant, ce.name())) {
                    if (!pl.isOp()) {
                        pl.sendMessage(Utils.color("&c&l(!) &cYou are not skilled enough to add anymore enchantments to this item."));
                        pl.sendMessage(Utils.color("&8&l&7Purchase a rank at &3&nshop.oneshotmc.com&r &7to increase your max custom enchants per item!"));
                        return;
                    }
                    pl.sendMessage(Utils.color("&c&l(!) &cThe enchantment limit on this item has been bypassed due to your admin status. "));
                }
                ItemStack ench_book = e.getCursor().clone();
                if (OneShotEnchants.itemHasEnchantment(to_enchant, ce.name())) {
                    CustomEnchantment toCheck = ce;
                    Map<CustomEnchantment, Integer> enchants = OneShotEnchants.getEnchantments(to_enchant);
                    Integer level = enchants.get(toCheck);
                    if (level == null)
                        level = enchants.get(toCheck);
                    if (level == null) {
                        pl.sendMessage(Utils.color("&c&l(!) &cUnable to apply enchantment to this item, please contact an admin if you think this is a mistake."));
                        return;
                    }
                    if (level.intValue() >= EnchantmentBook.getEnchantmentLevel(ench_book)) {
                        pl.sendMessage(Utils.color("&c&l(!) &cThat item already has " + toCheck.name() + " " + RomanNumeral.numeralOf(level.intValue()) + "!"));
                        return;
                    }
                }
                if (to_enchant.getAmount() > to_enchant.getMaxStackSize() && to_enchant.getAmount() > 1) {
                    pl.sendMessage(Utils.color("&c&l(!) &cYou cannot apply enchantments to stacked items!"));
                    return;
                }
                if (pl.hasPermission("oneshotenchants.bookkeep") && Math.random() <= 0.05D) {
                    pl.sendMessage(Utils.color("&a&l(!) &aDue to your rank status, this book will attempt to apply to your equipment but will not be consumed!"));
                } else {
                    pl.setItemOnCursor(null);
                }
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
                boolean success = EnchantmentBook.applyEnchantmentBook(pl, ench_book, e.getCurrentItem());
                if (success) {
                    pl.playSound(pl.getLocation(), Sound.LEVEL_UP, 1.0F, 0.75F);
                    pl.playEffect(EntityEffect.VILLAGER_HAPPY);
                    Location l = pl.getLocation();
                    pl.sendMessage(Utils.color("&8&l* &aEnchantment successful"));
                    ////ParticleEffect.SPELL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, l.add(0.0D, 1.0D, 0.0D), 100.0D);
                    ////ParticleEffect.CRIT_MAGIC.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, l.add(0.0D, 1.0D, 0.0D), 100.0D);
                    Bukkit.getPluginManager().callEvent((Event)new EnchantmentApplyEvent(pl, e.getCurrentItem(), EnchantmentApplyEvent.EnchantmentApplyOutcome.SUCCESS));
                } else {
                    pl.playSound(pl.getLocation(), Sound.LAVA_POP, 1.0F, 0.75F);
                    pl.playEffect(EntityEffect.VILLAGER_ANGRY);
                    Location l = pl.getLocation();
                    pl.sendMessage(Utils.color("&8&l* &cEnchantment failed"));
//                    try {
//                        //ParticleEffect.LAVA.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, l.add(0.0D, 1.0D, 0.0D), 100.0D);
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
                }
                pl.updateInventory();
                Bukkit.getPluginManager().callEvent((Event)new EnchantmentApplyEvent(pl, e.getCurrentItem(), EnchantmentApplyEvent.EnchantmentApplyOutcome.FAIL));
            }
        }
    }

    @EventHandler
    public void onProtectionApply(InventoryClickEvent e) {
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
            return;
        Player p = (Player)e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        ItemStack target = e.getCurrentItem();
        if (cursor.hasItemMeta() &&
                cursor.getItemMeta().hasDisplayName() && cursor.getItemMeta().hasLore() &&
                cursor.getItemMeta().getDisplayName().equals(Utils.color(Config.protectionScrollName)) && cursor.getType().equals(Config.protectionScrollMaterial) && (
                EnchantmentBook.isEquipment(target) || EnchantmentBook.isArmor(target))) {
            if (target.getItemMeta().hasLore()) {
                if (target.getItemMeta().getLore().contains(Utils.color(Config.protectedLore))) {
                    p.sendMessage(Utils.color("&c&l(!) &cThis item is already protected with a protection scroll."));
                    return;
                }
                ItemMeta itemMeta = target.getItemMeta();
                ArrayList<String> lore = new ArrayList<>();
                for (String s : itemMeta.getLore())
                    lore.add(s);
                lore.add(Utils.color(Config.protectedLore));
                itemMeta.setLore(lore);
                target.setItemMeta(itemMeta);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                ////ParticleEffect.SPELL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                if (cursor.getAmount() > 1) {
                    cursor.setAmount(cursor.getAmount() - 1);
                } else {
                    e.setCursor(new ItemStack(Material.AIR));
                }
                e.setCancelled(true);
                p.updateInventory();
                return;
            }
            ItemMeta tim = target.getItemMeta();
            ArrayList<String> tisLore = new ArrayList<>();
            tisLore.add(Utils.color(Config.protectedLore));
            tim.setLore(tisLore);
            target.setItemMeta(tim);
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            ////ParticleEffect.SPELL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
            if (cursor.getAmount() > 1) {
                cursor.setAmount(cursor.getAmount() - 1);
            } else {
                e.setCursor(new ItemStack(Material.AIR));
            }
            e.setCancelled(true);
            p.updateInventory();
            return;
        }
    }

    @EventHandler
    public void onDischargeApply(InventoryClickEvent e) {
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
            return;
        Player p = (Player)e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        ItemStack target = e.getCurrentItem();
        if (cursor.hasItemMeta() &&
                cursor.getItemMeta().hasDisplayName() && cursor.getItemMeta().hasLore() &&
                cursor.getItemMeta().getDisplayName().equals(Utils.color(Config.removalScrollName)) && cursor.getType().equals(Config.removalScrollMaterial) && (
                EnchantmentBook.isEquipment(target) || EnchantmentBook.isArmor(target)) &&
                target.getItemMeta().hasLore()) {
            if (!Utils.containsEnchants(target)) {
                p.sendMessage(Utils.color("&c&l(!) &cThis item does not contain any custom enchantments."));
                return;
            }
            String percentStr = Utils.removeColor(cursor.getItemMeta().getLore().get(2));
            percentStr = percentStr.replaceAll("into a ", "");
            percentStr = percentStr.replaceAll("% enchantment orb.", "");
            int percent = Integer.parseInt(percentStr);
            Multimap<CustomEnchantment, Integer> enchantmentsOnItem = EnchantmentListener.getValidEnchantments(target, p);
            Random generator = new Random();
            Object[] keys = enchantmentsOnItem.keys().toArray();
            CustomEnchantment randomValue = (CustomEnchantment)keys[generator.nextInt(keys.length)];
            randomValue.removeFromItem(target);
            p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomValue, ((Integer) Iterables.get(enchantmentsOnItem.get(randomValue), 0)).intValue(), percent, 100) });
            p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 1.0F);
            //ParticleEffect.CRIT_MAGIC.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
            if (cursor.getAmount() > 1) {
                cursor.setAmount(cursor.getAmount() - 1);
            } else {
                e.setCursor(new ItemStack(Material.AIR));
            }
            e.setCancelled(true);
            p.updateInventory();
        }
    }

    @EventHandler
    public void onOrganizationApply(InventoryClickEvent e) {
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
            return;
        Player p = (Player)e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        ItemStack target = e.getCurrentItem();
        if (cursor.hasItemMeta() &&
                cursor.getItemMeta().hasDisplayName() && cursor.getItemMeta().hasLore() &&
                cursor.getItemMeta().getDisplayName().equals(Utils.color(Config.organizationScrollName)) && cursor.getType().equals(Config.organizationScrollMaterial) && (
                EnchantmentBook.isEquipment(target) || EnchantmentBook.isArmor(target)) &&
                target.getItemMeta().hasLore()) {
            int loreCount = 0;
            ArrayList<String> lore = new ArrayList<>();
            ArrayList<String> organizedLore = new ArrayList<>();
            for (String s : target.getItemMeta().getLore())
                lore.add(s);
            for (String s : lore) {
                if (s.startsWith(Utils.color(Config.tier6BookColor)) && (
                        Utils.removeColor(s).endsWith("I") || Utils.removeColor(s).endsWith("V") || Utils.removeColor(s).endsWith("X")))
                    organizedLore.add(s);
            }
            for (String ss : lore) {
                if (ss.startsWith(Utils.color(Config.tier5BookColor)) && (
                        Utils.removeColor(ss).endsWith("I") || Utils.removeColor(ss).endsWith("V") || Utils.removeColor(ss).endsWith("X")))
                    organizedLore.add(ss);
            }
            for (String sss : lore) {
                if (sss.startsWith(Utils.color(Config.tier4BookColor)) && (
                        Utils.removeColor(sss).endsWith("I") || Utils.removeColor(sss).endsWith("V") || Utils.removeColor(sss).endsWith("X")))
                    organizedLore.add(sss);
            }
            for (String ssss : lore) {
                if (ssss.startsWith(Utils.color(Config.tier3BookColor)) && (
                        Utils.removeColor(ssss).endsWith("I") || Utils.removeColor(ssss).endsWith("V") || Utils.removeColor(ssss).endsWith("X")))
                    organizedLore.add(ssss);
            }
            for (String sssss : lore) {
                if (sssss.startsWith(Utils.color(Config.tier2BookColor)) && (
                        Utils.removeColor(sssss).endsWith("I") || Utils.removeColor(sssss).endsWith("V") || Utils.removeColor(sssss).endsWith("X")))
                    organizedLore.add(sssss);
            }
            for (String ssssss : lore) {
                if (ssssss.startsWith(Utils.color(Config.tier1BookColor)) && (
                        Utils.removeColor(ssssss).endsWith("I") || Utils.removeColor(ssssss).endsWith("V") || Utils.removeColor(ssssss).endsWith("X")))
                    organizedLore.add(ssssss);
            }
            for (String s2 : lore) {
                if (!Utils.removeColor(s2).endsWith("I") && !Utils.removeColor(s2).endsWith("V") && !Utils.removeColor(s2).endsWith("X"))
                    organizedLore.add(s2);
            }
            for (String lc : lore) {
                if (Utils.removeColor(lc).endsWith("I") || Utils.removeColor(lc).endsWith("V") || Utils.removeColor(lc).endsWith("X"))
                    loreCount++;
            }
            ItemMeta im = target.getItemMeta();
            if (im.hasDisplayName()) {
                if (!im.getDisplayName().contains(Utils.color("&9&l[")) || im.getDisplayName().contains(Utils.color("&r&9&l]")));
                im.setDisplayName(im.getDisplayName());
            } else {
                String itemName = target.getType().toString().toLowerCase();
                if (!itemName.contains("_")) {
                    itemName = Utils.color("&fBow");
                    im.setDisplayName(itemName);
                } else {
                    String[] itemNameSplit = itemName.split("_");
                    itemName = itemNameSplit[0] + itemNameSplit[1];
                    String itemName0 = ("" + itemNameSplit[0].charAt(0)).toUpperCase() + itemNameSplit[0].substring(1);
                    String itemName1 = ("" + itemNameSplit[1].charAt(0)).toUpperCase() + itemNameSplit[1].substring(1);
                    itemName = Utils.color("&f" + itemName0) + " " + Utils.color("&f" + itemName1);
                    im.setDisplayName(itemName);
                }
            }
            im.setLore(organizedLore);
            target.setItemMeta(im);
            lore.clear();
            organizedLore.clear();
            //ParticleEffect.SPELL_WITCH.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
            p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 1.0F);
            if (cursor.getAmount() > 1) {
                cursor.setAmount(cursor.getAmount() - 1);
            } else {
                e.setCursor(new ItemStack(Material.AIR));
            }
            e.setCancelled(true);
            p.updateInventory();
        }
    }

    @EventHandler
    public void onSpecialEnchantApply(InventoryClickEvent e) {
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
            return;
        Player p = (Player)e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        ItemStack target = e.getCurrentItem();
        if (cursor.hasItemMeta() &&
                cursor.getItemMeta().hasDisplayName() && cursor.getItemMeta().hasLore()) {
            if (cursor.getItemMeta().getDisplayName().equals(Utils.color("&d&l&nLooting IV Enchant Book")) && cursor.getType().equals(Material.ENCHANTED_BOOK) && (
                    target.getType().toString().contains("SWORD") || target
                            .getType().toString().contains("AXE"))) {
                ItemMeta im = target.getItemMeta();
                im.addEnchant(Enchantment.LOOT_BONUS_MOBS, 4, true);
                target.setItemMeta(im);
                //ParticleEffect.SPELL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                e.setCursor(new ItemStack(Material.AIR));
                e.setCancelled(true);
                p.updateInventory();
            }
            if (cursor.getItemMeta().getDisplayName().equals(Utils.color("&d&l&nLooting V Enchant Book")) && cursor.getType().equals(Material.ENCHANTED_BOOK) && (
                    target.getType().toString().contains("SWORD") || target
                            .getType().toString().contains("AXE"))) {
                ItemMeta im = target.getItemMeta();
                im.addEnchant(Enchantment.LOOT_BONUS_MOBS, 5, true);
                target.setItemMeta(im);
                //ParticleEffect.SPELL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                e.setCursor(new ItemStack(Material.AIR));
                e.setCancelled(true);
                p.updateInventory();
            }
            if (cursor.getItemMeta().getDisplayName().equals(Utils.color("&d&l&nDepth Strider I Enchant Book")) && cursor.getType().equals(Material.ENCHANTED_BOOK) &&
                    target.getType().toString().contains("BOOTS")) {
                ItemMeta im = target.getItemMeta();
                im.addEnchant(Enchantment.DEPTH_STRIDER, 1, true);
                target.setItemMeta(im);
                //ParticleEffect.SPELL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                e.setCursor(new ItemStack(Material.AIR));
                e.setCancelled(true);
                p.updateInventory();
            }
            if (cursor.getItemMeta().getDisplayName().equals(Utils.color("&d&l&nDepth Strider II Enchant Book")) && cursor.getType().equals(Material.ENCHANTED_BOOK) &&
                    target.getType().toString().contains("BOOTS")) {
                ItemMeta im = target.getItemMeta();
                im.addEnchant(Enchantment.DEPTH_STRIDER, 2, true);
                target.setItemMeta(im);
                //ParticleEffect.SPELL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                e.setCursor(new ItemStack(Material.AIR));
                e.setCancelled(true);
                p.updateInventory();
            }
            if (cursor.getItemMeta().getDisplayName().equals(Utils.color("&d&l&nDepth Strider III Enchant Book")) && cursor.getType().equals(Material.ENCHANTED_BOOK) &&
                    target.getType().toString().contains("BOOTS")) {
                ItemMeta im = target.getItemMeta();
                im.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
                target.setItemMeta(im);
                //ParticleEffect.SPELL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                e.setCursor(new ItemStack(Material.AIR));
                e.setCancelled(true);
                p.updateInventory();
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player pl = e.getPlayer();
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.hasItem() && EnchantmentBook.isEnchantmentBook(e.getItem())) {
            pl.sendMessage(" ");
            pl.sendMessage(Utils.color("&a&l(!) &aTo apply this enchantment to an item, simply drag n' drop the book onto the item you'd like to enchant in your inventory!"));
            pl.sendMessage(Utils.color("&8&l&7The &aSuccess Rate &7is the chance of the book successfully being applied to your equipment. The &cDestroy Rate &7 is the percent chance of your piece of equipment being &nDESTROYED&7 if the enchantment fails to apply."));
            pl.sendMessage(" ");
            pl.updateInventory();
        }
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.hasItem() && EnchantmentBook.isWhitescroll(e.getItem())) {
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            pl.sendMessage(" ");
            pl.sendMessage(Utils.color("&a&l(!) &7To apply this Protection Scroll to an item, simply drag n' drop the scroll onto the item you'd like to protect in your inventory!"));
            pl.sendMessage(" ");
            pl.updateInventory();
        }
    }
}
