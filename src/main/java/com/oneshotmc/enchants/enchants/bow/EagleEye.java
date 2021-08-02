package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.utils.ArmorSlot;
import com.oneshotmc.enchants.utils.ArmorUtil;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;

public class EagleEye extends CustomEnchantment {
    public EagleEye() {
        super("Eagle Eye", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (Math.random() < 0.05D * level + 0.2D) {
            if (e.isCancelled())
                return;
            if (!(playerHit instanceof Player))
                return;
            if (playerHit.hasMetadata("lastArrowDamageEvent")) {
                long minDelayMilliseconds = (level * 200);
                long last = ((MetadataValue)playerHit.getMetadata("lastArrowDamageEvent").get(0)).asLong();
                if (System.currentTimeMillis() - last <= minDelayMilliseconds)
                    return;
            }
            double duraDamage = 1.0D;
            double dist = player.getLocation().distanceSquared(playerHit.getLocation());
            if (dist >= 100.0D)
                duraDamage++;
            if (dist >= 400.0D)
                duraDamage++;
            if (dist >= 1024.0D)
                duraDamage++;
            Player pTarget = (Player)playerHit;
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.HELMET, -1);
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.CHESTPLATE, -1);
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.LEGGINGS, -1);
            ArmorUtil.modifyPlayerArmor(pTarget, ArmorSlot.BOOTS, -1);
            //ParticleEffect.CRIT_MAGIC.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 30, playerHit.getLocation(), 100.0D);
            ((Player)player).playSound(playerHit.getLocation(), Sound.ANVIL_BREAK, 0.3F, 0.8F);
        }
    }
}
