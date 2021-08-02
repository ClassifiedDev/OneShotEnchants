package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.OneShotEnchants;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

final class BleedEvent {
    protected Player player;

    protected double dmg;

    protected int ticks;

    protected BleedTask bleedTask;

    BleedEvent(Player p, double damage_per_interval, int intervals) {
        this.player = p;
        this.dmg = damage_per_interval;
        this.ticks = intervals;
    }

    public void start() {
        (this.bleedTask = new BleedTask(this)).runTaskTimer(OneShotEnchants.getInstance(), 20L, 35L);
        this.player.setMetadata("ce_bleeding", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(this.bleedTask.getTaskId())));
    }

    public void stop() {
        this.player.removeMetadata("ce_bleeding", OneShotEnchants.getInstance());
        this.bleedTask.cancel();
    }
}
