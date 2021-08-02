package com.oneshotmc.enchants.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageCapListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void damageCap(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof org.bukkit.entity.Player && event.getEntity() instanceof org.bukkit.entity.Player &&
                event.getDamage() > 30.0D)
            event.setDamage(14.0D);
    }
}
