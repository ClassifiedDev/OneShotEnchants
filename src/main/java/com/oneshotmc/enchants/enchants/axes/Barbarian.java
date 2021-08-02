package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Barbarian extends CustomEnchantment {
    public Barbarian() {
        super("Barbarian", GeneralUtils.axe, 7);
        this.max = 4;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        boolean enemyHasAxe = playerHit.getEquipment().getItemInHand().getType().name().contains("_AXE");
        if (!enemyHasAxe)
            return;
        double mod = 1.0D + 0.05D * level;
        e.setDamage(e.getDamage() * mod);
    }
}
