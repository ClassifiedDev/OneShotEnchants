package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.enchants.axes.BleedEvent;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

final class BleedTask extends BukkitRunnable {
    BleedEvent event;

    int x;

    public BleedTask(BleedEvent e) {
        this.x = 0;
        this.event = e;
    }

    public void run() {
        if (this.event.ticks <= this.x) {
            this.event.stop();
            return;
        }
        this.x++;
        if (this.event.player != null && this.event.player.hasMetadata("ce_bleeding")) {
            this.event.player.damage(this.event.dmg);
            this.event.player.playSound(this.event.player.getLocation(), Sound.HURT_FLESH, 1.0F, 0.75F);
        }
    }
}
