package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Voodoo extends CustomEnchantment {
    public Voodoo() {
        super("Voodoo", GeneralUtils.armor, 2);
        this.max = 6;
        this.base = 17.0D;
        this.interval = 6.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        int time = (int)(1.5D * level);
        double random = Math.random();
        double chance = 0.04D * level;
        if (random < chance && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && attacker != null) {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, time * 20 + 25, (level >= 4) ? 1 : 0));
            //ParticleEffect.SPELL_MOB.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.05F, 15, attacker.getEyeLocation().add(0.0D, 0.5D, 0.0D), 100.0D);
        }
    }
}
