package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poisoned extends CustomEnchantment {
    public Poisoned() {
        super("Poisoned", GeneralUtils.armor, 2);
        this.max = 4;
        this.base = 14.0D;
        this.interval = 4.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (attacker == null)
            return;
        int time = (int)(Math.random() * level + 0.5D * level);
        int amp = (level > 2) ? 1 : 0;
        double random = Math.random();
        double chance = (level > 3) ? 0.3D : (0.1D * level);
        if (random < chance && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && attacker != null)
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time * 20, amp));
    }
}
