package com.oneshotmc.enchants.enchants.armor;

import java.util.Map;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

final class LeadershipTask extends BukkitRunnable {
    public void run() {
        for (Map.Entry<String, Integer> LeadershipData : Leadership.Leaderships.entrySet()) {
            String playerName = LeadershipData.getKey();
            int level = ((Integer)LeadershipData.getValue()).intValue();
            Player p = Bukkit.getPlayer(playerName);
            if (p == null) {
                Leadership.Leaderships.remove(playerName);
                continue;
            }
            Location l = p.getEyeLocation().add(0.0D, 0.5D, 0.0D);
            int x = 0;
            for (Entity ent : p.getNearbyEntities((level * 2 + 3), (level * 2 + 3), (level * 2 + 3))) {
                if (ent instanceof Player && isAlly(p, (Player)ent))
                    x++;
            }
            p.setMetadata("leadershipLevel", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(x)));
            if (x <= 0 || l.getWorld().getName().equals("world_duels") || l.getWorld().getName().equals("world_duels2"))
                continue;
            //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new //ParticleEffect.BlockData(Material.SNOW_BLOCK, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 50, l.getBlock().getLocation(), 75.0D);
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }
}
