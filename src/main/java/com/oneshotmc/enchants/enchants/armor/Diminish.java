package com.oneshotmc.enchants.enchants.armor;

import java.text.DecimalFormat;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Diminish extends CustomEnchantment {
    private DecimalFormat df;

    public Diminish() {
        super("Diminish", GeneralUtils.chestplates, 2);
        this.df = new DecimalFormat("#.##");
        this.max = 6;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("diminishEffect")) {
            double maxDmg = ((MetadataValue)player.getMetadata("diminishEffect").get(0)).asDouble();
            if (e.getFinalDamage() > maxDmg) {
                e.setDamage(maxDmg);
                player.removeMetadata("diminishEffect", OneShotEnchants.getInstance());
                return;
            }
        }
        double random = Math.random();
        double procChance = 0.015D * level;
        if (random < procChance && e.getFinalDamage() > 0.0D) {
            player.setMetadata("diminishEffect", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Double.valueOf(e.getFinalDamage())));
            ((Player)player).sendMessage(Utils.color("&e&lDIMINISH [&eDMG: " + this.df.format(Math.max(0.0D, e.getFinalDamage())) + "&e&l] "));
        }
    }
}
