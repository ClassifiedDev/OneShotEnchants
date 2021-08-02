package com.oneshotmc.enchants.enchants.armor;

import java.util.ArrayList;
import java.util.List;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ArmorUtil;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Lifebloom extends CustomEnchantment {
    public Lifebloom() {
        super("Lifebloom", GeneralUtils.leggings, 3);
        this.max = 5;
        this.base = 25.0D;
        this.interval = 10.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            Player p = (Player)player;
            double dmg = e.getDamage();
            dmg *= ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0D) {
                if (p.hasMetadata("Lifeblooming") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("Lifeblooming").get(0)).asLong() <= 10000L)
                    return;
                p.setMetadata("Lifeblooming", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
                int radius = (int)(level * 2.5F);
                List<Player> allies = new ArrayList<>();
                for (Entity ent : player.getNearbyEntities(radius, radius, radius)) {
                    if (ent instanceof Player) {
                        Player pEnt = (Player)ent;
                        if (!isAlly(p, pEnt))
                            continue;
                        allies.add(pEnt);
                    }
                }
                p.getWorld().playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0F, 0.75F);
                //ParticleEffect.REDSTONE.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 1.4F, 65, p.getEyeLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                for (Player ally : allies) {
                    ally.sendMessage(Utils.color("&a&l(!) &a" + p.getName() + "'s Lifebloom was activated, fight on!"));
                    GeneralUtils.setPlayerHealth((LivingEntity)ally, ally.getMaxHealth());
                    ally.getWorld().playSound(ally.getLocation(), Sound.ORB_PICKUP, 1.5F, 0.75F);
                    if (level >= 4)
                        ally.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120, level - 4));
                    //ParticleEffect.VILLAGER_HAPPY.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 15, ally.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                }
            }
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }
}
