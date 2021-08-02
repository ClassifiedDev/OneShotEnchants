package com.oneshotmc.enchants.enchants.bow;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class DimensionRift extends CustomEnchantment {
    public DimensionRift() {
        super("Dimension Rift", new Material[] { Material.BOW }, 3);
        this.max = 4;
        this.base = 17.0D;
        this.interval = 4.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (Math.random() > level * 0.05D)
            return;
        if (!(e.getDamager() instanceof org.bukkit.entity.Projectile))
            return;
        if (!(playerHit instanceof Player))
            return;
        final HashMap<Location, Material> blocks = new HashMap<>();
        Location enemyLoc = playerHit.getLocation().subtract(0.0D, 1.0D, 0.0D).clone();
        FLocation fL = new FLocation(enemyLoc);
        if (Board.getInstance().getFactionAt(fL).isNormal())
            return;
        if (WorldGuardUtils.isSpawn((Player)playerHit))
            return;
        for (int x = (level >= 4) ? -1 : 0; x <= 1; x++) {
            for (int z = (level >= 3) ? -1 : 0; z <= 1; z++) {
                Location l = enemyLoc.clone().add(x, 0.0D, z);
                Material m = l.getBlock().getType();
                if ((m.isBlock() || m == Material.AIR) && !(l.getBlock().getState() instanceof org.bukkit.block.Chest) && m != Material.SOUL_SAND && m != Material.WEB && m != Material.BEDROCK && m != Material.ENDER_PORTAL && m != Material.PORTAL) {
                    blocks.put(l.clone(), m);
                    l.getBlock().setType(Material.SOUL_SAND);
                    //ParticleEffect.SPELL_WITCH.display(0.0F, 0.0F, 0.0F, 0.7F, 10, l.clone().add(0.0D, 1.0D, 0.0D), 100.0D);
                    if (Math.random() < level * 0.1D) {
                        Location lAbove = l.getBlock().getLocation().add(0.0D, 1.0D, 0.0D);
                        Material mAbove = lAbove.getBlock().getType();
                        if ((mAbove.isBlock() || mAbove == Material.AIR) && !(lAbove.getBlock().getState() instanceof org.bukkit.block.Chest) && mAbove != Material.SOUL_SAND && mAbove != Material.WEB && mAbove != Material.BEDROCK && mAbove != Material.ENDER_PORTAL && mAbove != Material.PORTAL) {
                            blocks.put(lAbove.clone(), mAbove);
                            lAbove.getBlock().setType(Material.WEB);
                            //ParticleEffect.SPELL_WITCH.display(0.0F, 0.0F, 0.0F, 0.7F, 10, e.getEntity().getLocation(), 100.0D);
                        }
                    }
                }
            }
        }
        final BukkitTask bt = Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
            public void run() {
                HashSet<Player> playersToBoost = new HashSet<>();
                for (Map.Entry<Location, Material> data : (Iterable<Map.Entry<Location, Material>>)blocks.entrySet()) {
                    Location l = data.getKey();
                    for (Player p : l.getWorld().getPlayers()) {
                        if (!playersToBoost.contains(p) && p.getWorld().equals(l.getWorld()) && Math.abs(p.getLocation().getChunk().getX() - l.getChunk().getX()) <= 1 && Math.abs(p.getLocation().getChunk().getZ() - l.getChunk().getZ()) <= 1 && p.getLocation().distanceSquared(l) <= 4.0D)
                            playersToBoost.add(p);
                    }
                    ((Location)data.getKey()).getBlock().setType(data.getValue());
                    //ParticleEffect.BLOCK_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.BlockData(data.getValue(), (byte)0), 0.3F, 1.3F, 0.3F, 1.0F, 50, ((Location)data.getKey()).getBlock().getLocation(), 75.0D);
                    for (Player p : playersToBoost) {
                        p.removePotionEffect(PotionEffectType.JUMP);
                        p.setVelocity(new Vector(0.0D, 0.5D, 0.0D));
                    }
                }
            }
        }, (level * 15 + 40));
        blockRevertTasks.put(Integer.valueOf(bt.getTaskId()), (HashMap<Location, Material>)blocks.clone());
        Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
            public void run() {
                DimensionRift.blockRevertTasks.remove(Integer.valueOf(bt.getTaskId()));
            }
        },  (level * 15 + 40 + 1));
    }

    public void applyProjectileEffect(LivingEntity player, int level, ProjectileLaunchEvent e) {
        //ParticleEffect.SPELL_WITCH.display(0.0F, 0.0F, 0.0F, 0.7F, 16, e.getEntity().getLocation(), 100.0D);
    }

    public static HashMap<Integer, HashMap<Location, Material>> blockRevertTasks = new HashMap<>();
}
