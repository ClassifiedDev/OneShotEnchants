package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.OneShotEnchants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

final class ReducePhoenixCount extends BukkitRunnable {
    public void run() {
        Bukkit.getLogger().info("[OneShot Enchants (Phoenix)] Reducing all phoenixProcs by 1.");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasMetadata("phoenixProcs"))
                p.setMetadata("phoenixProcs", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(Math.max(0, ((MetadataValue)p.getMetadata("phoenixProcs").get(0)).asInt() - 1))));
        }
    }
}
