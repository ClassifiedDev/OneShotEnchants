package com.oneshotmc.enchants.enchants.armor;

import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Clarity extends CustomEnchantment {
    private ConcurrentMap<Player, Integer> claritiedPlayers;

    public Clarity() {
        super("Clarity", GeneralUtils.armor, 2);
        this.claritiedPlayers = (new MapMaker()).weakKeys().concurrencyLevel(32).makeMap();
        this.max = 3;
        this.base = 10.0D;
        this.interval = 5.0D;
        //Bukkit.getScheduler().scheduleSyncRepeatingTask(OneShotEnchants.getInstance(), () -> this.claritiedPlayers.forEach(()), 3L, 3L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(OneShotEnchants.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : claritiedPlayers.keySet()) {
                    if (p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                        for (PotionEffect eff : p.getActivePotionEffects()) {
                            if (!canBlindnessApply(p, eff.getAmplifier())) p.removePotionEffect(PotionEffectType.BLINDNESS);
                        }
                    }
                }
            }
        }, 3L, 3L);
    }

    public void applyEquipEffect(Player player, int level) {
        int maxBlindLevelToRemove = (level == 3) ? 3 : (level - 1);
        this.claritiedPlayers.put(player, Integer.valueOf(maxBlindLevelToRemove));
        player.setMetadata("clarityEnchant", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
    }

    public static boolean canBlindnessApply(Player player, int blindLevel) {
        if (!player.hasMetadata("clarityEnchant"))
            return true;
        int level = ((MetadataValue)player.getMetadata("clarityEnchant").get(0)).asInt();
        if (level <= 0)
            return true;
        int maxBlindLevelToRemove = (level == 3) ? 3 : (level - 1);
        return (blindLevel > maxBlindLevelToRemove);
    }

    public void applyUnequipEffect(Player player, int level) {
        player.removeMetadata("clarityEnchant", OneShotEnchants.getInstance());
        this.claritiedPlayers.remove(player);
    }
}
