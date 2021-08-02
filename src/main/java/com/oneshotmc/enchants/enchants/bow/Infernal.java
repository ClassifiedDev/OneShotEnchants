package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Infernal extends CustomEnchantment {
    public Infernal() {
        super("Infernal", new Material[] { Material.BOW }, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new InfernalListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        playerHit.setFireTicks(level * 20);
        //ParticleEffect.FLAME.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.25F, 10, playerHit.getEyeLocation(), 100.0D);
    }

    public void applyProjectileEffect(LivingEntity player, int level, ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            ((Arrow)e.getEntity()).setFireTicks(level * 60);
            e.getEntity().setMetadata(infernalEncahntmentMetadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
            e.getEntity().setMetadata(infernalEncahntmentOwnerMetadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), ((Player)player).getName()));
        }
    }

    protected static String infernalEncahntmentMetadata = "infernal_enchantment";

    protected static String infernalEncahntmentOwnerMetadata = "infernal_enchantment_owner";
}
