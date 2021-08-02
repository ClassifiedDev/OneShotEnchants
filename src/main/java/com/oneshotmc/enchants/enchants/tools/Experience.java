package com.oneshotmc.enchants.enchants.tools;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;

public class Experience extends CustomEnchantment {
    public Experience() {
        super("Experience", GeneralUtils.tools, 6);
        this.max = 5;
        this.base = 10.0D;
        this.interval = 10.0D;
    }

    public void applyToolEffect(Player player, Block block, int enchantLevel, BlockEvent event) {
        if (event instanceof BlockBreakEvent) {
            BlockBreakEvent bbe = (BlockBreakEvent)event;
            double expMod = 1.0D + 0.25D * enchantLevel;
            bbe.setExpToDrop((int)(bbe.getExpToDrop() * expMod));
        }
    }
}
