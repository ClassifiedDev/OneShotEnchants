package com.oneshotmc.enchants.enchants.axes;

import java.text.DecimalFormat;

import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

final class HexListener implements Listener {
    private DecimalFormat df = new DecimalFormat("#.##");

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof LivingEntity))
            return;
        LivingEntity damager = (LivingEntity)e.getDamager();
        if (Hex.isHexed(damager) && e.getFinalDamage() > 0.0D) {
            double damage = Math.min(e.getFinalDamage(), (5 + Math.max(0, Hex.getHexLevel(damager) - 2)));
            damager.damage(damage);
            if (damager instanceof Player)
                ((Player)damager).sendMessage(Utils.color("&d&lHEX [&c" + this.df.format(damage) + " &5&lDMG] "));
        }
    }
}
