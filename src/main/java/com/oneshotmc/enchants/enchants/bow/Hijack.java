package com.oneshotmc.enchants.enchants.bow;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchants.armor.Guardians;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.MetadataValue;

public class Hijack extends CustomEnchantment {
    public Hijack() {
        super("Hijack", new Material[] { Material.BOW }, 2);
        this.max = 4;
        this.base = 10.0D;
        this.interval = 8.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)
            return;
        double random = Math.random();
        double chance = enchantLevel * 0.08D;
        if (random < chance) {
            Player pUser = (Player)user;
            if (target.getType() == EntityType.IRON_GOLEM && target.hasMetadata("guardianSummoner")) {
                String oldSummoner = ((MetadataValue)target.getMetadata("guardianSummoner").get(0)).asString();
                target.remove();
                IronGolem ig = Guardians.spawnGuardianEntity(target.getLocation().add(0.0D, 2.0D, 0.0D), pUser, enchantLevel * 2);
                //ParticleEffect.SPELL_WITCH.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 1.2F, 60, ig.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                for (Entity ent : ig.getNearbyEntities(24.0D, 24.0D, 24.0D)) {
                    if (ent instanceof Player) {
                        ((Player)ent).sendMessage(Utils.color("&5&lHIJACK (&7&m" + oldSummoner + "&5&l -> &f" + pUser.getName() + "&5&l) "));
                        ((Player)ent).playSound(ent.getLocation(), Sound.IRONGOLEM_DEATH, 0.8F, 1.2F);
                    }
                }
            }
        }
    }
}
