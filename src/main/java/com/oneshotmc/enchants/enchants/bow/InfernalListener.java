package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.MetadataValue;

final class InfernalListener implements Listener {
    private boolean factions_enabled;

    public InfernalListener() {
        this.factions_enabled = false;
        this.factions_enabled = Bukkit.getPluginManager().isPluginEnabled("Factions");
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity().hasMetadata(Infernal.infernalEncahntmentMetadata)) {
            Location loc = e.getEntity().getLocation();
            if (WorldGuardUtils.isPvPDisabled(loc))
                return;
            int radius = ((MetadataValue)e.getEntity().getMetadata(Infernal.infernalEncahntmentMetadata).get(0)).asInt(), level = radius;
            String enchantment_owner = e.getEntity().hasMetadata(Infernal.infernalEncahntmentOwnerMetadata) ? ((MetadataValue)e.getEntity().getMetadata(Infernal.infernalEncahntmentOwnerMetadata).get(0)).asString() : null;
            for (Entity ent : e.getEntity().getNearbyEntities(radius, radius, radius)) {
                if (ent.hasMetadata("spectator") ||
                        ent.hasMetadata("do_not_clear"))
                    continue;
                if (!(ent instanceof org.bukkit.entity.LivingEntity))
                    continue;
                if (enchantment_owner != null && ent instanceof Player) {
                    if (((Player)ent).getName().equals(enchantment_owner))
                        continue;
                    if (isAlly((Player)ent, Bukkit.getPlayer(enchantment_owner)))
                        continue;
                }
                ent.setFireTicks(radius * 20);
            }
            //ParticleEffect.FLAME.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.25F, 20, loc, 100.0D);
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }
}
