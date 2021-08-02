package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Explosive extends CustomEnchantment {
    public Explosive() {
        super("Explosive", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new ExplosiveListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player))
            return;
        playerHit.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * level, 1));
        //ParticleEffect.EXPLOSION_LARGE.display(0.0F, 0.0F, 0.0F, 0.25F, 1, playerHit.getEyeLocation(), 100.0D);
    }

    public void applyProjectileEffect(LivingEntity player, int level, ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Arrow && player instanceof Player) {
            WitherSkull ws = (WitherSkull)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.WITHER_SKULL);
            ws.setIsIncendiary(false);
            ws.setYield(0.0F);
            e.getEntity().setPassenger((Entity)ws);
            e.getEntity().setMetadata(explosiveEncahntmentMetadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
            e.getEntity().setMetadata(explosiveEncahntmentOwnerMetadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), ((Player)player).getName()));
        }
    }

    protected static String explosiveEncahntmentMetadata = "explosive_enchantment";

    protected static String explosiveEncahntmentOwnerMetadata = "explosive_enchantment_owner";
}
