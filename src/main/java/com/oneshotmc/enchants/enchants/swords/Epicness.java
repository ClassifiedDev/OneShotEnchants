package com.oneshotmc.enchants.enchants.swords;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import java.util.Random;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Epicness extends CustomEnchantment {
    public Epicness() {
        super("Epicness", GeneralUtils.swords, 2);
        this.max = 3;
        this.base = 10.0D;
        this.interval = 10.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random();
        double chance = 0.5D;
        if (random < chance)
            if (level == 1) {
                playSoundToEnemies(Sound.CHICKEN_HURT, 1.0F, 0.85F, playerHit);
                //ParticleEffect.CLOUD.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 20, playerHit.getEyeLocation(), 100.0D);
            } else if (level == 2) {
                playSoundToEnemies(Sound.MAGMACUBE_WALK2, 1.2F, 0.8F, playerHit);
                //ParticleEffect.SLIME.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 20, playerHit.getEyeLocation(), 100.0D);
            } else if (level == 3) {
                playSoundToEnemies(Sound.GHAST_SCREAM, 1.0F, 1.25F, playerHit);
                //ParticleEffect.SPELL_WITCH.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 15, playerHit.getEyeLocation(), 100.0D);
            }
    }

    private void playSoundToEnemies(Sound s, float volume, float pitch, LivingEntity le) {
        if (!(le instanceof Player))
            return;
        Player p = (Player)le;
        FPlayer fp = FPlayers.getInstance().getByPlayer(p);
        for (Entity ent : p.getNearbyEntities(16.0D, 16.0D, 16.0D)) {
            if (ent instanceof Player) {
                Player pEnt = (Player)ent;
                FPlayer fpEnt = FPlayers.getInstance().getByPlayer(pEnt);
                if (!fp.getFaction().getRelationTo((RelationParticipator)fpEnt.getFaction()).isAtLeast(Relation.TRUCE))
                    pEnt.playSound(pEnt.getLocation(), s, volume, pitch);
            }
        }
    }
}
