package com.oneshotmc.enchants.enchants.armor;

import java.util.Map;

import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchants.armor.Commander;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

final class CommanderTask extends BukkitRunnable {
    public void run() {
        for (Map.Entry<String, Integer> commanderData : Commander.commanders.entrySet()) {
            String playerName = commanderData.getKey();
            int level = ((Integer)commanderData.getValue()).intValue();
            if (Bukkit.getPlayer(playerName) == null) {
                Commander.commanders.remove(playerName);
                continue;
            }
            Player p = Bukkit.getPlayer(playerName);
            Location l = p.getEyeLocation().add(0.0D, 0.5D, 0.0D);
            int x = 0;
            for (Entity ent : p.getNearbyEntities((level * 2 + 3), (level * 2 + 3), (level * 2 + 3))) {
                if (ent instanceof Player && isAlly(p, (Player)ent)) {
                    x++;
                    Player pAlly = (Player)ent;
                    pAlly.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, ((level > 3) ? 30 : 15) * 20, (level > 4) ? 1 : 0));
                    if (level >= 2 && pAlly.hasPotionEffect(PotionEffectType.WEAKNESS))
                        pAlly.removePotionEffect(PotionEffectType.WEAKNESS);
                    if (level >= 3 && pAlly.hasPotionEffect(PotionEffectType.CONFUSION))
                        pAlly.removePotionEffect(PotionEffectType.CONFUSION);
                    if (level >= 4)
                        pAlly.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, (level != 4) ? 1 : 0));
                    if (level != 5 || !pAlly.hasPotionEffect(PotionEffectType.POISON))
                        continue;
                    pAlly.removePotionEffect(PotionEffectType.POISON);
                }
            }
            if (x <= 0 || p.getWorld().getName().equals("world_duels") || p.getWorld().getName().equals("world_duels2") || p.getWorld().getName().equals("cosmic-pvparenas"))
                continue;
            //ParticleEffect.BLOCK_CRACK.display((ParticleEffect.ParticleData)new //ParticleEffect.BlockData(Material.DIAMOND_BLOCK, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 50, l, 75.0D);
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }
}
