package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.metadata.MetadataValue;

final class GuardiansListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager().hasMetadata("guardianSummoner")) {
            Player p = (Player)e.getEntity();
            String summoner = ((MetadataValue)e.getDamager().getMetadata("guardianSummoner").get(0)).asString();
            Player pSummoner = Bukkit.getPlayer(summoner);
            if (pSummoner != null && (summoner.equalsIgnoreCase(p.getName()) || GeneralUtils.isAtleastTruce(p, pSummoner))) {
                e.setCancelled(true);
                e.setDamage(0.0D);
                return;
            }
        }
        if (e.getEntity().hasMetadata("guardianSummoner") && e instanceof EntityDamageByEntityEvent) {
            Entity damager = e.getDamager();
            if (damager instanceof Projectile)
                damager = (Entity)((Projectile)damager).getShooter();
            if (damager instanceof Player) {
                Player p2 = (Player)damager;
                String summoner2 = ((MetadataValue)e.getEntity().getMetadata("guardianSummoner").get(0)).asString();
                Player pSummoner2 = Bukkit.getPlayer(summoner2);
                if (pSummoner2 != null && (summoner2.equalsIgnoreCase(p2.getName()) || GeneralUtils.isAlly(p2, pSummoner2))) {
                    e.setCancelled(true);
                    e.setDamage(0.0D);
                    return;
                }
                if (pSummoner2 != null && pSummoner2.hasMetadata("bloodLink")) {
                    int bloodCurseLevel = ((MetadataValue)pSummoner2.getMetadata("bloodLink").get(0)).asInt();
                    if (Math.random() < bloodCurseLevel * 0.05D && pSummoner2.getHealth() < pSummoner2.getMaxHealth() && !pSummoner2.isDead()) {
                        GeneralUtils.healPlayer((LivingEntity)pSummoner2, (bloodCurseLevel == 5) ? 2.0D : 1.0D);
                        //ParticleEffect.HEART.display(0.0F, 0.0F, 0.0F, 0.2F, 1, pSummoner2.getEyeLocation(), 100.0D);
                        //ParticleEffect.HEART.display(0.0F, 0.0F, 0.0F, 0.2F, 1, e.getEntity().getLocation().add(0.0D, 3.0D, 0.0D).getBlock().getLocation(), 100.0D);
                    }
                }
                ((IronGolem)e.getEntity()).setTarget((LivingEntity)p2);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("guardianSummoner")) {
            e.getDrops().clear();
            e.getEntity().remove();
        }
        if (e.getEntity().hasMetadata("targetTaskID"))
            Bukkit.getScheduler().cancelTask(((MetadataValue)e.getEntity().getMetadata("targetTaskID").get(0)).asInt());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityTarget(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player && e.getEntity().hasMetadata("guardianSummoner")) {
            Player p = (Player)e.getTarget();
            if (((MetadataValue)e.getEntity().getMetadata("guardianSummoner").get(0)).asString().equalsIgnoreCase(p.getName())) {
                e.setCancelled(true);
                return;
            }
            String summonerName = ((MetadataValue)e.getEntity().getMetadata("guardianSummoner").get(0)).asString();
            Player summonerPlayer = Bukkit.getPlayer(summonerName);
            if (summonerPlayer != null && isAlly(p, summonerPlayer))
                e.setCancelled(true);
            if (p.hasMetadata("spectator"))
                e.setCancelled(true);
        }
        if (e.getTarget() != null && e.getTarget().hasMetadata("combatNPC_PlayerName")) {
            e.setCancelled(true);
            e.setTarget((Entity)null);
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }
}
