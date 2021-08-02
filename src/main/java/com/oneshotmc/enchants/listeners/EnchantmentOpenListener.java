package com.oneshotmc.enchants.listeners;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.EnchantmentBook;
import com.oneshotmc.enchants.enchantmentapi.RomanNumeral;
import com.oneshotmc.enchants.enchantmentapi.TieredEnchantments;
import com.oneshotmc.enchants.utils.Config;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class EnchantmentOpenListener implements Listener {
    public static void launchFirework(Player p, Color c) {
        Firework fw = (Firework)p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c).withFade(c).with(FireworkEffect.Type.STAR).trail(true).build();
        fwm.addEffect(effect);
        fwm.setPower(0);
        fw.setFireworkMeta(fwm);
    }

    @EventHandler
    public void onMysteryEnchantmentOpen(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack hand = p.getItemInHand();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook1Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier1BookName))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook1Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier1BookName)))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.WHITE);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(1).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(1).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&f&l(!) &7You open the mystery enchantment and receive: &f" + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.WHITE);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(1).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(1).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&f&l(!) &7You open the mystery enchantment and receive: &f" + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook2Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier2BookName))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook2Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier2BookName)))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.GREEN);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(2).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(2).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&a&l(!) &7You open the mystery enchantment and receive: " + TieredEnchantments.getTierColor(randomEnchant) + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.GREEN);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(2).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(2).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&a&l(!) &7You open the mystery enchantment and receive: " + TieredEnchantments.getTierColor(randomEnchant) + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook3Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier3BookName))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook3Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier3BookName)))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.AQUA);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(3).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(3).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&b&l(!) &7You open the mystery enchantment and receive: " + TieredEnchantments.getTierColor(randomEnchant) + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.AQUA);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(3).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(3).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&b&l(!) &7You open the mystery enchantment and receive: " + TieredEnchantments.getTierColor(randomEnchant) + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook4Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier4BookName))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook4Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier4BookName)))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.YELLOW);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(4).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(4).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&e&l(!) &7You open the mystery enchantment and receive: " + TieredEnchantments.getTierColor(randomEnchant) + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &You can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.YELLOW);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(4).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(4).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&e&l(!) &7You open the mystery enchantment and receive: " + TieredEnchantments.getTierColor(randomEnchant) + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook5Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier5BookName))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook5Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier5BookName)))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.ORANGE);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(5).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(5).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&6&l(!) &7You open the mystery enchantment and receive: " + TieredEnchantments.getTierColor(randomEnchant) + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.ORANGE);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(5).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(5).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&6&l(!) &7You open the mystery enchantment and receive: " + TieredEnchantments.getTierColor(randomEnchant) + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook6Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier6BookName))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Config.enchantmentBook6Material) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.tier6BookName)))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.RED);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(6).get(Utils.randomInt(0, TieredEnchantments.getAllEnchantmentsInTier(6).size() - 1));
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&c&l(!) &7You open the mystery enchantment and receive: " + TieredEnchantments.getTierColor(randomEnchant) + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 2.0F);
                    launchFirework(p, Color.RED);
                    CustomEnchantment randomEnchant = TieredEnchantments.getAllEnchantmentsInTier(6).get(0);
                    int level = Utils.randomInt(1, randomEnchant.getMaxLevel());
                    p.getInventory().addItem(new ItemStack[] { EnchantmentBook.getEnchantmentBook(randomEnchant, level) });
                    p.sendMessage(Utils.color("&c&l(!) &7You open the mystery enchantment and receive: " + TieredEnchantments.getTierColor(randomEnchant) + randomEnchant.name() + " " + RomanNumeral.numeralOf(level)));
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this enchantment because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
    }
}
