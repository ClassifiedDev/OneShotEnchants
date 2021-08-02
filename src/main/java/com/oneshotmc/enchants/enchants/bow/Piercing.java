package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;

public class Piercing extends CustomEnchantment {
    public Piercing() {
        super("Piercing", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double armor_percent_ignored = level * 2.5D;
        if (e.isCancelled() || !(playerHit instanceof Player))
            return;
        if (playerHit.hasMetadata("lastArrowDamageEvent")) {
            long minDelayMilliseconds = (level * 200);
            long last = ((MetadataValue)playerHit.getMetadata("lastArrowDamageEvent").get(0)).asLong();
            if (System.currentTimeMillis() - last <= minDelayMilliseconds)
                return;
        }
        double extra_dmg = armor_percent_ignored / 100.0D * e.getDamage();
        playerHit.damage(extra_dmg);
        ((Player)player).playSound(player.getLocation(), Sound.ARROW_HIT, 1.2F, 0.6F);
        //ParticleEffect.CRIT.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, playerHit.getLocation(), 100.0D);
    }
}
