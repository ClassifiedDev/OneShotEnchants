package com.oneshotmc.enchants.crystals;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public enum Tracker {
    COMMON(ChatColor.WHITE, String.valueOf(ChatColor.BOLD.toString()) + "Common", 1),
    UNCOMMON(ChatColor.GREEN, String.valueOf(ChatColor.BOLD.toString()) + "Uncommon", 2),
    RARE(ChatColor.AQUA, String.valueOf(ChatColor.BOLD.toString()) + "Rare", 3),
    ULTIMATE(ChatColor.YELLOW, String.valueOf(ChatColor.BOLD.toString()) + "Ultimate", 4),
    LEGENDARY(ChatColor.GOLD, String.valueOf(ChatColor.BOLD.toString()) + "Legendary", 5),
    MYTHIC(ChatColor.RED, String.valueOf(ChatColor.BOLD.toString()) + "Mythic", 8),
    MONSTER(ChatColor.DARK_AQUA, String.valueOf(ChatColor.BOLD.toString()) + "Monster", 0, true);

    ChatColor color;

    boolean trackMobKills;

    int rank;

    String name;

    public static String LORE_SUFFIX;

    Tracker(ChatColor color, String name, int rank) {
        this.trackMobKills = false;
        this.color = color;
        this.name = name;
        this.rank = rank;
    }

    Tracker(ChatColor color, String name, int rank, boolean mobTracker) {
        this.trackMobKills = mobTracker;
    }

    public int getRank() {
        return this.rank;
    }

    public String getName() {
        return getColor() + this.name;
    }

    public boolean trackMobKills() {
        return this.trackMobKills;
    }

    static {
        LORE_SUFFIX = ":  ";
    }

    public static Tracker getTrackerType(ItemStack is) {
        if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
            List<String> lore = is.getItemMeta().getLore();
            for (String s : lore) {
                if (s.contains(LORE_SUFFIX)) {
                    byte b = 0;
                    Tracker[] arrayOfTracker;
                    for (int i = (arrayOfTracker = values()).length; b < i; ) {
                        Tracker tracker = arrayOfTracker[b];
                        if (s.startsWith(tracker.getColor() + ChatColor.BOLD.toString() + (
                                tracker.trackMobKills() ? "Monster " : "") + "Crystals Harvested" + tracker.getColor() + LORE_SUFFIX))
                            return tracker;
                        b = (byte)(b + 1);
                    }
                }
            }
        }
        return null;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public boolean isTrackMobKills() {
        return this.trackMobKills;
    }
}
