package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Hellfire extends CustomEnchantment {
    public Hellfire() {
        super("Hellfire", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new FireballListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (playerHit instanceof Player) {
            Player p1 = (Player)player;
            Player p2 = (Player)playerHit;
            if (!p1.canSee(p2) || p1.getGameMode() != GameMode.SURVIVAL)
                return;
            if (e.isCancelled())
                return;
            if (WorldGuardUtils.isSpawn((Player)player))
                return;
            if (WorldGuardUtils.isPvPDisabled(player.getLocation()))
                return;
            if (WorldGuardUtils.isPvPDisabled(playerHit.getLocation()))
                return;
            playerHit.setFireTicks(level * 10);
            //ParticleEffect.FLAME.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.15F, 15, playerHit.getEyeLocation(), 50.0D);
            //ParticleEffect.LAVA.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 15, playerHit.getEyeLocation(), 50.0D);
        }
    }

    public void applyProjectileEffect(LivingEntity player, int level, ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            if (e.isCancelled())
                return;
            if (WorldGuardUtils.isSpawn((Player)player))
                return;
            if (WorldGuardUtils.isPvPDisabled(player.getLocation()))
                return;
            if (WorldGuardUtils.isPvPDisabled(e.getEntity().getLocation()))
                return;
            ((Arrow)e.getEntity()).setFireTicks(2147483647);
            e.getEntity().setMetadata(hellfireEncahntmentMetadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
            e.getEntity().setMetadata(hellfireEncahntmentOwnerMetadata, (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), ((Player)player).getName()));
        }
    }

    protected static String hellfireEncahntmentMetadata = "hellfire_enchantment";

    protected static String hellfireEncahntmentOwnerMetadata = "hellfire_enchantment_owner";
}
