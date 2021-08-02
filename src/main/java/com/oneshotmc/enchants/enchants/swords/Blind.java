package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchants.armor.Clarity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blind extends CustomEnchantment {
    public Blind() {
        super("Blind", GeneralUtils.swords_and_bow, 5);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 6.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        int time = enchantLevel * 30;
        double random = Math.random();
        double chance = 0.1D * enchantLevel;
        if (random < chance) {
            if (target instanceof Player && !Clarity.canBlindnessApply((Player)target, enchantLevel - 1))
                return;
            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, enchantLevel - 1));
        }
    }

    public static String RECENTLY_BLINDED_METADATA = "recentlyBlinded";
}
