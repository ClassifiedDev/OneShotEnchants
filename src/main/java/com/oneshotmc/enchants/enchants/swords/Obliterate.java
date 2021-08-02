package com.oneshotmc.enchants.enchants.swords;

import java.util.Random;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class Obliterate extends CustomEnchantment {
    public Obliterate() {
        super("Obliterate", GeneralUtils.weapons, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 6.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double random = Math.random();
        double chance = 0.1D * enchantLevel;
        if (target.getWorld().getEnvironment() == World.Environment.THE_END)
            return;
        if (random < chance) {
            double d_enchantLevel = enchantLevel;
            //ParticleEffect.EXPLOSION_LARGE.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 1.0F, 3, target.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
            GeneralUtils.pushAwayEntity(user, (Entity)target, 1.8D + d_enchantLevel * 0.5D);
        }
    }

    private void pushAwayEntity(LivingEntity center, Entity entity, double speed) {
        Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
