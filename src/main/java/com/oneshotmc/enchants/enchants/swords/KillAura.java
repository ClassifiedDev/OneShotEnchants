package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class KillAura extends CustomEnchantment {
    public KillAura() {
        super("Kill Aura", GeneralUtils.swords, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 6.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double random = Math.random();
        double chance = 0.05D * enchantLevel;
        if (target.hasMetadata("monsterAmount") && random < chance) {
            int mobsToKill = (enchantLevel == 5 && Math.random() < 0.05D) ? 3 : 2;
            target.setMetadata("killAura", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(mobsToKill)));
            //ParticleEffect.EXPLOSION_LARGE.display(0.0F, 0.0F, 0.0F, 0.5F, 3, target.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
        }
    }
}
