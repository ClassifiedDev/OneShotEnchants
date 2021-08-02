package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

final class ExplosiveListener implements Listener {
    private boolean factions_enabled;

    public ExplosiveListener() {
        this.factions_enabled = false;
        this.factions_enabled = Bukkit.getPluginManager().isPluginEnabled("Factions");
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity().hasMetadata(Explosive.explosiveEncahntmentMetadata)) {
            Location loc = e.getEntity().getLocation();
            if (e.getEntity().getPassenger() != null)
                e.getEntity().getPassenger().remove();
            if (WorldGuardUtils.isPvPDisabled(loc))
                return;
            int radius = ((MetadataValue)e.getEntity().getMetadata(Explosive.explosiveEncahntmentMetadata).get(0)).asInt(), level = radius;
            String enchantment_owner = e.getEntity().hasMetadata(Explosive.explosiveEncahntmentOwnerMetadata) ? ((MetadataValue)e.getEntity().getMetadata(Explosive.explosiveEncahntmentOwnerMetadata).get(0)).asString() : null;
            for (Entity ent : e.getEntity().getNearbyEntities(radius, radius, radius)) {
                if (!(ent instanceof LivingEntity) ||
                        ent.hasMetadata("spectator"))
                    continue;
                if (!GeneralUtils.canEffectEntity(ent))
                    continue;
                if (enchantment_owner != null && ent instanceof Player) {
                    if (((Player)ent).getName().equals(enchantment_owner))
                        continue;
                    if (isAlly((Player)ent, Bukkit.getPlayer(enchantment_owner)))
                        continue;
                }
                if (!(ent instanceof LivingEntity))
                    continue;
                ((LivingEntity)ent).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * level, 1));
            }
            //ParticleEffect.EXPLOSION_LARGE.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 5, loc, 100.0D);
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }
}
