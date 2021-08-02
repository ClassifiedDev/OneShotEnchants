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
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class SelfDestruct extends CustomEnchantment {
    public SelfDestruct() {
        super("Self Destruct", GeneralUtils.armor, 3);
        this.max = 3;
        this.base = 25.0D;
        this.interval = 10.0D;
        OneShotEnchants.getInstance().getServer().getPluginManager().registerEvents(new SelfDestructListener(), OneShotEnchants.getInstance());
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            Player p = (Player)player;
            double dmg = e.getDamage();
            dmg *= ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0D) {
                if (p.hasMetadata("SelfDestructing") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("SelfDestructing").get(0)).asLong() <= 10000L)
                    return;
                p.setMetadata("SelfDestructing", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
                int radius = (int)(level * 2.5F);
                FPlayer fp = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)p);
                List<LivingEntity> victims = new ArrayList<>();
                for (Entity ent : player.getNearbyEntities(radius, radius, radius)) {
                    if (ent instanceof LivingEntity) {
                        if (ent instanceof Player) {
                            Player pEnt = (Player)ent;
                            FPlayer fpEnt = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)pEnt);
                            if (fpEnt.getRelationTo((RelationParticipator)fp).isAtLeast(Relation.ALLY))
                                continue;
                        }
                        victims.add((LivingEntity)ent);
                    }
                }
                p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 2.0F, 0.75F);
                for (LivingEntity victim : victims) {
                    if (victim instanceof Player)
                        ((Player)victim).sendMessage(Utils.color("&c&l(!) &c" + p.getName() + "'s Self-Destruct was activated, RUN!"));
                }
                int tntAmount = radius;
                int fuseTicks = 120 - level * 20;
                while (tntAmount > 0) {
                    tntAmount--;
                    spawnExplosion(p, getNearbyLocation(player.getLocation(), 3, 1), fuseTicks);
                }
            }
        }
    }

    private void spawnExplosion(Player exploder, Location l, int fuseTicks) {
        if (l.getBlock().getType().isSolid())
            return;
        TNTPrimed tnt = (TNTPrimed)l.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
        tnt.setFuseTicks(fuseTicks);
        tnt.setMetadata("SelfDestructTNT", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), exploder.getName()));
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
