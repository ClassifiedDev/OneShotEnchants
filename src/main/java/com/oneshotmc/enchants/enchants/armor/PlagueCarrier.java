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
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlagueCarrier extends CustomEnchantment {
    public PlagueCarrier() {
        super("Plague Carrier", GeneralUtils.leggings, 3);
        this.max = 8;
        this.base = 25.0D;
        this.interval = 10.0D;
        OneShotEnchants.getInstance().getServer().getPluginManager().registerEvents(new PlagueCarrierListener(), OneShotEnchants.getInstance());
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            Player p = (Player)player;
            double dmg = e.getDamage();
            dmg *= ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0D) {
                if (p.hasMetadata("PlagueCarrier") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("PlagueCarrier").get(0)).asLong() <= 10000L)
                    return;
                p.setMetadata("PlagueCarrier", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
                int radius = Math.round(level * 1.5F);
                List<LivingEntity> victims = new ArrayList<>();
                FPlayer fp = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)p);
                for (Entity ent : player.getNearbyEntities(radius, radius, radius)) {
                    if (ent instanceof LivingEntity) {
                        if (ent instanceof Player) {
                            Player pEnt = (Player)ent;
                            FPlayer fpEnt = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)pEnt);
                            if (fpEnt.getRelationTo((RelationParticipator)fp).isAtLeast(Relation.TRUCE))
                                continue;
                        }
                        victims.add((LivingEntity)ent);
                    }
                }
                //ParticleEffect.CLOUD.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 75, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                p.getWorld().playSound(p.getLocation(), Sound.CREEPER_HISS, 2.0F, 0.75F);
                for (LivingEntity victim : victims) {
                    if (victim instanceof Player) {
                        ((Player)victim).sendMessage(Utils.color("&d&l(!) &d" + p.getName() + " has died, and was a Plague Carrier! RUN!"));
                        ((Player)victim).getWorld().playSound(victim.getLocation(), Sound.CREEPER_HISS, 1.6F, 0.75F);
                        ((Player)victim).addPotionEffect(new PotionEffect(PotionEffectType.POISON, (2 + level) * 20, (level >= 7) ? 1 : 0));
                    }
                }
                int plagueCarriers = radius / 2;
                while (plagueCarriers > 0) {
                    plagueCarriers--;
                    spawnPlagueCreeper(p.getLocation().add(0.0D, 1.0D, 0.0D), level);
                }
                if (p.getHealth() > 0.0D)
                    p.damage(p.getHealth());
            }
        }
    }

    private void spawnPlagueCreeper(Location l, int level) {
        Creeper c = (Creeper)l.getWorld().spawnEntity(l, EntityType.CREEPER);
        c.setMetadata("plagueCarrier", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
        c.setCustomName(Utils.color("&dPlague Carrier"));
        c.setCanPickupItems(false);
        c.setMetadata("no_loot", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Boolean.valueOf(true)));
        c.setCustomNameVisible(true);
        c.setPowered(true);
        //ParticleEffect.CLOUD.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, c.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
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
