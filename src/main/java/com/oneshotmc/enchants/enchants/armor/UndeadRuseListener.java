package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.metadata.MetadataValue;

final class UndeadRuseListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager().hasMetadata("undeadruseSummoner")) {
            Player p = (Player)e.getEntity();
            String summoner = ((MetadataValue)e.getDamager().getMetadata("undeadruseSummoner").get(0)).asString();
            Player pSummoner = Bukkit.getPlayer(summoner);
            if (pSummoner != null && (summoner.equalsIgnoreCase(p.getName()) || GeneralUtils.isAlly(p, pSummoner))) {
                e.setCancelled(true);
                e.setDamage(0.0D);
            }
        }
        if (e.getEntity().hasMetadata("undeadruseSummoner") && e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            String summoner = ((MetadataValue)e.getEntity().getMetadata("undeadruseSummoner").get(0)).asString();
            Player pSummoner = Bukkit.getPlayer(summoner);
            if (pSummoner != null && (summoner.equalsIgnoreCase(p.getName()) || GeneralUtils.isAlly(p, pSummoner))) {
                e.setCancelled(true);
                e.setDamage(0.0D);
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player && e.getEntity().hasMetadata("undeadruseSummoner")) {
            Player p = (Player)e.getTarget();
            String summoner = ((MetadataValue)e.getEntity().getMetadata("undeadruseSummoner").get(0)).asString();
            Player pSummoner = Bukkit.getPlayer(summoner);
            if (pSummoner != null && (summoner.equalsIgnoreCase(p.getName()) || GeneralUtils.isAlly(p, pSummoner) || p.hasMetadata("spectator")))
                e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("undeadruseSummoner")) {
            e.getDrops().clear();
            e.getEntity().remove();
        }
    }
}
