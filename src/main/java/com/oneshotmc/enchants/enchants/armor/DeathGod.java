package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathGod extends CustomEnchantment {
    public DeathGod() {
        super("Death God", GeneralUtils.helmets, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double random = Math.random();
        double procChance = 0.015D * level;
        if (random < procChance && player.getHealth() - e.getFinalDamage() <= (4 + level)) {
            e.setCancelled(true);
            e.setDamage(0.0D);
            GeneralUtils.healPlayer(player, (5 + level));
            ((Player)player).sendMessage(Utils.color("&6&lDEATH GOD [&6" + (5 + level) + "HP&6&l] "));
        }
    }
}
