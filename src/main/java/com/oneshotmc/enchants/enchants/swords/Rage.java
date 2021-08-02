package com.oneshotmc.enchants.enchants.swords;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Rage extends CustomEnchantment {
    public Rage() {
        super("Rage", GeneralUtils.weapons, 2);
        this.max = 6;
        this.base = 10.0D;
        this.interval = 8.0D;
        Bukkit.getPluginManager().registerEvents(new RageListener(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0D && user instanceof Player) {
            Player pUser = (Player)user;
            if (!pUser.getItemInHand().getType().name().toLowerCase().contains("_sword") || !(event.getDamager() instanceof LivingEntity))
                return;
            if (target.hasMetadata("doubledStriked") && System.currentTimeMillis() - ((MetadataValue)target.getMetadata("doubleStriked").get(0)).asLong() < 500L)
                return;
            if (user.hasMetadata("noRageUntil") && ((MetadataValue)user.getMetadata("noRageUntil").get(0)).asLong() > System.currentTimeMillis())
                return;
            double mod = 0.05D * enchantLevel;
            double hitCombo = ((target instanceof Player) ? getPVPHitCombo((Player)user) : getPVEHitCombo((Player)user)) * mod + 1.0D;
            if (hitCombo > 2.5D)
                hitCombo = 2.5D;
            if (hitCombo > 1.0D && !GeneralUtils.isEffectedByDevour(target)) {
                playRageEffect(target, user);
                event.setDamage(event.getDamage() * hitCombo);
                target.setMetadata("effectedByRage", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
            }
            if (target instanceof Player) {
                addPVPHitCombo((Player)user);
            } else {
                addPVEHitCombo((Player)user);
            }
        }
    }

    private static int getPVPHitCombo(Player p) {
        return p.hasMetadata("pvp_rageHitCombo") ? ((MetadataValue)p.getMetadata("pvp_rageHitCombo").get(0)).asInt() : 0;
    }

    private void addPVPHitCombo(Player p) {
        p.setMetadata("pvp_rageHitCombo", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(getPVPHitCombo(p) + 1)));
    }

    public static void subtractPVPHitCombo(Player p) {
        p.setMetadata("pvp_rageHitCombo", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(Math.max(0, getPVPHitCombo(p) - 1))));
    }

    private static int getPVEHitCombo(Player p) {
        return p.hasMetadata("pve_rageHitCombo") ? ((MetadataValue)p.getMetadata("pve_rageHitCombo").get(0)).asInt() : 0;
    }

    private void addPVEHitCombo(Player p) {
        p.setMetadata("pve_rageHitCombo", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(getPVEHitCombo(p) + 1)));
    }

    public static void subtractPVEHitCombo(Player p) {
        p.setMetadata("pve_rageHitCombo", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(Math.max(0, getPVEHitCombo(p) - 1))));
    }

    private void playRageEffect(LivingEntity target, LivingEntity attacker) {
        //ParticleEffect.BLOCK_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.BlockData(Material.REDSTONE_BLOCK, (byte)0), 0.3F, 1.3F, 0.3F, 1.0F, 50, target.getLocation(), 75.0D);
        if (attacker instanceof Player) {
            Player p = (Player)attacker;
            p.playEffect(target.getLocation(), Effect.STEP_SOUND, 152);
        }
    }
}
