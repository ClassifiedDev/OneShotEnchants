package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class Enlighted extends CustomEnchantment {
    public Enlighted() {
        super("Enlighted", GeneralUtils.armor, 2);
        this.max = 1;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyDefenseEffect(final LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double random = Math.random();
        if (level > 3)
            level = 3;
        double chance = 0.1D * level;
        if (random < chance)
            Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                public void run() {
                    GeneralUtils.healPlayer(player, 1.0D);
                }
            }, 1L);
    }
}
