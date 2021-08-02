package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Unfocus extends CustomEnchantment {
    public Unfocus() {
        super("Unfocus", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 20.0D;
        this.interval = 0.0D;
        Bukkit.getPluginManager().registerEvents(new UnfocusListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random() * 1.0D;
        double chance = 0.5D * level;
        if (random < chance && playerHit instanceof Player && (!playerHit.hasMetadata("unfocusEnchantmentExpire") || System.currentTimeMillis() > ((MetadataValue)playerHit.getMetadata("unfocusEnchantmentExpire").get(0)).asLong())) {
            ((Player)playerHit).sendMessage(Utils.color("&2&lUNFOCUS [" + (level * 2) + "s] "));
            ((Player)playerHit).playSound(playerHit.getLocation(), Sound.PORTAL_TRIGGER, 1.2F, 0.6F);
            ((Player)player).playSound(playerHit.getLocation(), Sound.ARROW_HIT, 1.2F, 2.5F);
            playerHit.setMetadata("unfocusEnchantmentLevel", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
            playerHit.setMetadata("unfocusEnchantmentExpire", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis() + (level * 2000))));
        }
    }
}
