package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Insomnia extends CustomEnchantment {
    public Insomnia() {
        super("Insomnia", GeneralUtils.swords, 2);
        this.max = 7;
        this.base = 15.0D;
        this.interval = 15.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random();
        double chance = 0.025D * level;
        int time = (level > 1) ? 6 : 4;
        if (random < chance) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Math.max(time * 10, 40), Math.min(level - 1, 2)));
            if (level >= 6)
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Math.max(time * 10, 40), 1));
            double multi = 1.0D + level * 0.05D;
            e.setDamage(e.getDamage() * multi);
        }
    }
}
