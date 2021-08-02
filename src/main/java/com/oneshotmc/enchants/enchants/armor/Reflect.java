package com.oneshotmc.enchants.enchants.armor;

import java.util.List;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Reflect extends CustomEnchantment {
    public Reflect() {
        super("Enchant Reflect", GeneralUtils.armor, 3);
        this.max = 10;
        this.base = 25.0D;
        this.interval = 10.0D;
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        player.setMetadata("normal_reflect_enchant", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(enchantLevel)));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removeMetadata("normal_reflect_enchant", OneShotEnchants.getInstance());
    }

    public static int getReflectLevel(LivingEntity player, String key) {
        List<MetadataValue> found = player.getMetadata(key + "_reflect_enchant");
        if (found != null && !found.isEmpty())
            return ((MetadataValue)found.get(0)).asInt();
        return 0;
    }
}
