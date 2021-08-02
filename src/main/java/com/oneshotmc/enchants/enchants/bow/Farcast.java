package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class Farcast extends CustomEnchantment {
    public Farcast() {
        super("Farcast", new Material[] { Material.BOW }, 3);
        this.max = 5;
        this.base = 17.0D;
        this.interval = 4.0D;
    }

    public void applyDefenseEffect(LivingEntity entityHit, LivingEntity attacker, int level, EntityDamageEvent e) {
        float procChance = 0.1F + (float)(0.025D * level / Math.max(0.25D, entityHit.getHealth() / entityHit.getMaxHealth()));
        if (Math.random() <= procChance && attacker != null && !GeneralUtils.canEffectEntity((Entity)entityHit)) {
            //ParticleEffect.EXPLOSION_LARGE.display(0.0F, 0.0F, 0.0F, 1.0F, 3, attacker.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
            GeneralUtils.pushAwayEntity(entityHit, (Entity)attacker, 2.3D);
        }
    }

    private void pushAwayEntity(LivingEntity center, Entity entity, double speed) {
        Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
