package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.DurabilityUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Blacksmith extends CustomEnchantment {
    public Blacksmith() {
        super("Blacksmith", GeneralUtils.axe, 2);
        this.max = 5;
        this.base = 17.0D;
        this.interval = 6.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random();
        double chance = 0.075D * level;
        if (random < chance) {
            DurabilityUtil.healMostDamagedArmorPeice((Player)player, (short)((level == 5) ? 2 : 1));
            if (level >= 3)
                DurabilityUtil.healMostDamagedArmorPeice((Player)player, (short)1);
            e.setDamage(e.getDamage() * 0.5D);
        }
    }
}
