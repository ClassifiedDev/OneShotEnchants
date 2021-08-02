package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Corrupt extends CustomEnchantment {
    public Corrupt() {
        super("Corrupt", GeneralUtils.axe, 2);
        this.max = 4;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, final LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player))
            return;
        double random = Math.random();
        double chance = 5.0D + 0.02D * level;
        if (random < chance && (!playerHit.hasMetadata("corruptEnchantExpire") || ((MetadataValue)playerHit.getMetadata("corruptEnchantExpire").get(0)).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("corruptEnchantExpire", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis() + (level * 2 * 1000))));
            playerHit.setMetadata("corruptEnchantLevel", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
            //ParticleEffect.PORTAL.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 25, playerHit.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
            if (OneShotEnchants.itemHasEnchantment(((Player)playerHit).getItemInHand(), "Inversion"))
                return;
            if (playerHit.hasMetadata("corruptTaskID"))
                return;
            int damageAmount = (level >= 3) ? 2 : 1;
            int damageInterval = level;
            final int taskID = (new CorruptDamageTask((Player)playerHit, damageInterval, damageAmount)).runTaskTimer(OneShotEnchants.getInstance(), 0L, (damageInterval * 20)).getTaskId();
            Bukkit.getScheduler().runTaskLaterAsynchronously(OneShotEnchants.getInstance(), new Runnable() {
                public void run() {
                    Bukkit.getScheduler().cancelTask(taskID);
                    Bukkit.getScheduler().runTask(OneShotEnchants.getInstance(), () -> playerHit.removeMetadata("corruptTaskID", OneShotEnchants.getInstance()));
                }
            }, (long)Math.pow(level, 2.0D) * 20L);
            playerHit.setMetadata("corruptTaskID", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(taskID)));
        }
    }

    public static boolean isCorrupted(LivingEntity e) {
        return (e.hasMetadata("corruptEnchantExpire") && ((MetadataValue)e.getMetadata("corruptEnchantExpire").get(0)).asLong() > System.currentTimeMillis());
    }

    public static int getCorruptLevel(LivingEntity e) {
        if (!isCorrupted(e))
            return 0;
        return ((MetadataValue)e.getMetadata("corruptEnchantLevel").get(0)).asInt();
    }
}
