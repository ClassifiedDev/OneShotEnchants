package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class CorruptDamageTask extends BukkitRunnable {
    Player p;

    long durationTicks;

    double dmgValue;

    CorruptDamageTask(Player p, long durationTicks, double dmgAmount) {
        this.p = p;
        this.durationTicks = durationTicks;
        this.dmgValue = dmgAmount;
    }

    public void run() {
        dmgPlayer(this.p, this.dmgValue);
    }

    private void dmgPlayer(Player p, double amount) {
        if (p.getHealth() <= 0.0D || p.isDead())
            return;
        p.damage(amount);
        //ParticleEffect.REDSTONE.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 25, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
    }
}
