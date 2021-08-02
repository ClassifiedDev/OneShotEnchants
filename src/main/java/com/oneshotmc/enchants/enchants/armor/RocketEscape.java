package com.oneshotmc.enchants.enchants.armor;

import java.util.HashMap;
import java.util.HashSet;
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
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class RocketEscape extends CustomEnchantment {
    public RocketEscape() {
        super("Rocket Escape", GeneralUtils.boots, 3);
        this.max = 3;
        this.base = 25.0D;
        this.interval = 5.0D;
        Bukkit.getServer().getPluginManager().registerEvents(new RocketEscapeListener(), OneShotEnchants.getInstance());
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            double dmg = e.getDamage();
            dmg *= ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue((Player)player));
            if (player.getWorld().getEnvironment() == World.Environment.THE_END)
                return;
            if (player.getHealth() - dmg <= 0.0D && canEscape((Player)player)) {
                Player pl = (Player)player;
                last_rocket_escape.put(pl.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis()));
                e.setCancelled(true);
                e.setDamage(0.0D);
                pl.sendMessage("");
                pl.sendMessage(Utils.color("&a&l(!) &aYour Rocket Escape boots have activated, recover while they last!"));
                pl.sendMessage("");
                pl.playSound(pl.getLocation(), Sound.EXPLODE, 1.0F, 0.54F);
                pl.setMetadata("no_fall_damage", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Boolean.valueOf(true)));
                pl.setVelocity(new Vector(0, (pl.getWorld().getName().equals("world_duels") || pl.getWorld().getName().equals("world_duels2")) ? (2 + level) : (4 + level * 2), 0));
                if (pl.hasPotionEffect(PotionEffectType.SLOW))
                    pl.removePotionEffect(PotionEffectType.SLOW);
                if (pl.hasPotionEffect(PotionEffectType.SLOW_DIGGING))
                    pl.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * (level + 2), level));
                final UUID uuid = player.getUniqueId();
                rocket_escapists.add(uuid);
                Location loc = pl.getLocation();
                ParticleEffect.CLOUD.display((float)Math.random() * 2.0F, (float)Math.random() * 2.0F, (float)Math.random() * 2.0F, 1.25F, 70, loc, 100.0D);
                Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                    public void run() {
                        if (Bukkit.getPlayer(uuid) != null)
                            ParticleEffect.CLOUD.display((float)Math.random() * 2.0F, (float)Math.random() * 2.0F, (float)Math.random() * 2.0F, 1.25F, 70, Bukkit.getPlayer(uuid).getLocation(), 100.0D);
                        RocketEscape.rocket_escapists.remove(uuid);
                    }
                }, (20 * (level + 2) + 5));
            }
        }
    }

    private boolean canEscape(Player pl) {
        return (!last_rocket_escape.containsKey(pl.getUniqueId().toString()) || System.currentTimeMillis() - ((Long)last_rocket_escape.get(pl.getUniqueId().toString())).longValue() > 30000L);
    }

    public static boolean isEscaping(Player pl) {
        return rocket_escapists.contains(pl.getUniqueId());
    }

    public static HashMap<String, Long> last_rocket_escape = new HashMap<>();

    public static HashSet<UUID> rocket_escapists = new HashSet<>();

    public class RocketEscapeListener implements Listener {
        @EventHandler
        public void onRocketEscapeFall(EntityDamageEvent e) {
            if (e instanceof Player) {
                Player p = ((Player)e).getPlayer();
                if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL) &&
                        p.hasMetadata("no_fall_damage"))
                    e.setDamage(0.0D);
            }
        }
    }
}
