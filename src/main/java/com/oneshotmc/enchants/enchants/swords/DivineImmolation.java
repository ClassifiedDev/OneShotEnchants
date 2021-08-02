package com.oneshotmc.enchants.enchants.swords;

import java.util.List;
import java.util.Random;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.MinecraftServerUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DivineImmolation extends CustomEnchantment {
    public DivineImmolation() {
        super("Divine Immolation", GeneralUtils.swords, 2);
        this.max = 4;
        this.base = 10.0D;
        this.interval = 10.0D;
        Bukkit.getPluginManager().registerEvents(new DivineImmolationListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        Player p = (Player)player;
        double radius = 1.0D * level;
        double damage = Math.floor(level * 1.15D);
        if (!GeneralUtils.isSword(p.getItemInHand()))
            return;
        if (p.hasMetadata("lastItemHeldChange") && MinecraftServerUtils.getCurrentServerTick() - ((MetadataValue)p.getMetadata("lastItemHeldChange").get(0)).asLong() <= 5L)
            return;
        if (p.hasMetadata("soulTrap") && ((MetadataValue)p.getMetadata("soulTrap").get(0)).asLong() > System.currentTimeMillis())
            return;
        if (CrystalAPI.hasCrystalMode(p) && CrystalAPI.getAllCrystals(p) > 0) {
            if (!p.hasMetadata("lastCrystalRemoveEvent") || System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastCrystalRemoveEvent").get(0)).asLong() > 1000L)
                CrystalAPI.removeCyrstalsFromGems(p, 20);
            List<Entity> ents = Utils.getNearbyPlayers(p, radius);
            ents.add(playerHit);
            for (Entity ent : ents) {
                if (ent instanceof LivingEntity) {
                    Player pEnt = null;
                    if (ent instanceof Player)
                        pEnt = (Player)ent;
                    if ((pEnt != null && p.getName().equals(pEnt.getName())) || (pEnt != null &&
                            GeneralUtils.isAtleastTruce(p, pEnt)) || (pEnt != null && pEnt
                            .getGameMode() != GameMode.SURVIVAL) || (pEnt != null &&
                            !p.canSee(pEnt)) ||
                            WorldGuardUtils.isPvPDisabled(ent.getLocation()) || ent
                            .hasMetadata("spectator"))
                        continue;
                    LivingEntity le = (LivingEntity)ent;
                    le.damage(damage);
                    //ParticleEffect.FLAME.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.15F, 20, le.getEyeLocation(), 100.0D);
                    //ParticleEffect.LAVA.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.5F, 20, le.getEyeLocation(), 100.0D);
                    le.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int)(damage * 20.0D), 1));
                    if (le instanceof Player) {
                        ((Player)le).playSound(le.getLocation(), Sound.FIREWORK_BLAST, 1.0F, 0.3F);
                        ((Player)le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.8F, 0.5F);
                    }
                    ent.setMetadata("divineFire", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
                    ent.setMetadata("divineFireLevel", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
                }
            }
        }
    }
}
