package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Insanity extends CustomEnchantment {
    public Insanity() {
        super("Insanity", GeneralUtils.axe, 7);
        this.max = 8;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        boolean enemyHasSword = playerHit.getEquipment().getItemInHand().getType().name().contains("_SWORD");
        if (!enemyHasSword)
            return;
        double mod = 1.0D + 0.02D * level;
        e.setDamage(e.getDamage() * mod);
    }
}
