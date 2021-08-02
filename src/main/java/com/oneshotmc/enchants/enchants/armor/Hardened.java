package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.DurabilityUtil;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Hardened extends CustomEnchantment {
    public Hardened() {
        super("Hardened", GeneralUtils.armor, 2);
        this.max = 3;
        this.base = 17.0D;
        this.interval = 6.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double random = Math.random();
        double chance = 0.2D * level;
        if (random < chance) {
            DurabilityUtil.healMostDamagedArmorPeice((Player)player, (short)1);
            Location l = player.getLocation();
            //ParticleEffect.SPELL.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.8F, 5, l, 100.0D);
        }
    }
}
