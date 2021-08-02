package com.oneshotmc.enchants.crystals;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    private static String getCrystalString(Tracker tracker) {
        return tracker.trackMobKills() ? ("Monster " + CRYSTAL_STRING) : CRYSTAL_STRING;
    }

    public static boolean isTrackable(ItemStack is) {
        String name = is.getType().name().toLowerCase();
        return (name.endsWith("_sword") || name.endsWith("bow") || name.endsWith("_axe"));
    }

    public static int getTrackedKills(ItemStack is) {
        if (is.hasItemMeta() && is.getItemMeta().hasLore())
            for (String s : is.getItemMeta().getLore()) {
                if (s.contains("Crystals Harvested") && s.contains(Tracker.LORE_SUFFIX))
                    return Integer.parseInt(ChatColor.stripColor(s.split(Tracker.LORE_SUFFIX)[1]));
            }
        return -1;
    }

    public static void removeTracker(ItemStack is, Tracker tracker) {
        if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
            ItemMeta im = is.getItemMeta();
            List<String> lore = im.getLore();
            for (String s : lore) {
                if (s.contains(getCrystalString(tracker)) && s.contains(Tracker.LORE_SUFFIX)) {
                    lore.remove(s);
                    break;
                }
            }
            im.setLore(lore);
            is.setItemMeta(im);
        }
    }

    public static boolean containsString(List<String> lore, String goal) {
        for (String s : lore) {
            if (s.contains(goal))
                return true;
        }
        return false;
    }

    public static void setTrackedKills(ItemStack is, Tracker tracker, int amount) {
        if (is.getItemMeta() == null)
            return;
        ItemMeta im = is.getItemMeta();
        boolean alreadySet = false;
        if (is.getItemMeta().hasLore() && im.getLore().size() > 0 &&
                containsString(im.getLore(), getCrystalString(tracker)))
            alreadySet = true;
        List<String> lore = alreadySet ? (im.hasLore() ? im.getLore() : new ArrayList<>()) : (im.hasLore() ? im.getLore() : new ArrayList<>());
        if (alreadySet) {
            for (int i = 0; i < lore.size(); i++) {
                String s = lore.get(i);
                if (s.contains(getCrystalString(tracker)) && s.contains(Tracker.LORE_SUFFIX))
                    if (i != lore.size() - 1) {
                        lore.remove(i);
                        lore.add(tracker.getColor() + ChatColor.BOLD.toString() + getCrystalString(tracker) + tracker
                                .getColor() + Tracker.LORE_SUFFIX + tracker.getColor() + ChatColor.BOLD + amount);
                    } else {
                        lore.set(i, tracker
                                .getColor() + ChatColor.BOLD.toString() + getCrystalString(tracker) + tracker
                                .getColor() + Tracker.LORE_SUFFIX + tracker.getColor() + ChatColor.BOLD + amount);
                    }
            }
        } else {
            lore.add(tracker.getColor() + ChatColor.BOLD.toString() + getCrystalString(tracker) + tracker.getColor() + Tracker.LORE_SUFFIX + tracker
                    .getColor() + ChatColor.BOLD + amount);
        }
        im.setLore(lore);
        is.setItemMeta(im);
    }

    private static String CRYSTAL_STRING = "Crystals Harvested";
}
