package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Vampire extends CustomEnchantment {
    public Vampire() {
        super("Vampire", GeneralUtils.swords, 2);
        this.max = 3;
        this.base = 25.0D;
        this.interval = 0.0D;
    }

    public void applyEffect(final LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double chance = 0.05D * level;
        final double healthGain = level;
        if (Math.random() < chance) {
            Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                public void run() {
                    if (player.isDead() || !player.isValid())
                        return;
                    if (GeneralUtils.healPlayer(player, healthGain))
                        ((Player)player).playSound(player.getLocation(), Sound.DRINK, 0.75F, 2.0F);
                }
            }, 60L);
            ((Player)player).playSound(player.getLocation(), Sound.DRINK, 0.75F, 2.0F);
        }
    }
}
