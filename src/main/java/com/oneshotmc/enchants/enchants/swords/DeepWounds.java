package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchants.axes.Bleed;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class DeepWounds extends CustomEnchantment {
    public DeepWounds() {
        super("Deep Wounds", GeneralUtils.swords, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 15.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        playerHit.setMetadata("deepWounds", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis() + (level * 1500))));
        double mod = Bleed.getBleedStack(playerHit) * 0.005D + level * 0.01D + 1.0D;
        e.setDamage(e.getDamage() * mod);
    }
}
