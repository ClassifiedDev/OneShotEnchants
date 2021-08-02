package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BleedLegacy extends CustomEnchantment {
    public BleedLegacy() {
        super("Bleed", GeneralUtils.axe, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 3.0D;
        Bukkit.getPluginManager().registerEvents(new BleedListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player))
            return;
        double random = Math.random();
        double chance = 0.075D * level;
        double chance2 = 0.025D * level;
        int time = 2 * level;
        double amp = (level / 2);
        if (amp < 0.5D)
            amp = 0.5D;
        if (random < chance && playerHit instanceof Player) {
            if (random < chance2)
                playerHit.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, time * 20, level - 1));
            if (!playerHit.hasMetadata("ce_bleeding"))
                (new BleedEvent((Player)playerHit, amp, time)).start();
        }
    }
}
