package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.listeners.VanillaDamageListener;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class DoubleStrike extends CustomEnchantment {
    public DoubleStrike() {
        super("Double Strike", GeneralUtils.swords, 2);
        this.max = 3;
        this.base = 10.0D;
        this.interval = 10.0D;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random();
        double chance = 0.02D * level;
        if (random < chance) {
            double dmg = VanillaDamageListener.getLastFinalDamage((Entity)playerHit);
            if (dmg == 0.0D)
                dmg = e.getDamage();
            final double fdmg = dmg;
            Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                public void run() {
                    if (!playerHit.isDead() && playerHit.getHealth() > 0.0D) {
                        playerHit.setMetadata("doubleStriked", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
                        playerHit.damage(fdmg, (Entity)player);
                    }
                }
            }, 2L);
            //ParticleEffect.CRIT_MAGIC.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 25, playerHit.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
        }
    }
}
