package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class NatureWrathTask extends BukkitRunnable {
    Player victim;

    public int enchantLevel;

    public NatureWrathTask(Player p, int level) {
        this.victim = p;
        this.enchantLevel = level;
    }

    public void run() {
        if (this.victim != null && !this.victim.isDead()) {
            this.victim.getWorld().strikeLightningEffect(this.victim.getLocation());
            this.victim.damage(this.enchantLevel);
            this.victim.playSound(this.victim.getLocation(), Sound.GHAST_SCREAM2, 2.0F, 2.0F);
            this.victim.sendMessage(Utils.color("&2&lNATURE'S WRATH "));
                    //ParticleEffect.SPELL.display(0.0F, 0.0F, 0.0F, 0.4F, 35, this.victim.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
        }
    }
}
