package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class ArrowBreak extends CustomEnchantment {
    public ArrowBreak() {
        super("Arrow Break", GeneralUtils.axe, 2);
        this.max = 6;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent)e;
            if (edbee.getDamager() instanceof org.bukkit.entity.Projectile && Math.random() < (0.1D + 0.04D * level) / 2.0D) {
                //ParticleEffect.CRIT.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, edbee.getDamager().getLocation(), 100.0D);
                ((Player)player).playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7F, 0.2F);
                e.setCancelled(true);
                e.setDamage(0.0D);
                edbee.getDamager().remove();
            }
        }
    }
}
