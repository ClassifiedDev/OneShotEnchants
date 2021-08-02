package com.oneshotmc.enchants.enchants.swords;

import java.util.HashSet;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ExpUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SkillSwipe extends CustomEnchantment {
    public SkillSwipe() {
        super("Skill Swipe", GeneralUtils.swords, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (target instanceof Player) {
            double random = Math.random();
            double chance = 0.2D * enchantLevel;
            int amountToSteal = enchantLevel * 6;
            int targetXp = ExpUtils.getTotalExperience((Player)target);
            if (targetXp - amountToSteal < 0)
                amountToSteal = targetXp;
            if (amountToSteal <= 0)
                return;
            if (random < chance) {
                ExpUtils.setTotalExperience((Player)target, targetXp - amountToSteal);
                ExpUtils.setTotalExperience((Player)user, ExpUtils.getTotalExperience((Player)user) + amountToSteal);
                user.getWorld().spawnEntity(user.getLocation(), EntityType.EXPERIENCE_ORB);
            }
        }
    }
}
