package com.oneshotmc.enchants.enchants.swords;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

final class SilenceListener implements Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getDamage() > 0.0D && e.getEntity() instanceof org.bukkit.entity.Player && e.getEntity().hasMetadata("noDefenseProcs"))
            e.setDamage(e.getDamage() * 0.5D);
    }
}
