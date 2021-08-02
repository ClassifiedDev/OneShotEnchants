package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Valor extends CustomEnchantment {
    public Valor() {
        super("Valor", GeneralUtils.armor, 2);
        this.max = 5;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(player instanceof Player))
            return;
        double damage_negation = level * 0.015D + 0.075D;
        double hp_threshold = (level * 2 + 6);
        if (player.getHealth() <= hp_threshold && (((Player)player).getItemInHand().getType() == Material.IRON_SWORD || ((Player)player).getItemInHand().getType() == Material.DIAMOND_SWORD) && e.getDamage() * (1.0D - damage_negation) > 1.0D) {
            e.setDamage(e.getDamage() * (1.0D - damage_negation));
            Location l = player.getLocation();
            //ParticleEffect.BLOCK_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.BlockData(Material.GOLD_BLOCK, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 50, l.getBlock().getLocation(), 75.0D);
        }
    }
}
