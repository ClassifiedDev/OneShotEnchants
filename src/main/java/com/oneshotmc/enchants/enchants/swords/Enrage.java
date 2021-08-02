package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Enrage extends CustomEnchantment {
    public Enrage() {
        super("Enrage", GeneralUtils.swords, 5);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 6.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double hpPercent = user.getHealth() / user.getMaxHealth();
        double multi = 1.0D;
        if (hpPercent <= 0.75D)
            multi += 0.15D;
        if (hpPercent <= 0.5D)
            multi += 0.15D;
        if (hpPercent <= 0.25D)
            multi += 0.15D;
        event.setDamage(event.getDamage() * multi);
    }
}
