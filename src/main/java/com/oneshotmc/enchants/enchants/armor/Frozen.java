package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Frozen extends CustomEnchantment {
    public Frozen() {
        super("Frozen", GeneralUtils.armor, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double random = Math.random();
        double chance = 0.04D * level;
        int time = (int)(Math.random() * level) + 1 * level;
        int amp = (level > 2) ? 1 : 0;
        if (random < chance && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && attacker != null) {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time * 20, amp));
            Location l = player.getLocation();
            //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new //ParticleEffect.BlockData(Material.ICE, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 50, l.getBlock().getLocation(), 75.0D);
        }
    }
}
