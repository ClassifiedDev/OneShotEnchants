package com.oneshotmc.enchants.enchants.bow;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import java.util.Random;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.DurabilityUtil;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Healing extends CustomEnchantment {
    public Healing() {
        super("Healing", new Material[] { Material.BOW }, 3);
        this.max = 4;
        this.base = 20.0D;
        this.interval = 10.0D;
        Bukkit.getPluginManager().registerEvents(new HealingEvents(), OneShotEnchants.getInstance());
    }

    private static class HealingEvents implements Listener {
        private HealingEvents() {}

        @EventHandler(priority = EventPriority.LOWEST)
        public void onArrowHit(EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Projectile && e.getDamager().hasMetadata("healing_level")) {
                Player player = null;
                Player playerHit = null;
                if (((Projectile)e.getDamager()).getShooter() instanceof Player)
                    player = (Player)((Projectile)e.getDamager()).getShooter();
                if (e.getEntity() instanceof Player)
                    playerHit = (Player)e.getEntity();
                if (player == null || playerHit == null)
                    return;
                int level = ((MetadataValue)e.getDamager().getMetadata("healing_level").get(0)).asInt();
                if (player.equals(playerHit)) {
                    e.setCancelled(true);
                    e.setDamage(0.0D);
                    if (player instanceof Player)
                        player.playSound(player.getLocation(), Sound.CAT_HISS, 0.85F, 0.2F);
                    return;
                }
                if (!(player instanceof Player) || !(playerHit instanceof Player))
                    return;
                FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
                Faction f = fPlayer.getFaction();
                Faction fply = FPlayers.getInstance().getByPlayer(playerHit).getFaction();
                if (f.isNormal() && fply.isNormal() && (fply.getRelationTo((RelationParticipator)f).isAlly() || fply.equals(f))) {
                    int min_heal = 1 * level;
                    int max_heal = 3 * level;
                    double heal = ((new Random()).nextInt(max_heal - min_heal) + min_heal);
                    boolean absorption = (level >= 3) ? ((Math.random() < 0.15D * (level - 2))) : false;
                    e.setCancelled(true);
                    e.setDamage(0.0D);
                    if (e.getDamager() instanceof Projectile)
                        e.getDamager().remove();
                    double current_hp = playerHit.getHealth();
                    if (current_hp + heal > playerHit.getMaxHealth()) {
                        GeneralUtils.setPlayerHealth((LivingEntity)playerHit, playerHit.getMaxHealth());
                        if (!absorption)
                            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20 * (4 + level), level - 1));
                    } else {
                        GeneralUtils.healPlayer((LivingEntity)playerHit, heal);
                    }
                    if (Math.random() < 0.25D * level)
                        DurabilityUtil.healMostDamagedArmorPeice(player, (short)1);
                    if (absorption)
                        playerHit.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * (1 + level), level - 3));
                    if (playerHit instanceof Player)
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.75F, 0.341F);
                    Location loc = playerHit.getLocation().add(0.0D, 0.5D, 0.0D);
                    //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new //ParticleEffect.BlockData(Material.EMERALD_BLOCK, (byte)0), 0.4F, 1.0F, 0.4F, 1.0F, 50, loc.getBlock().getLocation(), 150.0D);
                }
            }
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onPlayerShootBow(EntityShootBowEvent e) {
            if (e.getBow() != null && OneShotEnchants.itemHasEnchantment(e.getBow(), "Healing")) {
                Location loc = e.getProjectile().getLocation();
                e.getProjectile().setMetadata("healing_level", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), OneShotEnchants.getEnchantments(e.getBow()).get(OneShotEnchants.getEnchantment("Healing"))));
                //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new //ParticleEffect.BlockData(Material.EMERALD_BLOCK, (byte)0), 0.4F, 1.0F, 0.4F, 1.0F, 50, loc.getBlock().getLocation(), 150.0D);
            }
        }
    }
}
