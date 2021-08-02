package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class Stormcaller extends CustomEnchantment {
    public Stormcaller() {
        super("Stormcaller", GeneralUtils.armor, 2);
        this.max = 4;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double random = Math.random();
        double chance = 0.045D * level;
        if (random < chance && attacker != null) {
            if (attacker.equals(player))
                return;
            player.getWorld().strikeLightning(attacker.getLocation());
            player.damage(10.0D);
            GeneralUtils.pushAwayEntity(player, (Entity)attacker, 1.5D);
        }
    }

    private void pushAwayEntity(LivingEntity center, Entity entity, double speed) {
        Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
