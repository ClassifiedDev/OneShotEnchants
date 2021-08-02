package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Metaphysical extends CustomEnchantment {
    public Metaphysical() {
        super("Metaphysical", GeneralUtils.boots, 2);
        this.max = 4;
        this.base = 17.0D;
        this.interval = 4.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {}

    public void applyEquipEffect(Player player, int enchantLevel) {
        player.setMetadata("metaphysicalEnchant", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(enchantLevel)));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removeMetadata("metaphysicalEnchant", OneShotEnchants.getInstance());
    }
}
