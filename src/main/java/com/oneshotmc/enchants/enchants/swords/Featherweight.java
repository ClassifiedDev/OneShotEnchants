package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Featherweight extends CustomEnchantment {
    public Featherweight() {
        super("Featherweight", GeneralUtils.swords, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 10.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (Math.random() <= level * 0.25D && !player.hasPotionEffect(PotionEffectType.FAST_DIGGING))
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, level * 20, level - 1), true);
    }
}
