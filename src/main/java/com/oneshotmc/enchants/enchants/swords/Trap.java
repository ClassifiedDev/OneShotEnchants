package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;

public class Trap extends CustomEnchantment {
    public Trap() {
        super("Trap", GeneralUtils.swords, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 3.0D;
        Bukkit.getPluginManager().registerEvents(new TrapEvents(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {
        if (!(target instanceof Player))
            return;
        if (((Player)player).getWalkSpeed() < 0.2F)
            return;
        double random = Math.random();
        double chance = 0.04D * level;
        if (random < chance) {
            final Player ptarget = (Player)target;
            if (target.hasMetadata("metaphysicalEnchant")) {
                int metaphysicalLevel = ((MetadataValue)target.getMetadata("metaphysicalEnchant").get(0)).asInt();
                chance -= metaphysicalLevel * 0.025D;
                if (random > chance) {
                    ptarget.sendMessage(Utils.color("&e&lMETAPHYSICAL &7(Trap blocked!) &e&l"));
                    return;
                }
            }
            if (target.hasMetadata("immune_freeze"))
                return;
            ptarget.setWalkSpeed(0.0F);
            ptarget.sendMessage(Utils.color("&c&l(!) &cYou have been trapped by " + ((Player)player).getName() + "!"));
            OneShotEnchants.getInstance().getServer().getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                public void run() {
                    ptarget.setWalkSpeed(0.2F);
                    ptarget.sendMessage(Utils.color("&a&l(!) &aYou are no longer trapped!"));
                }
            },  35L);
            Location l = ptarget.getLocation();
            ptarget.playSound(player.getLocation(), Sound.DIG_SNOW, 5.0F, 10.0F);
            //ParticleEffect.BLOCK_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.BlockData(Material.SNOW_BLOCK, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 25, l.add(0.0D, 1.0D, 0.0D), 100.0D);
        }
    }

    private static class TrapEvents implements Listener {
        private TrapEvents() {}

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent e) {
            Player pl = e.getPlayer();
            pl.setWalkSpeed(0.2F);
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onEntityDamage(EntityDamageEvent e) {
            if (e.getEntity() instanceof Player) {
                Player p = (Player)e.getEntity();
                if (p.getWalkSpeed() == 0.0F && !p.isOp()) {
                    Location l = p.getLocation();
                    p.playSound(p.getLocation(), Sound.DIG_SNOW, 5.0F, 10.0F);
                    ParticleEffect.BLOCK_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.BlockData(Material.SNOW_BLOCK, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 25, l.add(0.0D, 1.0D, 0.0D), 100.0D);
                }
            }
        }
    }
}
