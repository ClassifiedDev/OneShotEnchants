package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Venom extends CustomEnchantment {
    public Venom() {
        super("Venom", new Material[] { Material.BOW }, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new VenomListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        playerHit.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 25 * level, 1));
    }

    public void applyProjectileEffect(LivingEntity player, int level, ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Arrow && player instanceof Player) {
            Player pl = (Player)player;
            if (!OneShotEnchants.itemHasEnchantment(pl.getItemInHand(), "Explosive")) {
                ExperienceOrb mark = (ExperienceOrb)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.EXPERIENCE_ORB);
                mark.setExperience(0);
                e.getEntity().setPassenger((Entity)mark);
            }
            e.getEntity().setMetadata(venomEncahntmentMetadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
            e.getEntity().setMetadata(venomEncahntmentOwnerMetadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), ((Player)player).getName()));
        }
    }

    protected static String venomEncahntmentMetadata = "venom_enchantment";

    protected static String venomEncahntmentOwnerMetadata = "venom_enchantment_owner";
}
