package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Shackle extends CustomEnchantment {
    public Shackle() {
        super("Shackle", GeneralUtils.weapons, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 6.0D;
    }

    public void applyEffect(LivingEntity user, final LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (target.hasMetadata("fromMobSpawner")) {
            if ((target.getType() == EntityType.BLAZE && enchantLevel < 2) || (target.getType() == EntityType.MAGMA_CUBE && enchantLevel < 3))
                return;
            (new BukkitRunnable() {
                public void run() {
                    if (target.getHealth() > 0.0D && target.isValid() && !target.isDead())
                        target.setVelocity((new Vector()).zero());
                }
            }).runTaskLater(OneShotEnchants.getInstance(), 1L);
        }
    }
}
