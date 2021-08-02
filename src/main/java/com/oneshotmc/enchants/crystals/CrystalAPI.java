package com.oneshotmc.enchants.crystals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.oneshotmc.enchants.crystals.listeners.PlayerListener;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CrystalAPI {
    public static List<String> crystalToggle = new ArrayList<>();

    public static boolean toggleCrystalMode(Player p) {
        if (crystalToggle.contains(p.getName())) {
            crystalToggle.remove(p.getName());
        } else {
            crystalToggle.add(p.getName());
        }
        return crystalToggle.contains(p.getName());
    }

    public static boolean hasCrystalMode(Player p) {
        return crystalToggle.contains(p.getName());
    }

    public static boolean isMythicCrystal(ItemStack is) {
        return (is != null && is.getType() == Material.DIAMOND && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is
                .getItemMeta().hasLore() &&
                ChatColor.stripColor(is.getItemMeta().getDisplayName()).contains("Mythic Crystal [") && is
                .getItemMeta().getLore().size() > 1);
    }

    public static void removeCyrstalsFromGems(Player p, int amount) {
        PlayerSubtractCrystalsEvent crystalsEvent = new PlayerSubtractCrystalsEvent(p, amount);
        int to_remove = amount;
        for (ListIterator<ItemStack> listIterator = p.getInventory().iterator(); listIterator.hasNext(); ) {
            ItemStack is = listIterator.next();
            if (to_remove <= 0)
                return;
            if (is == null)
                continue;
            if (is.getType() == Material.AIR)
                continue;
            if (!isMythicCrystal(is))
                continue;
            int crystals = getCrystals(is);
            if (to_remove < crystals) {
                crystals -= to_remove;
                setCrystals(is, crystals);
                return;
            }
            p.getInventory().removeItem(new ItemStack[] { is });
            to_remove -= crystals;
        }
        p.updateInventory();
    }

    public static boolean hasCrystalAmount(Player p, int amount) {
        return (getAllCrystals(p) >= amount);
    }

    public static int getAllCrystals(Player p) {
        int crystals = 0;
        Map<Long, List<ItemStack>> dupedGems = new HashMap<>();
        for (int slot = 0; slot < p.getInventory().getSize(); slot++) {
            ItemStack is2 = p.getInventory().getItem(slot);
            if (is2 != null && is2
                    .getType() != Material.AIR &&
                    isMythicCrystal(is2)) {
                p.getInventory().setItem(slot, is2);
                p.updateInventory();
                crystals += getCrystals(is2);
            }
        }
        return crystals;
    }

    public static void setCrystals(ItemStack is, int amount) {
        if (isMythicCrystal(is))
            is.setItemMeta(createCrystal(amount).clone().getItemMeta());
    }

    public static int getCrystals(ItemStack is) {
        if (is == null || is.getType() != Material.DIAMOND || !is.hasItemMeta() || !is.getItemMeta().hasDisplayName() ||
                !is.getItemMeta().hasLore())
            return -1;
        String name = is.getItemMeta().getDisplayName();
        if (!name.startsWith(Utils.color("&3&lMythic Crystal [")))
            return 0;
        name = ChatColor.stripColor(is.getItemMeta().getDisplayName());
        return Integer.parseInt(name.split("\\[")[1].split("\\]")[0]);
    }

    public static ItemStack buildTracker(Tracker tracker) {
        return Utils.itemBuilder(Material.PAPER, Short.valueOf((short)0), 1, tracker.getName() + " Crystal Tracker", new String[] { "&7Apply to weapons to start tracking",

                String.valueOf(tracker.getName()) + " Crystals &7collected from " + (tracker.trackMobKills() ? "mob" : "player") + " kills.", "", tracker

                .getColor() + "&lCollects: &7" + (tracker.trackMobKills() ? "1 crystal/100 mob kills" : (String.valueOf(tracker.getRank() * 5) + " crystals/player kill")), "", "&c&lWARNING: &7Previously tracked crystals", "&7will be removed on apply." }, false);
    }

    public static void attempCrystalHarvest(ItemStack is, Player killer, Player dead) {
        if (is == null || !ItemUtils.isTrackable(is) || is.getType() == Material.BOW)
            return;
        Tracker tracker = Tracker.getTrackerType(is);
        if (tracker == null)
            return;
        if (!PlayerListener.canPlayerKillPlayer(killer, dead)) {
            Long time = PlayerListener.getPlayerExpireTime(killer, dead);
            if (time != null) {
                killer.sendMessage(Utils.color("&c&l(!) &cYou cannot collect " + dead.getName() + "'s Crystal for another " + TextUtils.getFormattedCooldown(time.longValue())));
                return;
            }
        }
        if (!PlayerListener.logPlayerKill(killer, dead))
            return;
        int kills = ItemUtils.getTrackedKills(is);
        if (kills == -1)
            kills = 0;
        killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.4F);
        ItemUtils.setTrackedKills(is, tracker, ++kills);
    }

    public static ItemStack createCrystal(int amount) {
        ItemStack is = Utils.itemBuilder(Material.DIAMOND, Short.valueOf((short)0), 1, "&3&lMythic Crystal [" + getColor(amount) + amount + "&3&l]", new String[] {
                "", "&3&l&3Click this item to toggle &3&nMythic Mode", "&7While in Mythic Mode your ACTIVE Mythic tier", "&7enchantment will activate and drain crystals", "&7for as long as this mode is enabled.", "", "&3&l&7Use &3&n/splitcrystals&r &7with this item", "&7to split crystals off of it.", "", "&3&l&7Stack other Mythic Crystals on top of this",
                "&7one to combine their crystal counts." }, false);
        return is;
    }

    private static String getColor(int amount) {
        if (amount >= 5000)
            return "&4&l";
        if (amount >= 1000)
            return "&6&l";
        if (amount >= 500)
            return "&b";
        if (amount >= 100)
            return "&a";
        if (amount >= 0)
            return "&f";
        return "";
    }

    static {
        crystalToggle = new ArrayList<>();
    }
}
