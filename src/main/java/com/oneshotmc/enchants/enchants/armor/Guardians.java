package com.oneshotmc.enchants.enchants.armor;

import com.gmail.nossr50.mcMMO;
import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchants.armor.GuardiansListener;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Guardians extends CustomEnchantment {
    public Guardians() {
        super("Guardians", GeneralUtils.armor, 2);
        this.max = 10;
        this.base = 20.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new GuardiansListener(), OneShotEnchants.getInstance());
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent)e).getDamager() instanceof Player) {
            if (player.hasMetadata("guardianEvent") && System.currentTimeMillis() < ((MetadataValue)player.getMetadata("guardianEvent").get(0)).asLong())
                return;
            if (player.hasMetadata("inDungeonParkour"))
                return;
            boolean duel = (player.getWorld().getName().equals("world_duels") || player.getWorld().getName().equals("world_duels2"));
            double chance = 0.01D + level * 0.05D;
            if (chance > 0.02D)
                chance = 0.02D;
            if (duel)
                chance /= 2.0D;
            if (Math.random() < chance) {
                player.setMetadata("guardianEvent", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis() + 10000L)));
                int guardian_count = level / 10 + 1;
                if (duel)
                    guardian_count = 1;
                Player p = (Player)player;
                Location spawnLoc = player.getLocation().add(0.0D, 2.0D, 0.0D);
                //ParticleEffect.SPELL.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.6F, 45, spawnLoc, 100.0D);
                while (guardian_count > 0) {
                    guardian_count--;
                    if ((spawnLoc.getChunk().getEntities()).length < 100) {
                        IronGolem ig = spawnGuardianEntity(spawnLoc, p, level);
                        ig.setTarget(attacker);
                    }
                }
            }
        }
    }

    public static IronGolem spawnGuardianEntity(Location spawnLocation, Player summoner, int level) {
        IronGolem ig = (IronGolem)spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.IRON_GOLEM);
        ig.setMetadata("guardianSummoner", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), summoner.getName()));
        ig.setMetadata("no_loot", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Boolean.valueOf(true)));
        ig.setMetadata("mcMMO: Spawned Entity", (MetadataValue)mcMMO.metadataValue);
        ig.setCanPickupItems(false);
        ig.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + summoner.getName() + "'s Guardian");
        ig.setCustomNameVisible(true);
        double customHp = (50 + level * 10);
        ig.setMaxHealth(customHp);
        ig.setHealth(customHp);
        ig.getWorld().playSound(ig.getLocation(), Sound.IRONGOLEM_DEATH, 1.0F, 0.55F);
        ig.setPlayerCreated(false);
        ig.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2147483647, 0));
        if (level >= 4)
            ig.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2147483647, 0));
        if (level >= 6)
            ig.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, 0));
        if (level >= 8)
            ig.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 0));
        if (level == 10)
            ig.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2147483647, 0));
        final IronGolem golem = ig;
        int target_task_id = (new BukkitRunnable() {
            public void run() {
                if (!golem.isDead()) {
                    Player ptarg = Guardians.getClosestPlayer((Entity)golem);
                    if (ptarg != null)
                        golem.setTarget((LivingEntity)ptarg);
                } else {
                    cancel();
                    if (golem.hasMetadata("targetTaskID"))
                        golem.removeMetadata("targetTaskID", OneShotEnchants.getInstance());
                }
            }
        }).runTaskTimer(OneShotEnchants.getInstance(), 20L, 20L).getTaskId();
        ig.setMetadata("targetTaskID", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(target_task_id)));
        final IronGolem igf = ig;
        Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
            public void run() {
                igf.damage(igf.getHealth());
                if (!igf.isDead())
                    igf.remove();
            }
        },  600L);
        return ig;
    }

    private static Player getClosestPlayer(Entity boss) {
        if (!boss.hasMetadata("guardianSummoner"))
            return null;
        String summoner = ((MetadataValue)boss.getMetadata("guardianSummoner").get(0)).asString();
        Player pSummoner = Bukkit.getPlayer(summoner);
        if (pSummoner != null)
            for (Entity ent : pSummoner.getNearbyEntities(9.0D, 9.0D, 9.0D)) {
                if (!(ent instanceof Player) || ent.hasMetadata("spectator") || ((Player)ent).getName().equals(summoner) ||
                        GeneralUtils.isAtleastTruce(pSummoner, (Player)ent))
                    continue;
                return (Player)ent;
            }
        return null;
    }
}
