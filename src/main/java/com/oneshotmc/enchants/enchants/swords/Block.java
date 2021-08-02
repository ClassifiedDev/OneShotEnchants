package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Block extends CustomEnchantment {
    public Block() {
        super("Block", GeneralUtils.swords, 2);
        this.max = 3;
        this.base = 17.0D;
        this.interval = 4.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            Player player2 = (Player)player;
            double random = Math.random();
            double chance = 0.1D * level;
            if (random < chance && player2.isBlocking()) {
                double reduceDamage = 0.5D * (level / 2);
                e.setDamage(e.getDamage() * (1.0D - reduceDamage));
                player2.playSound(player2.getLocation(), Sound.ITEM_BREAK, 0.7F, 0.2F);
            }
        }
    }
}
