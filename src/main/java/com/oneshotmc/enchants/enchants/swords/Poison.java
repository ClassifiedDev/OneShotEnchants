package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poison extends CustomEnchantment {
    public Poison() {
        super("Poison", GeneralUtils.swords, 5);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 6.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        int time = (int)(Math.random() * enchantLevel * 20.0D);
        double random = Math.random();
        double chance = 0.1D * enchantLevel;
        if (random < chance)
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, enchantLevel - 1));
    }
}
