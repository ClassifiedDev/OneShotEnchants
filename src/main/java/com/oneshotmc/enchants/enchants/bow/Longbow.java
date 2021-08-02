package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Longbow extends CustomEnchantment {
    public Longbow() {
        super("Longbow", new Material[] { Material.BOW }, 2);
        this.max = 4;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (playerHit.getEquipment().getItemInHand().getType() == Material.BOW) {
            e.setDamage(e.getDamage() * (1.0D + 0.25D * level));
            if (playerHit instanceof Player)
                ((Player)playerHit).playSound(playerHit.getLocation(), Sound.ARROW_HIT, 1.0F, 0.4F);
        }
    }
}
