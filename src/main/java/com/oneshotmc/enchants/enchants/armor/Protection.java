package com.oneshotmc.enchants.enchants.armor;

import com.google.common.collect.HashMultimap;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Protection extends CustomEnchantment {
    public Protection() {
        super("Protection", GeneralUtils.armor, 2);
        this.protectionPlayers = HashMultimap.create();
        this.max = 5;
        this.base = 10.0D;
        this.interval = 5.0D;
        for (int i = 1; i <= this.max; i++) {
            final int level = i;
            BukkitTask existing = protectionTasks.get(Integer.valueOf(i));
            if (existing != null)
                existing.cancel();
            final int radius = level * 2;
            BukkitTask task = (new BukkitRunnable() {
                public void run() {
                    Collection<Player> leveledPlayers = Protection.this.protectionPlayers.get(Integer.valueOf(level));
                    if (leveledPlayers == null || leveledPlayers.isEmpty())
                        return;
                    Iterator<Entity> iterator = null;
                    leveledPlayers.forEach(player -> {
                        int amountToHeal = 0;
                        int val$level = level;
                        int val$radius = radius;
                        if (player.isValid() && !player.isDead() && !player.hasMetadata("spectator") && !player.hasMetadata("in_duel")) {
                            FPlayer fplayer = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)player);
                            amountToHeal = (val$level > 4) ? 2 : 1;
                            player.getNearbyEntities(val$radius, val$radius, val$radius).iterator();
                            while (iterator.hasNext()) {
                                Entity ent = iterator.next();
                                if (ent instanceof Player) {
                                    Player p = (Player)ent;
                                    FPlayer fp = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)p);
                                    if (fp != null && fp.getFaction() != null && fp.getFaction().getRelationTo((RelationParticipator)fplayer.getFaction()).isAtLeast(Relation.ALLY)) {
                                        if (p.getHealth() + amountToHeal < p.getMaxHealth() && p.getHealth() > 0.0D && !p.isDead()) {
                                            p.setHealth(p.getHealth() + amountToHeal);
                                            //ParticleEffect.REDSTONE.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, p.getLocation(), 100.0D);
                                        }
                                        if (val$level >= 1 && Math.random() <= 0.15D) {
                                            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, val$level * 20, (val$level > 2) ? 1 : 0));
                                            //ParticleEffect.SPELL.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 10, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                                        }
                                        if (val$level >= 2 && Math.random() <= 0.1D) {
                                            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, val$level * 20 * 2, val$level + 1));
                                            //ParticleEffect.SPELL.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                                        }
                                        if (val$level >= 3 && Math.random() <= 0.085D) {
                                            p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, val$level * 20 * 2, val$level + 1));
                                            //ParticleEffect.SPELL.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 30, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }).runTaskTimer(OneShotEnchants.getInstance(), (40 * 5 / level), (40 * 5 / level));
            protectionTasks.put(Integer.valueOf(i), task);
        }
    }

    public void applyEquipEffect(Player player, int level) {
        player.setMetadata(name() + "_enchant", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
        this.protectionPlayers.put(Integer.valueOf(level), player);
    }

    public void applyUnequipEffect(Player player, int level) {
        player.removeMetadata(name() + "_enchant", OneShotEnchants.getInstance());
        this.protectionPlayers.remove(Integer.valueOf(level), player);
    }

    private static HashMap<Integer, BukkitTask> protectionTasks = new HashMap<>();

    private HashMultimap<Integer, Player> protectionPlayers;
}
