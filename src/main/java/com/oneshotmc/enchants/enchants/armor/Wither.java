package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Wither extends CustomEnchantment {
    public Wither() {
        super("Wither", GeneralUtils.armor, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double random = Math.random();
        double chance = 0.015D * level;
        if (random < chance && attacker != null)
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * level, level - 1));
    }
}
