package com.oneshotmc.enchants.enchants.armor;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class Shockwave extends CustomEnchantment {
    public Shockwave() {
        super("Shockwave", GeneralUtils.chestplates, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double chance = level * 0.02D;
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && Math.random() < chance) {
            FPlayer fp = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)player);
            for (Entity ent : player.getNearbyEntities(level, level, level)) {
                if (ent instanceof LivingEntity) {
                    if (ent instanceof org.bukkit.entity.Player) {
                        FPlayer fpEnt = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)ent);
                        if (fpEnt.getRelationTo((RelationParticipator)fp).isAtLeast(Relation.ALLY))
                            continue;
                    }
                    if (WorldGuardUtils.isPvPDisabled(ent.getLocation()))
                        continue;
                    GeneralUtils.pushAwayEntity(player, ent, 1.7000000476837158D);
                    Location l = ent.getLocation();
                    //ParticleEffect.ENCHANTMENT_TABLE.display((float)Math.random() * 2.0F, (float)Math.random() * 2.0F, (float)Math.random() * 2.0F, 0.7F, 100, l, 100.0D);
                }
            }
            player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1.0F, 0.1F);
            //ParticleEffect.ENCHANTMENT_TABLE.display((float)Math.random() * 3.0F, (float)Math.random() * 3.0F, (float)Math.random() * 3.0F, 0.7F, 100, player.getEyeLocation(), 100.0D);
        }
    }

    private void pushAwayEntity(LivingEntity center, Entity entity, double speed) {
        Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
