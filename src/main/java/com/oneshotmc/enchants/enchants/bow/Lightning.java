package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Lightning extends CustomEnchantment {
    public Lightning() {
        super("Lightning", new Material[] { Material.BOW }, 2);
        this.max = 3;
        this.base = 20.0D;
        this.interval = 0.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random() * 1.0D;
        double chance = 0.1D * level;
        if (random < chance) {
            if (!(playerHit instanceof org.bukkit.entity.Player))
                return;
            playerHit.getLocation().getWorld().strikeLightningEffect(playerHit.getLocation());
            playerHit.damage(5.0D, (Entity)player);
        }
    }
}
