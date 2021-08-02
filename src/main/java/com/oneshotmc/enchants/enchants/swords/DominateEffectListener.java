package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.utils.MinecraftServerUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;

final class DominateEffectListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageByEntiy(EntityDamageByEntityEvent e) {
        if (e.getDamager().hasMetadata(Dominate.dominateEffectLevel_metadata)) {
            long expireTick = ((MetadataValue)e.getDamager().getMetadata(Dominate.dominateEffectExpire_metadata).get(0)).asLong();
            if (MinecraftServerUtils.getCurrentServerTick() < expireTick) {
                int dominateLevel = ((MetadataValue)e.getDamager().getMetadata(Dominate.dominateEffectLevel_metadata).get(0)).asInt();
                double mod = 1.0D - (dominateLevel * 5 / 100);
                e.setDamage(e.getDamage() * mod);
            } else {
                e.getDamager().removeMetadata(Dominate.dominateEffectExpire_metadata, OneShotEnchants.getInstance());
                e.getDamager().removeMetadata(Dominate.dominateEffectLevel_metadata, OneShotEnchants.getInstance());
            }
        }
    }
}
