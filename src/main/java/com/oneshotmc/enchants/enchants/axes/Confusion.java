package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Confusion extends CustomEnchantment {
    public Confusion() {
        super("Confusion", GeneralUtils.axe, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof org.bukkit.entity.Player))
            return;
        double random = Math.random();
        double chance = 0.075D * level;
        int time = 2 * level + 4;
        int amp = level;
        if (random < chance && playerHit instanceof org.bukkit.entity.Player)
            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time * 20, amp));
    }
}
