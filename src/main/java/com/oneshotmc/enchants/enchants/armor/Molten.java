package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class Molten extends CustomEnchantment {
    public Molten() {
        super("Molten", GeneralUtils.armor, 2);
        this.max = 4;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double random = Math.random() * 1.0D;
        double chance = 0.1D * level;
        int time = 20 * level;
        if (random < chance && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && attacker != null)
            attacker.setFireTicks(time);
    }
}
