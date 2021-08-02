package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Lifesteal extends CustomEnchantment {
    public Lifesteal() {
        super("Lifesteal", GeneralUtils.swords, 2);
        this.max = 5;
        this.base = 10.0D;
        this.interval = 8.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double random = Math.random();
        double chance = (enchantLevel > 3) ? 0.3D : (0.1D * enchantLevel);
        int health = (int)user.getHealth() + enchantLevel;
        if (health > user.getMaxHealth())
            health = (int)user.getMaxHealth();
        if (random < chance)
            GeneralUtils.setPlayerHealth(user, health);
    }
}
