package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.MinecraftServerUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Dominate extends CustomEnchantment {
    public Dominate() {
        super("Dominate", GeneralUtils.swords, 2);
        this.max = 4;
        this.base = 15.0D;
        this.interval = 15.0D;
        Bukkit.getServer().getPluginManager().registerEvents(new DominateEffectListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double procChance = 0.04D * level;
        if (Math.random() < procChance) {
            if (playerHit.hasMetadata(dominateEffectLevel_metadata) && ((MetadataValue)playerHit.getMetadata(dominateEffectLevel_metadata).get(0)).asInt() > level)
                return;
            if (!playerHit.hasMetadata(dominateEffectLevel_metadata) && playerHit instanceof Player)
                ((Player)playerHit).sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "* DOMINATED [" + ChatColor.RED + "-" + (level * 5) + "% DMG for " + (level * 2) + "s" + ChatColor.RED + ChatColor.BOLD + "] *");
            playerHit.setMetadata(dominateEffectExpire_metadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick() + level * 2 * 20)));
            playerHit.setMetadata(dominateEffectLevel_metadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
            //ParticleEffect.ENCHANTMENT_TABLE.display(0.0F, 0.0F, 0.0F, 0.8F, 32, playerHit.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
        }
    }

    protected static String dominateEffectLevel_metadata = "dominateEffect";

    protected static String dominateEffectExpire_metadata = "dominateExpire";
}
