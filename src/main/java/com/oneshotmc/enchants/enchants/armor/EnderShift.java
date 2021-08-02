package com.oneshotmc.enchants.enchants.armor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ArmorUtil;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnderShift extends CustomEnchantment {
    public EnderShift() {
        super("Ender Shift", GeneralUtils.helmets_and_boots, 3);
        this.max = 3;
        this.base = 25.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new EnderShiftEvents(), OneShotEnchants.getInstance());
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            double dmg = e.getDamage();
            dmg *= ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue((Player)player));
            if (player.getHealth() - dmg <= 0.0D && canEnderShift((Player)player)) {
                Player pl = (Player)player;
                last_ender_shift.put(pl.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis()));
                e.setCancelled(true);
                e.setDamage(0.0D);
                pl.sendMessage("");
                pl.sendMessage(Utils.color("&d&l(!) &dYou were about to die, so you have entered the Ender dimension, escape to safety!"));
                pl.sendMessage("");
                pl.playSound(pl.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 0.54F);
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * (level + 7), 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * (level + 7), level + 2), true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * (level + 7), level - 1), true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * (level + 7), level + 2));
                final String uuid = player.getUniqueId().toString();
                ender_shifters.add(uuid);
                Location loc = pl.getLocation();
                //ParticleEffect.SPELL_WITCH.display((float)Math.random() * 2.0F, (float)Math.random() * 2.0F, (float)Math.random() * 2.0F, 1.25F, 100, Bukkit.getPlayer(UUID.fromString(uuid)).getLocation(), 100.0D);
                Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                    public void run() {
                        if (Bukkit.getPlayer(UUID.fromString(uuid)) != null)
                            //ParticleEffect.SPELL_WITCH.display((float)Math.random() * 2.0F, (float)Math.random() * 2.0F, (float)Math.random() * 2.0F, 1.25F, 100, Bukkit.getPlayer(UUID.fromString(uuid)).getLocation(), 100.0D);
                        EnderShift.ender_shifters.remove(uuid);
                    }
                }, (20 * (level + 7) + 10));
            }
        }
    }

    private boolean canEnderShift(Player pl) {
        return (!last_ender_shift.containsKey(pl.getUniqueId().toString()) || System.currentTimeMillis() - ((Long)last_ender_shift.get(pl.getUniqueId().toString())).longValue() > 30000L);
    }

    private static boolean isEnderShifting(Player pl) {
        return ender_shifters.contains(pl.getUniqueId().toString());
    }

    protected static HashMap<String, Long> last_ender_shift = new HashMap<>();

    protected static List<String> ender_shifters = new ArrayList<>();

    private static class EnderShiftEvents implements Listener {
        private EnderShiftEvents() {}

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onPlayerShootBow(EntityShootBowEvent e) {
            if (e.getEntity() instanceof Player && EnderShift.isEnderShifting((Player)e.getEntity())) {
                e.setCancelled(true);
                ((Player)e.getEntity()).playSound(e.getEntity().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.54F);
            }
        }

        @EventHandler(priority = EventPriority.LOWEST)
        public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player && EnderShift.isEnderShifting((Player)e.getDamager())) {
                e.setCancelled(true);
                e.setDamage(0.0D);
                ((Player)e.getDamager()).playSound(e.getDamager().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 0.54F);
            }
        }
    }
}
