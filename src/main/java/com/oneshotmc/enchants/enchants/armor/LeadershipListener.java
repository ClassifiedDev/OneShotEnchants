package com.oneshotmc.enchants.enchants.armor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;

final class LeadershipListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Leadership.Leaderships.remove(e.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getDamager().hasMetadata("leadershipLevel")) {
            int level = ((MetadataValue)((Player)e.getDamager()).getMetadata("leadershipLevel").get(0)).asInt();
            double incre = Math.min(1.75D, 1.0D + level * 0.1D);
            double damage = e.getDamage();
            e.setDamage(e.getDamage() * incre);
        }
    }
}
