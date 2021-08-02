package com.oneshotmc.enchants.enchants.swords;

import java.util.Random;

import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.MetadataValue;

final class DivineImmolationListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("divineFire") && e.getCause() == EntityDamageEvent.DamageCause.WITHER && e.getEntity() instanceof LivingEntity) {
            int enchantLevel = ((MetadataValue)e.getEntity().getMetadata("divineFireLevel").get(0)).asInt();
            if (System.currentTimeMillis() - ((MetadataValue)e.getEntity().getMetadata("divineFire").get(0)).asLong() < ((4 + enchantLevel) * 1000)) {
                e.setCancelled(true);
                LivingEntity le = (LivingEntity)e.getEntity();
                le.damage(Math.min(5, 2 + enchantLevel));
                //ParticleEffect.FLAME.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.15F, 20, le.getEyeLocation(), 100.0D);
                //ParticleEffect.LAVA.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.5F, 20, le.getEyeLocation(), 100.0D);
                if (le instanceof Player) {
                    ((Player)le).sendMessage(Utils.color("&c&lDIVINE IMMOLATION "));
                    ((Player)le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.6F, 0.8F);
                }
            }
        }
    }
}
