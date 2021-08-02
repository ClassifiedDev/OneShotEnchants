package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Hex extends CustomEnchantment {
    public Hex() {
        super("Hex", GeneralUtils.axe, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new HexListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, final LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player))
            return;
        double random = Math.random();
        double chance = 0.02D * level;
        if (random < chance && (!playerHit.hasMetadata("hexEnchantExpire") || ((MetadataValue)playerHit.getMetadata("hexEnchantExpire").get(0)).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("hexEnchantExpire", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis() + ((2 + level / 2) * 1000))));
            playerHit.setMetadata("hexEnchantLevel", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
            //ParticleEffect.SPELL_WITCH.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, playerHit.getEyeLocation(), 100.0D);
            ((Player)playerHit).sendMessage(Utils.color("&5&lHEX DEBUFF [&d&l" + (2 + level / 2) + "s&5&l] "));
                    Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                        public void run() {
                            ((Player)playerHit).sendMessage(Utils.color("&5&lHEX OFF "));
                        }
                    },  (2 + level / 2) * 20L);
        }
    }

    public static boolean isHexed(LivingEntity e) {
        return (e.hasMetadata("hexEnchantExpire") && ((MetadataValue)e.getMetadata("hexEnchantExpire").get(0)).asLong() > System.currentTimeMillis());
    }

    public static int getHexLevel(LivingEntity e) {
        if (!isHexed(e))
            return 0;
        return ((MetadataValue)e.getMetadata("hexEnchantLevel").get(0)).asInt();
    }
}
