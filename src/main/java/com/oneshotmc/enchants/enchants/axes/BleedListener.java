package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.OneShotEnchants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

final class BleedListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.getEntity().removeMetadata("ce_bleeding", OneShotEnchants.getInstance());
    }
}
