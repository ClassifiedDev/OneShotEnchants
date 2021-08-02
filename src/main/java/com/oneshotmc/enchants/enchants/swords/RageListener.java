package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

final class RageListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Player && (e.getEntity().hasMetadata("pvp_rageHitCombo") || e.getEntity().hasMetadata("pve_rageHitCombo"))) {
            e.getEntity().removeMetadata("pvp_rageHitCombo", OneShotEnchants.getInstance());
            e.getEntity().removeMetadata("pve_rageHitCombo", OneShotEnchants.getInstance());
        }
    }
}
