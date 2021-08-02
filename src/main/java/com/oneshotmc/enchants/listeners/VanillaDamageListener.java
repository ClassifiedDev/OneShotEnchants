package com.oneshotmc.enchants.listeners;

import com.oneshotmc.enchants.OneShotEnchants;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class VanillaDamageListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent e) {
        if ((e.getEntity() instanceof org.bukkit.entity.Player && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) || e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            e.getEntity().setMetadata("lastDamageEventRawDamage", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Double.valueOf(e.getDamage())));
            e.getEntity().setMetadata("lastDamageEventFinalDamage", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Double.valueOf(e.getFinalDamage())));
        }
    }

    public static double getLastRawDamage(Entity e) {
        return e.hasMetadata("lastDamageEventRawDamage") ? ((MetadataValue)e.getMetadata("lastDamageEventRawDamage").get(0)).asDouble() : 0.0D;
    }

    public static double getLastFinalDamage(Entity e) {
        return e.hasMetadata("lastDamageEventFinalDamage") ? ((MetadataValue)e.getMetadata("lastDamageEventFinalDamage").get(0)).asDouble() : 0.0D;
    }
}
