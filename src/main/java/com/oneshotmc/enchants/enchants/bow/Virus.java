package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;

public class Virus extends CustomEnchantment {
    public Virus() {
        super("Virus", new Material[] { Material.BOW }, 2);
        this.max = 4;
        this.base = 20.0D;
        this.interval = 0.0D;
        Bukkit.getPluginManager().registerEvents(new VirusListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        playerHit.setMetadata("virusEnchantmentLevel", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
        playerHit.setMetadata("virusEnchantmentExpire", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis() + (level * 1500))));
        double random = Math.random() * 1.0D;
        double chance = 0.15D * level;
        if (random < chance) {
            playerHit.removePotionEffect(PotionEffectType.REGENERATION);
            //ParticleEffect.SPELL_WITCH.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 25, playerHit.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
            if (playerHit instanceof Player)
                ((Player)playerHit).playSound(playerHit.getLocation(), Sound.ENDERMAN_TELEPORT, 0.6F, 2.0F);
        }
    }
}
