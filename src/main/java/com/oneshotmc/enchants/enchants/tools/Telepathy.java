package com.oneshotmc.enchants.enchants.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.block.Block;
import net.minecraft.server.v1_8_R3.Item;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;

public class Telepathy extends CustomEnchantment {
    private List<Material> fortuneBlocks;

    public Telepathy() {
        super("Telepathy", GeneralUtils.tools, 6);
        this.fortuneBlocks = new ArrayList<>(Arrays.asList(new Material[] { Material.COAL_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.QUARTZ_ORE, Material.LAPIS_ORE }));
        this.max = 4;
        this.base = 10.0D;
        this.interval = 10.0D;
    }

    public void applyToolEffect(Player player, Block block, int enchantLevel, BlockEvent event) {
        if (event instanceof BlockBreakEvent) {
            BlockBreakEvent bbe = (BlockBreakEvent)event;
            Material m = bbe.getBlock().getType();
            if (m == Material.SPONGE)
                return;
            if (WorldGuardUtils.isProtected(bbe.getBlock()))
                return;
            double chance = enchantLevel * 0.25D;
            if (Math.random() < chance) {
                if (bbe.getBlock().getDrops().size() < 0 || ((bbe.getBlock().getType() == Material.IRON_ORE || bbe.getBlock().getType() == Material.GOLD_ORE) && OneShotEnchants.itemHasEnchantment(player.getItemInHand(), "Auto Smelt")))
                    return;
                if (bbe.getBlock().getDrops(player.getItemInHand()).size() < 0)
                    return;
                Collection<ItemStack> drops = bbe.getBlock().getDrops(player.getItemInHand());
                if (drops.size() <= 0)
                    return;
                ItemStack drop = (ItemStack)drops.toArray()[0];
                boolean hasSilkTouch = player.getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH);
                boolean silkTouchedBlock = false;
                if (hasSilkTouch) {
                    int amount = drop.getAmount();
                    Item item = Item.getItemOf(net.minecraft.server.v1_8_R3.Block.getById(bbe.getBlock().getTypeId()));
                    short durability = (short)bbe.getBlock().getData();
                    Material mm = Material.getMaterial(Item.getId(item));
                    if (mm == Material.ANVIL)
                        durability = 0;
                    if (mm != drop.getType())
                        silkTouchedBlock = true;
                    drop = new ItemStack(mm, amount, durability);
                }
                if (bbe.getBlock().getType() == Material.DOUBLE_STEP)
                    drop = new ItemStack(Material.STEP, 2);
                if (!silkTouchedBlock) {
                    int fortuneDropAmount = getBlockDropsFromEnchantedTool(block, player.getItemInHand());
                    if (drop.getAmount() < fortuneDropAmount)
                        drop.setAmount(fortuneDropAmount);
                }
                bbe.getBlock().setTypeIdAndData(0, (byte)0, true);
                player.giveExp(bbe.getExpToDrop());
                if (bbe.getBlock().getType() == Material.RAILS)
                    return;
                if (player.getInventory().firstEmpty() != -1) {
                    player.getInventory().addItem(new ItemStack[] { drop });
                } else {
                    player.getWorld().dropItem(player.getLocation(), drop);
                }
            }
        }
    }

    private int getBlockDropsFromEnchantedTool(Block b, ItemStack tool) {
        Material m = b.getType();
        if (tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) > 0) {
            int fortune = tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            if (this.fortuneBlocks.contains(b.getType())) {
                if (fortune == 1)
                    return 1 + ((Math.random() < 0.25D) ? 1 : 0);
                if (fortune == 2)
                    return 1 + ((Math.random() < 0.25D) ? 1 : 0) + ((Math.random() < 0.25D) ? 1 : 0) + ((Math.random() < 0.25D) ? 1 : 0);
                if (fortune >= 3)
                    return 1 + ((Math.random() < 0.2D) ? 1 : 0) + ((Math.random() < 0.2D) ? 1 : 0) + ((Math.random() < 0.2D) ? 1 : 0) + ((Math.random() < 0.2D) ? 1 : 0);
            }
        }
        return 1;
    }
}
