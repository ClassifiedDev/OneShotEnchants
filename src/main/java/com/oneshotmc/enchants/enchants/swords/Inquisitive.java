package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Inquisitive extends CustomEnchantment {
    public Inquisitive() {
        super("Inquisitive", GeneralUtils.swords, 7);
        this.max = 4;
        this.base = 25.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new InquisitiveEvents(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (target instanceof org.bukkit.entity.Player)
            return;
        double random = Math.random();
        double chance = 0.075D * enchantLevel;
        if (random < chance)
            target.setMetadata("inquisitive_enchant", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(enchantLevel)));
    }

    private class InquisitiveEvents implements Listener {
        private InquisitiveEvents() {}

        @EventHandler(priority = EventPriority.HIGH)
        public void onEntityDeath(EntityDeathEvent e) {
            if (e.getEntity().hasMetadata("inquisitive_enchant")) {
                double multiple = 1.0D + 0.25D * ((MetadataValue)e.getEntity().getMetadata("inquisitive_enchant").get(0)).asDouble();
                e.setDroppedExp((int)(e.getDroppedExp() * multiple));
            }
        }
    }
}
