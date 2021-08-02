package com.oneshotmc.enchants.enchants.armor;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ArmorUtil;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SmokeBomb extends CustomEnchantment {
    public SmokeBomb() {
        super("Smoke Bomb", GeneralUtils.helmets, 3);
        this.max = 8;
        this.base = 25.0D;
        this.interval = 10.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            Player p = (Player)player;
            double dmg = e.getDamage();
            dmg *= ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0D) {
                if (p.hasMetadata("SmokeBomb") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("SmokeBomb").get(0)).asLong() <= 15000L)
                    return;
                p.setMetadata("SmokeBomb", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
                int radius = Math.round(level * 1.5F);
                List<LivingEntity> victims = new ArrayList<>();
                FPlayer fp = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)p);
                for (Entity ent : player.getNearbyEntities(radius, radius, radius)) {
                    if (ent instanceof LivingEntity) {
                        if (ent instanceof Player) {
                            FPlayer fpEnt = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)ent);
                            if (fp.getRelationTo((RelationParticipator)fpEnt).isAtLeast(Relation.TRUCE))
                                continue;
                        }
                        victims.add((LivingEntity)ent);
                    }
                }
                //ParticleEffect.CLOUD.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 75, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_TWINKLE2, 2.0F, 0.75F);
                for (LivingEntity victim : victims) {
                    if (victim instanceof Player) {
                        ((Player)victim).sendMessage(Utils.color("&c&l(!) &c" + p.getName() + " has thrown a Smoke Bomb in an attempt to escape!"));
                        ((Player)victim).getWorld().playSound(victim.getLocation(), Sound.FIREWORK_TWINKLE2, 1.6F, 0.75F);
                        ((Player)victim).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Math.max(level, 3) * 20, Math.min(level, 3)));
                        ((Player)victim).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Math.max(level, 3) * 20, Math.min(level, 3)));
                    }
                }
                e.setCancelled(true);
                e.setDamage(0.0D);
            }
        }
    }

    private Location getNearbyLocation(Location l, int radius, int y_boost) {
        Random rand = new Random();
        Location rand_loc = l.clone();
        rand_loc.add(((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 1)), 0.0D, ((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 1)));
        rand_loc.add(0.5D, y_boost, 0.5D);
        while ((rand_loc.getBlock().getType() != Material.AIR || rand_loc.getBlock().getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR) && rand_loc.getY() < 255.0D)
            rand_loc = rand_loc.add(0.0D, 1.0D, 0.0D);
        return rand_loc;
    }
}
