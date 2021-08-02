package com.oneshotmc.enchants.enchants.axes;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Pummel extends CustomEnchantment {
    public Pummel() {
        super("Pummel", GeneralUtils.axe, 7);
        this.max = 3;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random();
        double chance = 0.06D * level;
        double duration = level * 1.5D;
        int radius = level;
        if (random < chance) {
            FPlayer fplayer = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)player);
            for (Entity ent : playerHit.getNearbyEntities(radius, radius, radius)) {
                if (ent.equals(player))
                    continue;
                if (!(ent instanceof LivingEntity))
                    continue;
                LivingEntity le = (LivingEntity)ent;
                if (le instanceof Player) {
                    Player p = (Player)le;
                    if (p.hasMetadata("metaphysicalEnchant")) {
                        int metaphysicalLevel = ((MetadataValue)p.getMetadata("metaphysicalEnchant").get(0)).asInt();
                        chance -= metaphysicalLevel * 0.04D;
                        if (random > chance) {
                            p.sendMessage(Utils.color("&8&lMETAPHYSICAL (&8Pummel blocked!&8&l) "));
                            continue;
                        }
                    }
                    FPlayer fp = FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)p);
                    if (fplayer.getRelationTo((RelationParticipator)fp).isAtLeast(Relation.TRUCE))
                        continue;
                }
                le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)duration * 20, level - 1));
                Location l = playerHit.getLocation().add(0.0D, 0.5D, 0.0D);
                Material blockId = playerHit.getLocation().getBlock().getType();
                //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new //ParticleEffect.BlockData(blockId, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 30, l.getBlock().getLocation(), 75.0D);
            }
        }
    }
}
