package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Marksman extends CustomEnchantment {
    public Marksman() {
        super("Marksman", GeneralUtils.armor, 4);
        this.max = 4;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            int marksmanLevel = 0;
            for (ItemStack i : player.getEquipment().getArmorContents()) {
                if (i != null) {
                    Map<CustomEnchantment, Integer> enchants = OneShotEnchants.getEnchantments(i);
                    if (enchants.containsKey(this))
                        marksmanLevel += ((Integer)enchants.get(this)).intValue();
                }
            }
            if (marksmanLevel > 0) {
                double mod = 1.0D + marksmanLevel * 0.015625D;
                e.setDamage(e.getDamage() * mod);
            }
        }
    }
}
