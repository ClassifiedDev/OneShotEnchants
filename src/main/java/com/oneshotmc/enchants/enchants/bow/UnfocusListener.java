package com.oneshotmc.enchants.enchants.bow;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;

class UnfocusListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Projectile) {
            Projectile pj = (Projectile)e.getDamager();
            if (pj.getShooter() instanceof Player) {
                Player p = (Player)pj.getShooter();
                if (p.hasMetadata("unfocusEnchantmentExpire") && System.currentTimeMillis() < ((MetadataValue)p.getMetadata("unfocusEnchantmentExpire").get(0)).asLong()) {
                    e.setDamage(e.getDamage() / 2.0D);
                    p.sendMessage(ChatColor.DARK_GREEN + "** UNFOCUSED [50% BOW DMG] **");
                }
            }
        }
    }
}
