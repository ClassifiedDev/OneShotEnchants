package com.oneshotmc.enchants.enchants.tools;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;

public class Oxygenate extends CustomEnchantment {
    public Oxygenate() {
        super("Oxygenate", GeneralUtils.tools, 6);
        this.max = 1;
        this.base = 10.0D;
        this.interval = 10.0D;
    }

    public void applyToolEffect(Player player, Block block, int enchantLevel, BlockEvent event) {
        if (event instanceof org.bukkit.event.block.BlockBreakEvent && player
                .getRemainingAir() + 20 <= player.getMaximumAir())
            player.setRemainingAir(player.getRemainingAir() + 20);
    }
}
