package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class SpiritLink extends CustomEnchantment {
    public SpiritLink() {
        super("Spirit Link", GeneralUtils.chestplates, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double chance = level * 0.1D;
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && Math.random() < chance) {
            Player playerHurt = (Player)player;
            double healAmount = Math.min(4.0D, e.getDamage() / (this.max - level - 1) / 4.0D);
            double radius = (level * 2);
            for (Entity ent : player.getNearbyEntities(radius, radius, radius)) {
                if (ent instanceof Player) {
                    Player playerNear = (Player)ent;
                    if (!isAlly(playerHurt, playerNear))
                        continue;
                    GeneralUtils.healPlayer((LivingEntity)playerNear, healAmount);
                    //ParticleEffect.HEART.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 3, playerNear.getEyeLocation().add(0.0D, 0.35D, 0.0D), 100.0D);
                }
            }
            //ParticleEffect.HEART.display((float)Math.random() / 2.0F, (float)Math.random() / 2.0F, (float)Math.random() / 2.0F, 2.0F, 3, player.getLocation().add(0.0D, 0.5D, 0.0D), 100.0D);
            player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.35F);
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }
}
