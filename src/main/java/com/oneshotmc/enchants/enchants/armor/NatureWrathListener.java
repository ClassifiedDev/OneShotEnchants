package com.oneshotmc.enchants.enchants.armor;


import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.MetadataValue;

final class NatureWrathListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && ((Player)e
                .getDamager()).getWalkSpeed() == 0.0F) {
            e.setCancelled(true);
            //ParticleEffect.CRIT_MAGIC.display(0.0F, 0.0F, 0.0F, 0.6F, 30, e.getEntity().getLocation(), 100.0D);
        }
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (p.hasMetadata("natureWrathTask")) {
                NatureWrathTask nwt = (NatureWrathTask)((MetadataValue)p.getMetadata("natureWrathTask").get(0)).value();
                if (Math.random() < 0.3D - 0.075D * nwt.enchantLevel) {
                    nwt.cancel();
                    p.setWalkSpeed(0.2F);
                    p.removeMetadata("natureWrathTask", OneShotEnchants.getInstance());
                    //ParticleEffect.EXPLOSION_LARGE.display(0.0F, 0.0F, 0.0F, 0.8F, 5, e.getEntity().getLocation(), 100.0D);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player && ((Player)e
                .getEntity()).getWalkSpeed() == 0.0F) {
            e.setCancelled(true);
            //ParticleEffect.CRIT_MAGIC.display(0.0F, 0.0F, 0.0F, 0.6F, 30, e.getEntity().getLocation(), 100.0D);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().hasMetadata("natureWrathTask")) {
            NatureWrathTask nwt = (NatureWrathTask)((MetadataValue)e.getEntity().getMetadata("natureWrathTask").get(0)).value();
            nwt.cancel();
            e.getEntity().removeMetadata("natureWrathTask", OneShotEnchants.getInstance());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (e.getPlayer().hasMetadata("natureWrathTask")) {
            NatureWrathTask nwt = (NatureWrathTask)((MetadataValue)e.getPlayer().getMetadata("natureWrathTask").get(0)).value();
            nwt.cancel();
            e.getPlayer().removeMetadata("natureWrathTask", OneShotEnchants.getInstance());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (e.getPlayer().getWalkSpeed() == 0.0F)
            e.getPlayer().setWalkSpeed(0.2F);
        if (e.getPlayer().hasMetadata("natureWrathTask")) {
            NatureWrathTask nwt = (NatureWrathTask)((MetadataValue)e.getPlayer().getMetadata("natureWrathTask").get(0)).value();
            nwt.cancel();
            e.getPlayer().removeMetadata("natureWrathTask", OneShotEnchants.getInstance());
        }
    }
}
