package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchants.armor.CreeperArmor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

final class CreeperArmorListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && CreeperArmor.creeperArmor.containsKey(((Player)e.getEntity()).getName())) {
            final Player player = (Player)e.getEntity();
            int level = ((Integer)CreeperArmor.creeperArmor.get(player.getName())).intValue();
            if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                e.setCancelled(true);
                e.setDamage(0.0D);
                if (level >= 2)
                    (new BukkitRunnable() {
                        public void run() {
                            player.setVelocity((new Vector()).zero());
                        }
                    }).runTaskLater(OneShotEnchants.getInstance(), 1L);
                if (level == 3)
                    GeneralUtils.healPlayer((LivingEntity)player, Math.min(6.0D, e.getDamage() * 0.1D));
            }
        }
    }
}
