package com.oneshotmc.enchants.enchants.swords;

import java.util.Random;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchants.axes.Corrupt;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Inversion extends CustomEnchantment {
    public Inversion() {
        super("Inversion", GeneralUtils.swords, 2);
        this.max = 4;
        this.base = 17.0D;
        this.interval = 4.0D;
    }

    public void applyDefenseEffect(LivingEntity entityHit, LivingEntity attacker, int level, EntityDamageEvent e) {
        float procChance = (float)(0.05D * level);
        if (Math.random() <= procChance) {
            Player playerHit = (Player)entityHit;
            double heal = ((new Random()).nextInt(3) + 1);
            if (heal >= 1.0D) {
                if (Corrupt.isCorrupted((LivingEntity)playerHit) && Math.random() < (Corrupt.getCorruptLevel((LivingEntity)playerHit) * 0.2F)) {
                    playerHit.damage(heal);
                    playerHit.sendMessage(Utils.color("&5&lCORRUPTED [&c" + heal + " &5&lDMG] "));
                    return;
                }
                GeneralUtils.healPlayer((LivingEntity)playerHit, heal);
                e.setDamage(0.0D);
                e.setCancelled(true);
                //ParticleEffect.SPELL.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 25, playerHit.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                playerHit.playSound(playerHit.getLocation(), Sound.PISTON_EXTEND, 0.8F, 2.0F);
            }
        }
    }
}
