package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Sniper extends CustomEnchantment {
    public Sniper() {
        super("Sniper", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 10.0D;
        this.interval = 8.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)
            return;
        Projectile proj = (Projectile)event.getDamager();
        if (!(proj.getShooter() instanceof Player))
            return;
        LivingEntity livingEntity = target;
        double y = proj.getLocation().getY();
        double victimay = livingEntity.getLocation().getY();
        double level = enchantLevel;
        boolean headshot = (y - victimay > 1.9D);
        if (headshot && Math.random() < 0.35D + level * 0.075D) {
            if (GeneralUtils.isEffectedByRage(target))
                return;
            double multiplier = level * 0.42D + 1.0D;
            event.setDamage(event.getDamage() * multiplier);
            playBleedEffect(target.getEyeLocation(), enchantLevel);
            if (target instanceof Player) {
                ((Player)target).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "*** HEADSHOT [+" + multiplier + "x DMG] ***");
                ((Player)target).playSound(target.getLocation(), Sound.HURT_FLESH, 2.0F, 0.3F);
            }
        }
    }

    private void playBleedEffect(Location loc, int bleedStack) {
        //for (int i = Math.max(1, bleedStack / 2); i > 0; i--) {}
            //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new //ParticleEffect.BlockData(Material.REDSTONE_BLOCK, (byte)0), 0.3F, 1.3F, 0.3F, 1.0F, 50, loc.getBlock().getLocation(), 75.0D);
    }
}
