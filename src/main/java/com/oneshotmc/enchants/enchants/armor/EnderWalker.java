package com.oneshotmc.enchants.enchants.armor;

import java.util.ArrayList;
import java.util.List;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnderWalker extends CustomEnchantment {
    public EnderWalker() {
        super("Ender Walker", GeneralUtils.boots, 2);
        this.max = 5;
        this.base = 20.0D;
        this.interval = 10.0D;
    }

    public void applyEquipEffect(Player player, int level) {
        player.playSound(player.getLocation(), Sound.WITHER_HURT, 1.0F, 0.6F);
    }

    public void applyUnequipEffect(Player player, int level) {}

    public void applyDefenseEffect(final LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.WITHER || e.getCause() == EntityDamageEvent.DamageCause.POISON) {
            e.setCancelled(true);
            boolean heal = (Math.random() <= level * 0.15D);
            if (heal && GeneralUtils.healPlayer(player, e.getDamage()))
                //ParticleEffect.SPELL_WITCH.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.8F, 40, player.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
            e.setDamage(0.0D);
            Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                public void run() {
                    if (!player.hasPotionEffect(PotionEffectType.BLINDNESS) && !player.hasMetadata("clarityEnchant"))
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                }
            }, 2L);
            //ParticleEffect.PORTAL.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.8F, 70, player.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
        }
    }

    private List<Player> getNearbyPlayers(List<Entity> ents) {
        List<Player> players = new ArrayList<>();
        for (Entity e : ents) {
            if (e instanceof Player)
                players.add((Player)e);
        }
        return players;
    }

//    public static void updateEntity(Entity entity, List<Player> observers) {
//        World world = entity.getWorld();
//        WorldServer worldServer = ((CraftWorld)world).getHandle();
//        EntityTracker tracker = worldServer.tracker;
//        EntityTrackerEntry entry = (EntityTrackerEntry)tracker.trackedEntities.get(entity.getEntityId());
//        List<EntityPlayer> nmsPlayers = getNmsPlayers(observers);
//        if (entry != null) {
//            entry.trackedPlayers.removeAll(nmsPlayers);
//            entry.scanPlayers(nmsPlayers);
//        }
//    }

    private static List<EntityPlayer> getNmsPlayers(List<Player> players) {
        List<EntityPlayer> nsmPlayers = new ArrayList<>();
        for (Player bukkitPlayer : players) {
            CraftPlayer craftPlayer = (CraftPlayer)bukkitPlayer;
            nsmPlayers.add(craftPlayer.getHandle());
        }
        return nsmPlayers;
    }
}
