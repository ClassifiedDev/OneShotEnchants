package com.oneshotmc.enchants.enchants.swords;

import java.util.concurrent.ThreadLocalRandom;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ArmorSlot;
import com.oneshotmc.enchants.utils.ArmorUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Demonforged extends CustomEnchantment {
    public Demonforged() {
        super("Demonforged", GeneralUtils.swords, 2);
        this.max = 4;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {
        if (!(target instanceof Player))
            return;
        double random = Math.random();
        double chance = 0.04D * level;
        if (random < chance)
            damageRandomArmor((Player)player);
    }

    private void damageRandomArmor(Player p) {
        ArmorSlot slot = ArmorSlot.getArmorSlotByIndex(ThreadLocalRandom.current().nextInt(4));
        if (slot != null)
            ArmorUtil.modifyPlayerArmor(p, slot, -1, true);
    }
}
