package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.MetadataValue;

final class FireballListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("hellfire")) {
            e.setCancelled(true);
            e.setDamage(0.0D);
            if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent)e).getDamager() instanceof Player) {
                ((Player)((EntityDamageByEntityEvent)e).getDamager()).playSound(e.getEntity().getLocation(), Sound.FIRE, 1.0F, 0.7F);
                e.getEntity().remove();
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity().hasMetadata(Hellfire.hellfireEncahntmentMetadata)) {
            Location loc = e.getEntity().getLocation();
            if (e.getEntity().getPassenger() != null)
                e.getEntity().getPassenger().remove();
            if (WorldGuardUtils.isPvPDisabled(loc))
                return;
            loc.getWorld().playSound(loc, Sound.FIRE, 1.0F, 0.85F);
            final int level = ((MetadataValue)e.getEntity().getMetadata(Hellfire.hellfireEncahntmentMetadata).get(0)).asInt();
            final boolean duelWorld = loc.getWorld().getName().equals("world_duels2");
            int radius = level * 2;
            if (duelWorld)
                radius /= 2;
            String enchantment_owner = e.getEntity().hasMetadata(Hellfire.hellfireEncahntmentOwnerMetadata) ? ((MetadataValue)e.getEntity().getMetadata(Hellfire.hellfireEncahntmentOwnerMetadata).get(0)).asString() : null;
            Player owner = Bukkit.getPlayer(enchantment_owner);
            for (Entity ent : e.getEntity().getNearbyEntities(radius, radius, radius)) {
                if (ent.hasMetadata("spectator"))
                    continue;
                if (!(ent instanceof LivingEntity) || ent.hasMetadata("do_not_clear"))
                    continue;
                if (enchantment_owner != null && ent instanceof Player) {
                    if (((Player)ent).getName().equals(enchantment_owner) || isAlly((Player)ent, owner))
                        continue;
                    if (!canHurt((Player)ent, owner))
                        continue;
                }
                if (!(ent instanceof Player))
                    continue;
                if (ent instanceof Player)
                   // Player player = (Player)ent;
                if (WorldGuardUtils.isPvPDisabled(ent.getLocation()))
                    continue;
                if (ent instanceof org.bukkit.entity.EnderDragon)
                    continue;
                if (ent instanceof LivingEntity)
                    ((LivingEntity)ent).setFireTicks(((LivingEntity)ent).getFireTicks() + level * 20);
                final LivingEntity le = (LivingEntity)ent;
                if (Bukkit.getPlayer(enchantment_owner) != null)
                    Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                        public void run() {
                            if (!le.isDead()) {
                                int dmg = 1 + level;
                                le.damage(duelWorld ? (dmg / 1.5D) : dmg);
                            }
                        }
                    },  1L);
                Location eloc = ent.getLocation().add(0.0D, 1.0D, 0.0D);
                //ParticleEffect.LAVA.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 16, eloc, 100.0D);
            }
            //ParticleEffect.EXPLOSION_LARGE.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 5, loc, 100.0D);
            //ParticleEffect.FLAME.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.25F, 45, loc, 100.0D);
            e.getEntity().remove();
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }

    private boolean canHurt(Player p1, Player p2) {
        return GeneralUtils.isEnemy(p1, p2);
    }
}
