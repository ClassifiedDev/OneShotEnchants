package com.oneshotmc.enchants.enchants.tools;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;

public class AutoSmelt extends CustomEnchantment {
    public AutoSmelt() {
        super("Auto Smelt", GeneralUtils.pickaxe, 6);
        this.max = 3;
        this.base = 10.0D;
        this.interval = 10.0D;
    }

    public void applyToolEffect(Player player, Block block, int enchantLevel, BlockEvent event) {
        if (event instanceof BlockBreakEvent) {
            BlockBreakEvent bbe = (BlockBreakEvent)event;
            Material m = bbe.getBlock().getType();
            if (getSmeltedItem(m) != m) {
                boolean hasDetonate = OneShotEnchants.itemHasEnchantment(player.getItemInHand(), "Detonate");
                if (hasDetonate)
                    return;
                if (bbe.isCancelled())
                    return;
                double chance = enchantLevel * 0.34D;
                if (Math.random() < chance) {
                    ItemStack smelted = new ItemStack(getSmeltedItem(m), enchantLevel);
                    bbe.getBlock().setTypeIdAndData(0, (byte)0, false);
                    player.giveExp(bbe.getExpToDrop());
                    if (player.getInventory().firstEmpty() != -1) {
                        player.getInventory().addItem(new ItemStack[] { smelted });
                    } else {
                        player.getWorld().dropItem(player.getLocation(), smelted);
                    }
                }
            }
        }
    }

    private Material getSmeltedItem(Material m) {
        if (m == Material.IRON_ORE)
            return Material.IRON_INGOT;
        if (m == Material.GOLD_ORE)
            return Material.GOLD_INGOT;
        return m;
    }
}
