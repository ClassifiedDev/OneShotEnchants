package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class ArrowDeflect extends CustomEnchantment {
    public ArrowDeflect() {
        super("Arrow Deflect", GeneralUtils.armor, 4);
        this.max = 4;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent)e;
            if (edbee.getDamager() instanceof Projectile) {
                Projectile shooter = (Projectile)edbee.getDamager();
                if (shooter != null && shooter.hasMetadata("lethalSniper")) {
                    int sniper = ((MetadataValue)shooter.getMetadata("lethalSniper").get(0)).asInt();
                    double chance = 0.1D * sniper;
                    if (Math.random() <= chance)
                        return;
                }
                if (player.hasMetadata("lastArrowDamageEvent")) {
                    long minDelayMilliseconds = (level * 500);
                    long last = ((MetadataValue)player.getMetadata("lastArrowDamageEvent").get(0)).asLong();
                    if (System.currentTimeMillis() - last <= minDelayMilliseconds) {
                        //ParticleEffect.SPELL.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 20, player.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                        ((Player)player).playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7F, 0.2F);
                        e.setCancelled(true);
                        e.setDamage(0.0D);
                        edbee.getDamager().remove();
                        player.removeMetadata("lastArrowDamageEvent", OneShotEnchants.getInstance());
                        return;
                    }
                }
                player.setMetadata("lastArrowDamageEvent", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
            }
        }
    }
}
