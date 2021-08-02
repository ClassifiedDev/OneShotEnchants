package com.oneshotmc.enchants.enchants.armor;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitTask;

public class Angelic extends CustomEnchantment {
    HashMap<String, BukkitTask> playerTasks;

    public Angelic() {
        super("Angelic", GeneralUtils.armor, 2);
        this.playerTasks = new HashMap<>();
        this.max = 3;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyDefenseEffect(final LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double random = Math.random();
        double chance = 0.03D * level;
        if (random < chance) {
            long healDelay = 100L;
            if (level <= 1)
                healDelay = 100L;
            if (level == 2)
                healDelay = 90L;
            if (level == 3)
                healDelay = 80L;
            if (level == 4)
                healDelay = 70L;
            if (level >= 5)
                healDelay = 60L;
            long healDuration = Math.max(healDelay + 2L, level * 20L + 10L + ThreadLocalRandom.current().nextInt(16));
            double healAmount = 1.0D;
            if (player.hasMetadata("angelicTaskID"))
                return;
            final int taskID = (new RegenerationTask((Player)player, healDuration, healDelay, 1.0D)).runTaskTimer(OneShotEnchants.getInstance(), 0L, healDelay).getTaskId();
            Bukkit.getScheduler().runTaskLaterAsynchronously(OneShotEnchants.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.getScheduler().cancelTask(taskID);
                    Bukkit.getScheduler().runTask(OneShotEnchants.getInstance(), () -> player.removeMetadata("angelicTaskID", OneShotEnchants.getInstance()));
                }
            }, healDuration);
            player.setMetadata("angelicTaskID", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(taskID)));
        }
    }
}
