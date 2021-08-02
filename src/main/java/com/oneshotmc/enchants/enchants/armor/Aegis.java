package com.oneshotmc.enchants.enchants.armor;

import java.util.HashSet;
import java.util.UUID;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.MinecraftServerUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Aegis extends CustomEnchantment {
    private static final String aegisNextReset_MetadataKey = "aegis_nextReset";

    private static final String aegisUniqueEnemies_MetadataKey = "aegis_uniqueEnemies";

    public Aegis() {
        super("Aegis", GeneralUtils.armor, 4);
        this.max = 6;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(attacker instanceof Player))
            return;
        int ticksToCheck = 100;
        if (player.hasMetadata("aegis_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > ((MetadataValue)player.getMetadata("aegis_nextReset").get(0)).asLong()) {
                player.removeMetadata("aegis_uniqueEnemies", OneShotEnchants.getInstance());
                player.setMetadata("aegis_nextReset", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick() + 100)));
            }
        } else {
            player.setMetadata("aegis_nextReset", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick() + 100)));
        }
        HashSet<UUID> enemies = player.hasMetadata("aegis_uniqueEnemies") ? (HashSet<UUID>)((MetadataValue)player.getMetadata("aegis_uniqueEnemies").get(0)).value() : new HashSet<>();
        Player pAttacker = (Player)attacker;
        if (enemies.size() > 8 - level && !enemies.contains(pAttacker.getUniqueId())) {
            e.setDamage(e.getDamage() * 0.5D);
            //ParticleEffect.CRIT_MAGIC.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, player.getEyeLocation(), 100.0D);
            return;
        }
        enemies.add(pAttacker.getUniqueId());
        player.setMetadata("aegis_uniqueEnemies", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), enemies));
    }
}
