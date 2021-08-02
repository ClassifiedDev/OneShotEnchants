package com.oneshotmc.enchants.enchants.tools;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Haste extends CustomEnchantment {
    public Haste() {
        super("Haste", GeneralUtils.tools, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 10.0D;
    }

    public void applyToolEffect(Player player, Block block, int enchantLevel, BlockEvent event) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, enchantLevel - 1), true);
    }
}
