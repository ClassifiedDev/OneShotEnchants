package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Resilience extends CustomEnchantment {
    public Resilience() {
        super("Resilience", GeneralUtils.helmets, 2);
        this.max = 4;
        this.base = 17.0D;
        this.interval = 4.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double dmg = e.getFinalDamage();
        double minDmgToProc = (10 - level);
        double chance = level * 0.05D;
        if (player.getHealth() - dmg <= 0.0D && dmg >= minDmgToProc && Math.random() < chance) {
            e.setDamage(e.getDamage() / 2.0D);
            ((Player)player).sendMessage(Utils.color("&a&lRESILIENCE (&a50% DMG TAKEN&a&l) "));
        }
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        player.setMetadata("resilienceEnchant", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(enchantLevel)));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removeMetadata("resilienceEnchant", OneShotEnchants.getInstance());
    }
}
