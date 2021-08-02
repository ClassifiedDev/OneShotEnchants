package com.oneshotmc.enchants.enchants.bow;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.MetadataValue;

class VirusListener implements Listener {
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && (e.getCause() == EntityDamageEvent.DamageCause.WITHER || e.getCause() == EntityDamageEvent.DamageCause.POISON)) {
            Player p = (Player)e.getEntity();
            if (p.hasMetadata("virusEnchantmentExpire") && System.currentTimeMillis() < ((MetadataValue)p.getMetadata("virusEnchantmentExpire").get(0)).asLong()) {
                int level = ((MetadataValue)p.getMetadata("virusEnchantmentLevel").get(0)).asInt();
                e.setDamage(e.getDamage() * (level + 1));
            }
        }
    }
}
