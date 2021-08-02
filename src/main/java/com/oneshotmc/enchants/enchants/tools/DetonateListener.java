package com.oneshotmc.enchants.enchants.tools;

import com.oneshotmc.enchants.OneShotEnchants;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

final class DetonateListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onTagBlockFace(PlayerInteractEvent event) {
        if (event.getPlayer() == null)
            return;
        if (event.getBlockFace() == null)
            return;
        if (event.getItem() == null || event.getMaterial() == Material.AIR)
            return;
        if (!OneShotEnchants.itemHasEnchantment(event.getItem(), "Detonate"))
            return;
        Detonate.updateSavedBlockFace(event.getPlayer(), event.getBlockFace());
    }
}
