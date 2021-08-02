package com.oneshotmc.enchants.enchants.swords;

import java.util.Random;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Silence extends CustomEnchantment {
    public Silence() {
        super("Silence", GeneralUtils.swords_and_bow, 2);
        Bukkit.getServer().getPluginManager().registerEvents(new SilenceListener(), OneShotEnchants.getInstance());
        this.max = 4;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        int solitudeLevel = 0;
        if (OneShotEnchants.getEnchantments(user.getEquipment().getItemInHand()).containsKey(OneShotEnchants.getEnchantment("Solitude")))
            solitudeLevel = ((Integer)OneShotEnchants.getEnchantments(user.getEquipment().getItemInHand()).get(OneShotEnchants.getEnchantment("Solitude"))).intValue();
        double chance = (enchantLevel + solitudeLevel) * 0.02D;
        if (Math.random() < chance && target instanceof Player) {
            final Player pTarget = (Player)target;
            if (pTarget.hasMetadata("immune_silence") && user instanceof Player && Math.random() < 0.75D) {
                ((Player)user).sendMessage(Utils.color("&c&lSILENCE BLOCKED [&7" + pTarget.getName() + "&c&l] "));
                return;
            }
            long durationTicks = ((enchantLevel + solitudeLevel) * 20);
            pTarget.setMetadata("noDefenseProcs", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Boolean.valueOf(true)));
            pTarget.playSound(pTarget.getLocation(), Sound.WITHER_HURT, 1.0F, 0.25F);
            pTarget.sendMessage(Utils.color("&5&lSILENCED &7[" + (durationTicks / 20L) + "s] "));
                    //ParticleEffect.ENCHANTMENT_TABLE.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.4F, 80, pTarget.getEyeLocation(), 100.0D);
            //ParticleEffect.PORTAL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.4F, 60, pTarget.getEyeLocation(), 100.0D);
            Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                public void run() {
                    pTarget.removeMetadata("noDefenseProcs", OneShotEnchants.getInstance());
                    //ParticleEffect.ENCHANTMENT_TABLE.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.4F, 80, pTarget.getEyeLocation(), 100.0D);
                    //ParticleEffect.PORTAL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.4F, 60, pTarget.getEyeLocation(), 100.0D);
                }
            }, durationTicks);
        }
    }
}
