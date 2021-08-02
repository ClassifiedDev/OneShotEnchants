package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Devour extends CustomEnchantment {
    public Devour() {
        super("Devour", GeneralUtils.axe, 2);
        this.max = 4;
        this.base = 10.0D;
        this.interval = 8.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0D && user instanceof org.bukkit.entity.Player && Bleed.getBleedStack(target) > 0) {
            if (GeneralUtils.isEffectedByRage(target))
                return;
            double mod = Bleed.getBleedStack(target) * 0.015D + enchantLevel * 0.1D + 1.0D;
            event.setDamage(event.getDamage() * mod);
            target.setMetadata("effectedByDevour", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
            Location l = target.getLocation();
            //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new ParticleEffect.BlockData(Material.MYCEL, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 30, l, 75.0D);
        }
    }
}
