package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

final class CowificationListener implements Listener {
    private boolean factions_enabled;

    public CowificationListener() {
        this.factions_enabled = false;
        this.factions_enabled = Bukkit.getPluginManager().isPluginEnabled("Factions");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("cowification")) {
            e.setCancelled(true);
            e.setDamage(0.0D);
            if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent)e).getDamager() instanceof Player) {
                ((Player)((EntityDamageByEntityEvent)e).getDamager()).playSound(e.getEntity().getLocation(), Sound.COW_HURT, 1.0F, 0.7F);
                e.getEntity().remove();
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity().hasMetadata(Cowification.cowificationEncahntmentMetadata)) {
            Location loc = e.getEntity().getLocation();
            if (e.getEntity().getPassenger() != null)
                e.getEntity().getPassenger().remove();
            if (WorldGuardUtils.isPvPDisabled(loc))
                return;
            loc.getWorld().playSound(loc, Sound.COW_HURT, 1.0F, 0.85F);
            int radius = ((MetadataValue)e.getEntity().getMetadata(Cowification.cowificationEncahntmentMetadata).get(0)).asInt(), level = radius;
            String enchantment_owner = e.getEntity().hasMetadata(Cowification.cowificationEncahntmentOwnerMetadata) ? ((MetadataValue)e.getEntity().getMetadata(Cowification.cowificationEncahntmentOwnerMetadata).get(0)).asString() : null;
            for (Entity ent : e.getEntity().getNearbyEntities(radius, radius, radius)) {
                if (ent instanceof LivingEntity) {
                    if (enchantment_owner != null && ent instanceof Player) {
                        if (((Player)ent).getName().equals(enchantment_owner))
                            continue;
                        if (isAlly((Player)ent, Bukkit.getPlayer(enchantment_owner)))
                            continue;
                    }
                    if (!(ent instanceof LivingEntity))
                        continue;
                    ((LivingEntity)ent).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 25 * level, 1));
                }
            }
            ParticleEffect.EXPLOSION_LARGE.display(0.0F, 0.0F, 0.0F, 0.5F, 5, loc, 100.0D);
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }
}
