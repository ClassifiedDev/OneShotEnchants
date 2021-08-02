package com.oneshotmc.enchants.enchants.armor;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.MetadataValue;

final class SelfDestructListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockExplode(EntityExplodeEvent e) {
        if (e.getEntity() != null && e.getEntity().hasMetadata("SelfDestructTNT")) {
            e.setCancelled(true);
            e.setYield(0.0F);
            e.blockList().clear();
            if (WorldGuardUtils.isPvPDisabled(e.getLocation()))
                return;
            Location loc = e.getLocation();
            loc.getWorld().playSound(loc, Sound.EXPLODE, 1.0F, 1.0F);
            ParticleEffect.EXPLOSION_LARGE.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 3, loc, 100.0D);
            String exploder = ((MetadataValue)e.getEntity().getMetadata("SelfDestructTNT").get(0)).asString();
            Player pExploder = Bukkit.getPlayer(exploder);
            FPlayer fp = (pExploder != null) ? FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)pExploder) : null;
            for (Entity ent : e.getEntity().getNearbyEntities(4.0D, 4.0D, 4.0D)) {
                if (ent instanceof LivingEntity && !ent.hasMetadata("spectator")) {
                    if (ent instanceof Player && fp != null) {
                        FPlayer fpEnt = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)ent);
                        if (fp.getRelationTo((RelationParticipator)fpEnt).isAtLeast(Relation.TRUCE))
                            continue;
                    }
                    if (WorldGuardUtils.isPvPDisabled(ent.getLocation()))
                        continue;
                    ((LivingEntity)ent).damage(16.0D);
                    ((LivingEntity)ent).setFireTicks(40);
                    GeneralUtils.pushAwayEntity((LivingEntity)ent, e.getEntity(), 1.7000000476837158D);
                }
            }
            e.getEntity().remove();
        }
    }
}
