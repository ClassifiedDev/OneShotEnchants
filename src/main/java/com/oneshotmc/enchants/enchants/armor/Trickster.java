package com.oneshotmc.enchants.enchants.armor;

import java.util.HashSet;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Trickster extends CustomEnchantment {
    public Trickster() {
        super("Trickster", GeneralUtils.armor, 2);
        this.max = 8;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double random = Math.random();
        double chance = 0.0125D * level;
        if (!(attacker instanceof org.bukkit.entity.Player))
            return;
        if (attacker != null && random < chance) {
            Location l1 = player.getLocation().add(0.0D, 1.0D, 0.0D);
            Location l2 = player.getTargetBlock((HashSet<Byte>) null, 4).getLocation().add(0.0D, 1.0D, 0.0D).setDirection(attacker.getLocation().getDirection());
            if (l2.getBlock().getType() == Material.AIR && l2.getBlock().getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.AIR) {
                player.teleport(l2, PlayerTeleportEvent.TeleportCause.PLUGIN);
                player.getWorld().playSound(l1, Sound.PORTAL_TRIGGER, 0.8F, 1.4F);
                //ParticleEffect.CLOUD.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, l1, 100.0D);
                //ParticleEffect.SPELL_WITCH.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, l2, 100.0D);
            }
        }
    }
}
