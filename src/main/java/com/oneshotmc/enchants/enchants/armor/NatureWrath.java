package com.oneshotmc.enchants.enchants.armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NatureWrath extends CustomEnchantment {
    int soulCost;

    public NatureWrath() {
        super("Nature Wrath", GeneralUtils.armor, 2);
        this.max = 4;
        this.base = 15.0D;
        this.interval = 3.0D;
        this.soulCost = 75;
        Bukkit.getPluginManager().registerEvents(new NatureWrathListener(), OneShotEnchants.getInstance());
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)
            return;
        double random = Math.random();
        double chance = 0.002D * level;
        if (random < chance) {
            Player p = (Player)player;
            if (p.hasMetadata("soulTrap") && ((MetadataValue)p.getMetadata("soulTrap").get(0)).asLong() > System.currentTimeMillis())
                return;
            int cost = this.soulCost;
            if (CrystalAPI.hasCrystalAmount(p, cost)) {
                CrystalAPI.removeCyrstalsFromGems(p, cost);
                p.sendMessage("");
                p.sendMessage(Utils.color("&2&lNATURE'S WRATH "));
                        p.sendMessage(Utils.color("&c- " + cost + " Crystals"));
                p.sendMessage(Utils.color("&8&l&7You have &n" + CrystalAPI.getAllCrystals(p) + "&r&7 crystals left."));
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 0.65F);
                p.sendMessage("");
                procNaturesWrath(p, attacker, level, e, 1.0D);
            } else {
                //ParticleEffect.LAVA.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.4F, 20, p.getEyeLocation(), 100.0D);
                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 0.7F, 0.4F);
                p.sendMessage(Utils.color("&c&lOUT OF SOULS "));
            }
        }
    }

    public int procNaturesWrath(Player playerWhoProced, LivingEntity affected, int level, EntityDamageEvent e, double durationModifier) {
        int radius = 8 + level * 5;
        List<String> affectedPlayers = new ArrayList<>();
        for (Entity ent : playerWhoProced.getNearbyEntities(radius, radius, radius)) {
            if (WorldGuardUtils.isPvPDisabled(ent.getLocation()) || ent
                    .hasMetadata("spectator") || ent instanceof org.bukkit.entity.EnderCrystal)
                continue;
            if (ent instanceof LivingEntity && !(ent instanceof Player) && !ent.hasMetadata("do_not_clear")) {
                ent.getWorld().strikeLightningEffect(ent.getLocation());
                //ParticleEffect.EXPLOSION_LARGE.display(0.0F, 0.0F, 0.0F, 0.6F, 10, ent.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                //ParticleEffect.SPELL.display(0.0F, 0.0F, 0.0F, 0.4F, 35, ent.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                ent.remove();
                continue;
            }
            if (ent instanceof Player) {
                Player pEnt = (Player)ent;
                if (GeneralUtils.isAtleastTruce(playerWhoProced, pEnt) || pEnt.hasMetadata("natureWrathTask") || pEnt.getGameMode() != GameMode.SURVIVAL)
                    continue;
                boolean freeze = true;
                affectedPlayers.add(pEnt.getName());
                pEnt.getWorld().strikeLightningEffect(pEnt.getLocation());
                if (freeze) {
                    pEnt.setWalkSpeed(0.0F);
                    pEnt.removePotionEffect(PotionEffectType.JUMP);
                    pEnt.removePotionEffect(PotionEffectType.SLOW);
                    pEnt.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (7 + level) * 20, 128));
                    pEnt.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (7 + level) * 20, 128));
                    pEnt.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (7 + level) * 20, 2));
                }
                pEnt.playSound(pEnt.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 2.0F);
                NatureWrathTask nwt = new NatureWrathTask(pEnt, level);
                nwt.runTaskTimer(OneShotEnchants.getInstance(), 20L, 20L);
                pEnt.setMetadata("lastNatureWrathEffect", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
                pEnt.setMetadata("natureWrathTask", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), nwt));
            }
        }
        final String[] players = affectedPlayers.<String>toArray(new String[affectedPlayers.size()]);
        Bukkit.getScheduler().runTaskLater(OneShotEnchants.getInstance(), new Runnable() {
            public void run() {
                for (String s : players) {
                    if (Bukkit.getPlayer(s) != null) {
                        Player p = Bukkit.getPlayer(s);
                        if (p.hasMetadata("natureWrathTask")) {
                            NatureWrathTask nwt = (NatureWrathTask)((MetadataValue)p.getMetadata("natureWrathTask").get(0)).value();
                            nwt.cancel();
                            p.setWalkSpeed(0.2F);
                            p.removeMetadata("natureWrathTask", OneShotEnchants.getInstance());
                        }
                    }
                }
            }
        }, (long)(((7 + level) * 20L) * durationModifier));
        return affectedPlayers.size();
    }
}
