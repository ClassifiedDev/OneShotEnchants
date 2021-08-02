package com.oneshotmc.enchants.crystals.listeners;

import com.oneshotmc.enchants.crystals.ItemUtils;
import com.oneshotmc.enchants.crystals.Tracker;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null && e.getCursor() != null && e.getCurrentItem().getType() != Material.AIR &&
                ItemUtils.isTrackable(e.getCurrentItem()) && isKillTracker(e.getCursor())) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            Player p = (Player)e.getWhoClicked();
            ItemStack item = e.getCurrentItem();
            ItemStack cursor = e.getCursor();
            Tracker trying_to_add = getKillTrackerType(cursor);
            Tracker current = Tracker.getTrackerType(e.getCurrentItem());
            if (trying_to_add == null) {
                System.out.println("Null add item.");
                return;
            }
            if (current != null) {
                if (trying_to_add.equals(current)) {
                    p.setItemOnCursor(null);
                    if (p.getInventory().firstEmpty() == -1) {
                        p.getWorld().dropItem(p.getLocation(), cursor);
                    } else {
                        p.getInventory().addItem(new ItemStack[] { cursor });
                    }
                    p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You " + ChatColor.RED + ChatColor.UNDERLINE
                            .toString() + "cannot" + ChatColor.RED + " add the same type of tracker to this item.");
                    p.updateInventory();
                    p.closeInventory();
                    return;
                }
                if (trying_to_add.getRank() < current.getRank() || trying_to_add
                        .trackMobKills() != current.trackMobKills()) {
                    p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot overwrite a higher tier tracker.");
                    return;
                }
                ItemUtils.removeTracker(item, current);
            }
            if (cursor.getAmount() == 1) {
                p.setItemOnCursor(null);
            } else {
                cursor.setAmount(cursor.getAmount() - 1);
                p.setItemOnCursor(cursor);
            }
            ItemUtils.setTrackedKills(item, trying_to_add, 0);
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.3F);
            p.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GRAY + "You have applied a " + trying_to_add
                    .getName() + " Crystal Tracker" + ChatColor.GRAY + " to your " + ((item
                    .hasItemMeta() && item.getItemMeta().hasDisplayName()) ? item.getItemMeta().getDisplayName() :
                    StringUtils.capitaliseAllWords(item.getType().name().toLowerCase().replace("_", " "))) + "!");
            p.updateInventory();
        }
    }

    public boolean isKillTracker(ItemStack is) {
        return (is.getType() == Material.PAPER && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is
                .getItemMeta().hasLore() && is.getItemMeta().getDisplayName().contains("Crystal Tracker") && is
                .getItemMeta().getLore().size() > 0 && ((String)is
                .getItemMeta().getLore().get(0)).equals(ChatColor.GRAY + "Apply to weapons to start tracking"));
    }

    public Tracker getKillTrackerType(ItemStack is) {
        String name = is.getItemMeta().getDisplayName();
        byte b;
        int i;
        Tracker[] arrayOfTracker;
        for (i = (arrayOfTracker = Tracker.values()).length, b = 0; b < i; ) {
            Tracker t = arrayOfTracker[b];
            if (name.startsWith(t.getName()))
                return t;
            b = (byte)(b + 1);
        }
        return null;
    }
}
