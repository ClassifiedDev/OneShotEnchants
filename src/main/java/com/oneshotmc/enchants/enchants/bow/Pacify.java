package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Pacify extends CustomEnchantment {
    public Pacify() {
        super("Pacify", new Material[] { Material.BOW }, 3);
        this.max = 4;
        this.base = 17.0D;
        this.interval = 4.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (Math.random() < 0.5D + level * 0.125D && playerHit instanceof Player) {
            Player pHit = (Player)playerHit;
            pHit.setMetadata("noRageUntil", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis() + (level * 750))));
        }
    }
}
