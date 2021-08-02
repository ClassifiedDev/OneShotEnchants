package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.listeners.EnchantmentListener;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Teleportation extends CustomEnchantment {
    public Teleportation() {
        super("Teleportation", new Material[] { Material.BOW }, 3);
        this.max = 5;
        this.base = 20.0D;
        this.interval = 10.0D;
        Bukkit.getPluginManager().registerEvents(new TeleportationEvents(), OneShotEnchants.getInstance());
    }

    private static class TeleportationEvents implements Listener {
        private TeleportationEvents() {}

        @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
        public void onArrowHit(EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Projectile && e.getDamager().hasMetadata("teleportation_level")) {
                Player player = null;
                Player playerHit = null;
                if (((Projectile)e.getDamager()).getShooter() instanceof Player)
                    player = (Player)((Projectile)e.getDamager()).getShooter();
                if (e.getEntity() instanceof Player)
                    playerHit = (Player)e.getEntity();
                if (player == null || playerHit == null)
                    return;
                int level = ((MetadataValue)e.getDamager().getMetadata("teleportation_level").get(0)).asInt();
                if (player.equals(playerHit)) {
                    e.setCancelled(true);
                    e.setDamage(0.0D);
                    if (player instanceof Player)
                        player.playSound(player.getLocation(), Sound.CAT_HISS, 0.85F, 0.2F);
                    return;
                }
                if (!(player instanceof Player) || !(playerHit instanceof Player))
                    return;
                if (GeneralUtils.isAlly(player, playerHit)) {
                    e.setCancelled(true);
                    e.setDamage(0.0D);
                    if (e.getDamager() instanceof Projectile)
                        e.getDamager().remove();
                    Location tp = playerHit.getLocation();
                    tp.setPitch(player.getLocation().getPitch());
                    tp.setYaw(player.getLocation().getYaw());
                    if (tp.distanceSquared(player.getLocation()) > Math.pow((level * 6), 2.0D)) {
                        player.sendMessage(Utils.color("&c&l(!)(!) &cYour ally is too far away to teleport to with this level of Teleporation."));
                        return;
                    }
                    if ((player.getWorld().getEnvironment() == World.Environment.THE_END || player.getWorld().getName().equals("world_koth")) && !WorldGuardUtils.isPvPDisabled(player.getLocation()) && WorldGuardUtils.isPvPDisabled(tp)) {
                        player.sendMessage(Utils.color("&c&l(!) &cYou cannot teleport from PvP-enabled to PvP-disabled with Teleportation."));
                        return;
                    }
                    player.setMetadata("ignore_combat_tag", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), tp));
                    player.teleport(tp, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    player.removeMetadata("ignore_combat_tag", OneShotEnchants.getInstance());
                    if (playerHit instanceof Player)
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.75F, 0.341F);
                    Location loc = playerHit.getLocation().add(0.0D, 0.5D, 0.0D);
                    //ParticleEffect.SPELL_WITCH.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 35, loc, 100.0D);
                }
            }
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onPlayerShootBow(EntityShootBowEvent e) {
            if (e.getEntity() instanceof Player) {
                Player p = (Player)e.getEntity();
                if (e.getBow() != null && EnchantmentListener.getValidEnchantments(e.getBow(), p).containsKey(OneShotEnchants.getEnchantment("Teleportation"))) {
                    Location loc = e.getProjectile().getLocation();
                    e.getProjectile().setMetadata("teleportation_level", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), OneShotEnchants.getEnchantments(e.getBow()).get(OneShotEnchants.getEnchantment("Teleportation"))));
                    //ParticleEffect.SPELL_WITCH.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 35, loc, 100.0D);
                }
            }
        }
    }
}
