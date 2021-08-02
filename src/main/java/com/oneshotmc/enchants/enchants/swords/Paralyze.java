package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Paralyze extends CustomEnchantment {
    public Paralyze() {
        super("Paralyze", GeneralUtils.swords, 2);
        this.max = 4;
        this.base = 25.0D;
        this.interval = 0.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random();
        double chance = (level == 3) ? 0.05D : (0.0175D * level);
        if (random < chance) {
            playerHit.getLocation().getWorld().strikeLightningEffect(player.getLocation());
            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, (level > 2) ? 1 : 0));
            if (level == 4)
                playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, (level > 2) ? 1 : 0));
            playerHit.damage((1 + level));
        }
    }
}
