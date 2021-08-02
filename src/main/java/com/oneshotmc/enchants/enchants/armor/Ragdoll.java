package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class Ragdoll extends CustomEnchantment {
    public Ragdoll() {
        super("Ragdoll", GeneralUtils.armor, 3);
        this.max = 4;
        this.base = 25.0D;
        this.interval = 10.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player && attacker instanceof LivingEntity) {
            if (attacker instanceof org.bukkit.entity.IronGolem)
                return;
            if (player.getWorld().getEnvironment() == World.Environment.THE_END)
                return;
            Player p = (Player)player;
            if (Math.random() < level * 0.125D)
                GeneralUtils.pushAwayEntity(attacker, (Entity)p, 1.5D + 0.5D * level);
        }
    }

    private void pushAwayEntity(LivingEntity center, Entity entity, double speed) {
        Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
