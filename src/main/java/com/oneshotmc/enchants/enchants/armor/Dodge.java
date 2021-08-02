package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Dodge extends CustomEnchantment {
    public Dodge() {
        super("Dodge", GeneralUtils.armor, 2);
        this.max = 3;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (e instanceof org.bukkit.event.entity.EntityDamageByEntityEvent) {
            double chance = 0.025D * level;
            boolean sneaking = ((Player)player).isSneaking();
            if (sneaking)
                chance += 0.15D;
            if (Math.random() < chance) {
                e.setCancelled(true);
                e.setDamage(0.0D);
                ((Player)player).sendMessage(Utils.color("&e&lDODGE "));
                //ParticleEffect.CLOUD.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.2F, 10, player.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 0.75F);
            }
        }
    }
}
