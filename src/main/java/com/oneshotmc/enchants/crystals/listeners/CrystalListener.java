package com.oneshotmc.enchants.crystals.listeners;

import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CrystalListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryclick(InventoryClickEvent e) {
        if (e.getCursor() != null && e.getCurrentItem() != null && CrystalAPI.isMythicCrystal(e.getCursor()) && CrystalAPI.isMythicCrystal(e.getCurrentItem()) && e.getCursor().getType() == Material.DIAMOND) {
            Player p = (Player)e.getWhoClicked();
            if (e.getCurrentItem().getAmount() > 1) {
                e.setCancelled(true);
                p.sendMessage(Utils.color("&c&l(!) &cYou cannot combine stacked Crystal Gems."));
                return;
            }
            int cursorSouls = CrystalAPI.getCrystals(e.getCursor());
            int currentSouls = CrystalAPI.getCrystals(e.getCurrentItem());
            if (cursorSouls == -1 || currentSouls == -1)
                return;
            int newSouls = currentSouls + cursorSouls;
            e.setCancelled(true);
            if (e.getCursor().getAmount() > 1) {
                e.getCursor().setAmount(e.getCursor().getAmount() - 1);
            } else {
                p.setItemOnCursor(null);
            }
            CrystalAPI.setCrystals(e.getCurrentItem(), newSouls);
            p.playSound(p.getLocation(), Sound.ANVIL_USE, 1.0F, 1.4F);
            p.updateInventory();
        }
    }
}
