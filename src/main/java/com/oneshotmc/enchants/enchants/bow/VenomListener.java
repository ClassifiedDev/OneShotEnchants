package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

final class VenomListener implements Listener {
    private boolean factions_enabled;

    public VenomListener() {
        this.factions_enabled = false;
        this.factions_enabled = Bukkit.getPluginManager().isPluginEnabled("Factions");
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity().hasMetadata(Venom.venomEncahntmentMetadata)) {
            Location loc = e.getEntity().getLocation();
            if (e.getEntity().getPassenger() != null)
                e.getEntity().getPassenger().remove();
            if (WorldGuardUtils.isPvPDisabled(loc))
                return;
            int radius = ((MetadataValue)e.getEntity().getMetadata(Venom.venomEncahntmentMetadata).get(0)).asInt(), level = radius;
            String enchantment_owner = e.getEntity().hasMetadata(Venom.venomEncahntmentOwnerMetadata) ? ((MetadataValue)e.getEntity().getMetadata(Venom.venomEncahntmentOwnerMetadata).get(0)).asString() : null;
            for (Entity ent : e.getEntity().getNearbyEntities(radius, radius, radius)) {
                if (ent.hasMetadata("spectator"))
                    continue;
                if (!(e.getEntity() instanceof Player))
                    continue;
                if (!(ent instanceof org.bukkit.entity.LivingEntity))
                    continue;
                if (enchantment_owner != null && ent instanceof Player) {
                    if (((Player)ent).getName().equals(enchantment_owner))
                        continue;
                    if (isAlly((Player)ent, Bukkit.getPlayer(enchantment_owner)))
                        continue;
                }
                if (!(ent instanceof Player))
                    continue;
                ((Player)ent).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 25 * level, 1));
            }
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }
}
