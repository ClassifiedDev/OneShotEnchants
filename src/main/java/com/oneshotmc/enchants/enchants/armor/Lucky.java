package com.oneshotmc.enchants.enchants.armor;

import java.util.Random;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ArmorUtil;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Lucky extends CustomEnchantment {
    public Lucky() {
        super("Lucky", GeneralUtils.armor, 3);
        this.max = 10;
        this.base = 25.0D;
        this.interval = 10.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player && (new Random()).nextInt(400) <= level) {
            Player p = (Player)player;
            double dmg = e.getDamage();
            dmg *= ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0D) {
                e.setCancelled(true);
                e.setDamage(0.0D);
                //ParticleEffect.CLOUD.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.3F, 30, p.getLocation(), 100.0D);
            }
        }
    }
}
