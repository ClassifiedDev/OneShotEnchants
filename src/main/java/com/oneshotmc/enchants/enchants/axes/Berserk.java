package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Berserk extends CustomEnchantment {
    public Berserk() {
        super("Berserk", GeneralUtils.axe, 7);
        this.max = 5;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player))
            return;
        Player playerAttacker = (Player)player;
        double random = Math.random();
        double chance = 0.03D * level;
        double duration = (level * 2);
        if (random < chance)
            playerAttacker.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int)duration * 20, 1));
    }
}
