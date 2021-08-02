package com.oneshotmc.enchants.crystals.listeners;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.crystals.ItemUtils;
import com.oneshotmc.enchants.crystals.PlayerKillTracker;
import com.oneshotmc.enchants.crystals.TextUtils;
import com.oneshotmc.enchants.crystals.Tracker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {
    public static HashMap<String, PlayerKillTracker> playerKills = new HashMap<>();

    public static HashMap<UUID, Long> lastcrystalCollected = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player) && e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            ItemStack hand = killer.getItemInHand();
            if (hand == null || !ItemUtils.isTrackable(hand))
                return;
            Tracker tracker = Tracker.getTrackerType(hand);
            if (tracker == null || !tracker.trackMobKills())
                return;
            int kills = ItemUtils.getTrackedKills(hand);
            if (kills == -1)
                kills = 0;
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.4F);
            ItemUtils.setTrackedKills(hand, tracker, ++kills);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerKillPlayer(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            if (killer.equals(e.getEntity()))
                return;
            ItemStack hand = killer.getItemInHand();
            if (e.getEntity().getLastDamageCause() != null && e
                    .getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent)e.getEntity().getLastDamageCause();
                if (damageEvent.getDamager() instanceof Arrow && (hand == null || hand.getType() != Material.BOW))
                    return;
            }
            if (hand == null || !ItemUtils.isTrackable(hand) || hand.getType() == Material.BOW)
                return;
            Tracker tracker = Tracker.getTrackerType(hand);
            if (tracker == null)
                return;
            if (tracker.trackMobKills())
                return;
            if (lastcrystalCollected.containsKey(killer.getUniqueId())) {
                long time = ((Long)lastcrystalCollected.get(killer.getUniqueId())).longValue();
                if (time > System.currentTimeMillis()) {
                    killer.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot harvest more crystals for another " +
                            TextUtils.getFormattedCooldown(time) + "!");
                    return;
                }
            }
            if (!canPlayerKillPlayer(killer, e.getEntity())) {
                Long time2 = getPlayerExpireTime(killer, e.getEntity());
                if (time2 != null) {
                    killer.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot collect " + e
                            .getEntity().getName() + "'s crystal for another " +
                            TextUtils.getFormattedCooldown(time2.longValue()));
                    return;
                }
            }
            if (!logPlayerKill(killer, e.getEntity()))
                return;
            int kills = ItemUtils.getTrackedKills(hand);
            if (kills == -1)
                kills = 0;
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.4F);
            ItemUtils.setTrackedKills(hand, tracker, ++kills);
            lastcrystalCollected.put(killer.getUniqueId(),
                    Long.valueOf(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2L)));
        }
    }

    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent e) {
        if (e.getEntity() != null && e.getEntity() instanceof Player && Tracker.getTrackerType(e.getBow()) != null)
            e.getProjectile().setMetadata("bow_used", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), e.getBow()));
    }

    public static Long getPlayerExpireTime(Player p, Player dead) {
        PlayerKillTracker tracker = playerKills.get(p.getName());
        if (tracker == null)
            return null;
        return (Long)tracker.get(dead.getName());
    }

    public static boolean logPlayerKill(Player p, Player dead) {
        if (p.hasMetadata("kill_tracker_cooldown") && p.getMetadata("kill_tracker_cooldown").size() > 0) {
            long time = ((MetadataValue)p.getMetadata("kill_tracker_cooldown").get(0)).asLong();
            if (time > System.currentTimeMillis())
                return false;
        }
        p.setMetadata("kill_tracker_cooldown", (MetadataValue)new FixedMetadataValue(
                OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis() + 100L)));
        PlayerKillTracker tracker = null;
        if (playerKills.containsKey(p.getName())) {
            tracker = playerKills.get(p.getName());
        } else {
            tracker = new PlayerKillTracker();
        }
        tracker.put(dead.getName(), Long.valueOf(System.currentTimeMillis() + 600000L));
        playerKills.put(p.getName(), tracker);
        return true;
    }

    public static boolean canPlayerKillPlayer(Player p, Player dead) {
        PlayerKillTracker tracker = playerKills.get(p.getName());
        if (tracker == null)
            return true;
        Long expire = (Long)tracker.get(dead.getName());
        if (expire != null && expire.longValue() <= System.currentTimeMillis()) {
            tracker.remove(dead.getName());
            return true;
        }
        return false;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageEvent(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player && e.getDamager().hasMetadata("bow_used") && e
                .getDamager().getMetadata("bow_used").size() > 0) {
            final Arrow arrow = (Arrow)e.getDamager();
            if (arrow.getShooter() instanceof Player) {
                final Player shooter = (Player)arrow.getShooter();
                final Player p = (Player)e.getEntity();
                if (e.getFinalDamage() >= p.getHealthScale())
                    (new BukkitRunnable() {
                        public void run() {
                            if (p.isDead() || p.getTicksLived() <= 3) {
                                ItemStack bow = (arrow.hasMetadata("bow_used") && arrow.getMetadata("bow_used").size() > 0) ? (ItemStack)((MetadataValue)arrow.getMetadata("bow_used").get(0)).value() : null;
                                if (shooter != null && bow != null && bow.getType() == Material.BOW) {
                                    Tracker tracker = Tracker.getTrackerType(bow);
                                    if (tracker == null)
                                        return;
                                    if (!PlayerListener.canPlayerKillPlayer(shooter, p)) {
                                        Long time = PlayerListener.getPlayerExpireTime(shooter, p);
                                        if (time != null) {
                                            shooter.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot collect " + p
                                                    .getName() + "'s crystal for another " +
                                                    TextUtils.getFormattedCooldown(time.longValue()));
                                            return;
                                        }
                                    }
                                    if (PlayerListener.lastcrystalCollected.containsKey(shooter.getUniqueId())) {
                                        long time2 = ((Long)PlayerListener.lastcrystalCollected.get(shooter.getUniqueId())).longValue();
                                        if (time2 > System.currentTimeMillis()) {
                                            shooter.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot harvest more crystals for another " +

                                                    TextUtils.getFormattedCooldown(time2) + "!");
                                            return;
                                        }
                                    }
                                    PlayerListener.logPlayerKill(shooter, p);
                                    int kills = ItemUtils.getTrackedKills(bow);
                                    if (kills == -1)
                                        kills = 0;
                                    shooter.playSound(shooter.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.4F);
                                    ItemUtils.setTrackedKills(bow, tracker, ++kills);
                                    PlayerListener.lastcrystalCollected.put(shooter.getUniqueId(),
                                            Long.valueOf(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2L)));
                                }
                            }
                        }
                    }).runTaskLater(OneShotEnchants.getInstance(), 1L);
            }
        }
    }

    static {
        playerKills = new HashMap<>();
        lastcrystalCollected = new HashMap<>();
    }
}
