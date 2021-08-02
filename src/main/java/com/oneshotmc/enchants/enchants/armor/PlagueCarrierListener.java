package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

final class PlagueCarrierListener implements Listener {
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getEntity() != null && e.getEntity().hasMetadata("plagueCarrier")) {
            e.setCancelled(true);
            e.setYield(0.0F);
            int level = ((MetadataValue)e.getEntity().getMetadata("plagueCarrier").get(0)).asInt();
            Location loc = e.getLocation();
            loc.getWorld().playSound(loc, Sound.EXPLODE, 1.0F, 1.0F);
            //ParticleEffect.EXPLOSION_LARGE.display(0.0F, 0.0F, 0.0F, 1.5F, 3, loc, 100.0D);
            for (Entity ent : e.getEntity().getNearbyEntities(5.0D, 4.0D, 5.0D)) {
                if (ent instanceof LivingEntity) {
                    LivingEntity le = (LivingEntity)ent;
                    le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, level * 2 * 20, (level == 8) ? 2 : ((level >= 4) ? 1 : 0)));
                    if (level >= 3)
                        le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, level * 2 * 20, (level == 8) ? 2 : ((level >= 4) ? 1 : 0)));
                    if (level >= 6)
                        le.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, level * 2 * 20, (level == 8) ? 2 : ((level >= 4) ? 1 : 0)));
                    if (level != 8)
                        continue;
                    le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 2 * 20, (level == 8) ? 2 : ((level >= 4) ? 1 : 0)));
                }
            }
            e.getEntity().remove();
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("plagueCarrier")) {
            e.setDroppedExp(0);
            e.getDrops().clear();
            e.getEntity().remove();
        }
    }
}
