package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Greatsword extends CustomEnchantment {
    public Greatsword() {
        super("Greatsword", GeneralUtils.swords, 7);
        this.max = 5;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        boolean enemyHasBow = playerHit.getEquipment().getItemInHand().getType().name().equals("BOW");
        if (!enemyHasBow)
            return;
        double mod = 1.0D + 0.05D * level;
        e.setDamage(e.getDamage() * mod);
    }
}
