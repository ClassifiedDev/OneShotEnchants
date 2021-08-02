package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class ThunderingBlow extends CustomEnchantment {
    public ThunderingBlow() {
        super("Thundering Blow", GeneralUtils.swords, 2);
        this.max = 3;
        this.base = 25.0D;
        this.interval = 0.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random();
        double chance = (level == 3) ? 0.05D : (0.0175D * level);
        if (random < chance) {
            if (playerHit.hasMetadata("lastThunderingBlowProc") && System.currentTimeMillis() - ((MetadataValue)playerHit.getMetadata("lastThunderingBlowProc").get(0)).asLong() <= 2500L)
                return;
            playerHit.setMetadata("lastThunderingBlowProc", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
            playerHit.getWorld().strikeLightningEffect(playerHit.getLocation());
            playerHit.damage(5.0D);
        }
    }
}
