package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Ravenous extends CustomEnchantment {
    public Ravenous() {
        super("Ravenous", GeneralUtils.axe, 7);
        this.max = 4;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!(user instanceof Player))
            return;
        Player playerAttacker = (Player)user;
        double random = Math.random();
        double chance = 0.05D * enchantLevel;
        if (random < chance && playerAttacker.getFoodLevel() < 20)
            playerAttacker.setFoodLevel(playerAttacker.getFoodLevel() + 1);
    }
}
