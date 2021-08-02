package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cowification extends CustomEnchantment {
    public Cowification() {
        super("Cowification", new Material[] { Material.BOW }, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new CowificationListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (Math.random() < 0.1D * level) {
            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * level, 1));
            if (playerHit instanceof Player)
                ((Player)playerHit).playSound(playerHit.getLocation(), Sound.COW_IDLE, 1.0F, 0.75F);
        }
        //ParticleEffect.EXPLOSION_LARGE.display(0.0F, 0.0F, 0.0F, 0.25F, 1, playerHit.getEyeLocation(), 100.0D);
    }

    public void applyProjectileEffect(LivingEntity player, int level, ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Arrow && player instanceof Player) {
            if (!OneShotEnchants.itemHasEnchantment(((Player)player).getItemInHand(), "Explosive")) {
                final Cow mark = (Cow)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0.0D, 3.0D, 0.0D), EntityType.COW);
                mark.setMetadata("cowification", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Boolean.valueOf(true)));
                mark.setCanPickupItems(false);
                mark.setNoDamageTicks(200);
                e.getEntity().setPassenger((Entity)mark);
                Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                    public void run() {
                        if (mark != null && mark.isValid() && !mark.isDead())
                            mark.remove();
                    }
                },  200L);
            }
            e.getEntity().setMetadata(cowificationEncahntmentMetadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
            e.getEntity().setMetadata(cowificationEncahntmentOwnerMetadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), ((Player)player).getName()));
        }
    }

    protected static String cowificationEncahntmentMetadata = "cowification_enchantment";

    protected static String cowificationEncahntmentOwnerMetadata = "cowification_enchantment_owner";
}
