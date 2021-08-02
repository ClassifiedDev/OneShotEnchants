package com.oneshotmc.enchants.enchants.armor;

import java.util.HashSet;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UndeadRuse extends CustomEnchantment {
    public UndeadRuse() {
        super("Undead Ruse", GeneralUtils.armor, 2);
        this.max = 10;
        this.base = 20.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new UndeadRuseListener(), OneShotEnchants.getInstance());
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent)e).getDamager() instanceof Player) {
            double chance = 0.01D * level;
            if (chance > 0.04D)
                chance = 0.04D;
            if (Math.random() < chance) {
                int zombie_count = (int)(Math.random() / 2.0D * level) + 1;
                final Player p = (Player)player;
                Location spawnLoc = p.getTargetBlock((HashSet<Byte>) null, 5).getLocation().add(0.0D, 1.0D, 0.0D);
                //ParticleEffect.SPELL_WITCH.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, p.getLocation(), 100.0D);
                while (zombie_count > 0) {
                    zombie_count--;
                    if ((spawnLoc.getChunk().getEntities()).length < 50) {
                        Zombie z = (Zombie)player.getWorld().spawnEntity(spawnLoc, EntityType.ZOMBIE);
                        z.setMetadata("undeadruseSummoner", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), p.getName()));
                        z.setCustomName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + p.getName() + "'s Undead Minion");
                        z.setCustomNameVisible(true);
                        z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, (level > 6) ? 2 : ((level > 3) ? 1 : 0)));
                        if (level > 4)
                            z.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, 2));
                        if (level <= 7)
                            continue;
                        z.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2147483647, 2));
                    }
                }
                if (attacker instanceof Player) {
                    final Player pAttacker = (Player)attacker;
                    pAttacker.hidePlayer(p);
                    //ParticleEffect.SPELL_WITCH.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.75F, 35, p.getLocation(), 100.0D);
                    Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
                        public void run() {
                            pAttacker.showPlayer(p);
                            //ParticleEffect.SPELL_WITCH.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.75F, 35, p.getLocation(), 100.0D);
                        }
                    }, zombie_count * 20L + 20L);
                }
            }
        }
    }
}
