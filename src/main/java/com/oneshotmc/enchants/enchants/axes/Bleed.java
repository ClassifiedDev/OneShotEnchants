package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Bleed extends CustomEnchantment {
    public Bleed() {
        super("Bleed", GeneralUtils.axe, 2);
        this.max = 6;
        this.base = 10.0D;
        this.interval = 8.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0D && user instanceof Player) {
            double bleedChance = 0.08D * enchantLevel + 0.4D;
            if (Math.random() < bleedChance && getBleedStack(target) < getMaxBleedStack(enchantLevel))
                addBleedStack(user, target);
        }
    }

    private int getMaxBleedStack(int level) {
        return 20;
    }

    public static int getBleedStack(LivingEntity le) {
        return le.hasMetadata("bleedStack") ? ((MetadataValue)le.getMetadata("bleedStack").get(0)).asInt() : 0;
    }

    public static void clearBleedEffect(LivingEntity le) {
        le.removeMetadata("bleedStack", OneShotEnchants.getInstance());
        if (le instanceof Player) {
            ((Player)le).setWalkSpeed(0.2F);
        } else {
            le.removePotionEffect(PotionEffectType.SLOW);
        }
    }

    private void addBleedStack(LivingEntity leDamager, LivingEntity le) {
        le.setMetadata("bleedStack", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(getBleedStack(le) + 1)));
        int bleedStack = getBleedStack(le) / 2;
        if (le instanceof Player) {
            Player p = (Player)le;
            p.setWalkSpeed((float)(0.20000000298023224D - 0.005D * bleedStack));
            for (Entity ent : p.getNearbyEntities(7.0D, 7.0D, 7.0D)) {
                if (ent.getEntityId() == leDamager.getEntityId())
                    continue;
                if (!(ent instanceof Player) || !ent.hasMetadata("bloodLust"))
                    continue;
                Player pEnt = (Player)ent;
                if (!GeneralUtils.isAlly(p, pEnt))
                    continue;
                int bloodLustLevel = ((MetadataValue)pEnt.getMetadata("bloodLust").get(0)).asInt();
                if (Math.random() >= 0.2D + bloodLustLevel * 0.05D)
                    continue;
                double amountToHeal = Math.max(2.0D, bleedStack * 0.05D * bloodLustLevel);
                GeneralUtils.healPlayer((LivingEntity)pEnt, amountToHeal);
                //ParticleEffect.DRIP_LAVA.display(0.0F, 0.0F, 0.0F, 0.075F, 10, pEnt.getLocation(), 100.0D);
                pEnt.playSound(pEnt.getLocation(), Sound.EAT, 0.4F, 0.6F);
            }
        } else {
            le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, bleedStack * 20, Math.max(1, bleedStack / 3)));
        }
        playBleedEffect(le.getLocation().add(0.0D, 1.0D, 0.0D), bleedStack);
    }

    private void playBleedEffect(Location loc, int bleedStack) {
        //for (int i = Math.max(1, bleedStack / 2); i > 0; i--)
            //ParticleEffect.BLOCK_CRACK.display((ParticleEffect.ParticleData)new ParticleEffect.BlockData(Material.REDSTONE_BLOCK, (byte)14), 0.4F, 1.0F, 0.4F, 1.0F, 100, loc.getBlock().getLocation(), 150.0D);
    }
}
