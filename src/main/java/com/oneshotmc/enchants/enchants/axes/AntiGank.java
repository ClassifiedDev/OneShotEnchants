package com.oneshotmc.enchants.enchants.axes;

import java.util.HashSet;
import java.util.UUID;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.MinecraftServerUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class AntiGank extends CustomEnchantment {
    private static final String antiGankNextReset_MetadataKey = "antiGank_nextReset";

    private static final String antiGankUniqueEnemies_MetadataKey = "antiGank_uniqueEnemies";

    private static final int ticksToCheck = 120;

    public AntiGank() {
        super("Anti Gank", GeneralUtils.axe, 7);
        this.max = 4;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (player.hasMetadata("antiGank_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > ((MetadataValue)player.getMetadata("antiGank_nextReset").get(0)).asLong()) {
                player.removeMetadata("antiGank_uniqueEnemies", OneShotEnchants.getInstance());
                player.setMetadata("antiGank_nextReset", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick() + 120)));
                return;
            }
        } else {
            player.setMetadata("antiGank_nextReset", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick() + 120)));
        }
        HashSet<UUID> enemies = player.hasMetadata("antiGank_uniqueEnemies") ? (HashSet<UUID>)((MetadataValue)player.getMetadata("antiGank_uniqueEnemies").get(0)).value() : new HashSet<>();
        if (enemies.size() >= 6 - level) {
            e.setDamage(e.getDamage() * Math.min(1.5D, 1.0D + 0.1D * enemies.size()));
            Location l = playerHit.getLocation().add(0.0D, 1.0D, 0.0D);
            ParticleEffect.BLOCK_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.BlockData(Material.PUMPKIN, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 25, l.getBlock().getLocation(), 150.0D);
        }
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(attacker instanceof Player))
            return;
        if (player.hasMetadata("antiGank_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > ((MetadataValue)player.getMetadata("antiGank_nextReset").get(0)).asLong())
                player.removeMetadata("antiGank_uniqueEnemies", OneShotEnchants.getInstance());
        } else {
            player.setMetadata("antiGank_nextReset", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick() + 120)));
        }
        HashSet<UUID> enemies = player.hasMetadata("antiGank_uniqueEnemies") ? (HashSet<UUID>)((MetadataValue)player.getMetadata("antiGank_uniqueEnemies").get(0)).value() : new HashSet<>();
        Player pAttacker = (Player)attacker;
        enemies.add(pAttacker.getUniqueId());
        player.setMetadata("antiGank_uniqueEnemies", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), enemies));
    }
}
