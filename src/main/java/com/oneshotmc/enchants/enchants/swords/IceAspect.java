package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IceAspect extends CustomEnchantment {
    public IceAspect() {
        super("Ice Aspect", GeneralUtils.swords, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (playerHit.hasPotionEffect(PotionEffectType.SLOW))
            return;
        double random = Math.random();
        double chance = 0.075D * level;
        int time = 2 * level;
        if (random < chance && playerHit instanceof org.bukkit.entity.Player) {
            Location l = playerHit.getLocation();
            if (playerHit.hasMetadata("immune_freeze"))
                return;
            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time * 20, 5));
            //ParticleEffect.BLOCK_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.BlockData(Material.ICE, (byte)14), 0.4F, 1.0F, 0.4F, 1.0F, 50, l.getBlock().getLocation(), 150.0D);
        }
    }
}
