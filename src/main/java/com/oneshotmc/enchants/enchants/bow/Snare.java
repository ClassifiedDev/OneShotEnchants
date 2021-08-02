package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Snare extends CustomEnchantment {
    public Snare() {
        super("Snare", new Material[] { Material.BOW }, 2);
        this.max = 4;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double chance = level * 0.09D;
        double random = Math.random();
        if (random < chance) {
            if (playerHit.hasMetadata("metaphysicalEnchant")) {
                int metaphysicalLevel = ((MetadataValue)playerHit.getMetadata("metaphysicalEnchant").get(0)).asInt();
                chance -= metaphysicalLevel * 0.07D;
                if (random > chance) {
                    ((Player)playerHit).sendMessage(Utils.color("&8&lMETAPHYSICAL (&8Snare blocked!&8&l) "));
                    return;
                }
            }
            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 20 + 20, level));
            if (level >= 3)
                playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, level * 20 + 20, level - 3));
            Location l = playerHit.getLocation().add(0.0D, 0.5D, 0.0D);
            //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new //ParticleEffect.BlockData(Material.VINE, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, l.getBlock().getLocation(), 75.0D);
        }
    }
}
