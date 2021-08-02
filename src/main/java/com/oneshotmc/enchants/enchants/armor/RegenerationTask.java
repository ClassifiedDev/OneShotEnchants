package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class RegenerationTask extends BukkitRunnable {
    Player p;

    long durationTicks;

    long delayTicks;

    double healValue;

    RegenerationTask(Player p, long durationTicks, long delayTicks, double healAmount) {
        this.p = p;
        this.durationTicks = durationTicks;
        this.delayTicks = delayTicks;
        this.healValue = healAmount;
    }

    public void run() {
        healPlayer(this.p, this.healValue);
    }

    private void healPlayer(Player p, double amount) {
        if (p.getHealth() == p.getMaxHealth() || p.isDead())
            return;
        GeneralUtils.healPlayer((LivingEntity)p, amount);
        //ParticleEffect.SPELL.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 15, this.p.getEyeLocation().add(0.0D, 0.5D, 0.0D), 100.0D);
    }
}
