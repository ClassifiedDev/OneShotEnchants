package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Execute extends CustomEnchantment {
    public Execute() {
        super("Execute", GeneralUtils.swords, 2);
        this.max = 7;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {
        if (!(target instanceof org.bukkit.entity.Player))
            return;
        if (target.getHealth() > 8.0D)
            return;
        if (GeneralUtils.isEffectedByRage(target))
            return;
        double random = Math.random();
        double chance = 0.04D * level;
        double mod = 1.5D;
        if (random < chance) {
            e.setDamage(e.getDamage() * 1.5D);
            Location l = target.getLocation();
            //ParticleEffect.REDSTONE.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 25, l.getBlock().getLocation(), 100.0D);
        }
    }
}
