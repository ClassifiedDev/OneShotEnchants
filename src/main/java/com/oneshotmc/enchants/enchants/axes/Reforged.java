package com.oneshotmc.enchants.enchants.axes;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ArmorUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Reforged extends CustomEnchantment {
    public Reforged() {
        super("Reforged", GeneralUtils.weapons_and_tools, 7);
        this.max = 10;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!(user instanceof Player))
            return;
        Player playerAttacker = (Player)user;
        double random = Math.random();
        double chance = 0.08D * enchantLevel;
        if (random < chance) {
            ItemStack weapon = playerAttacker.getItemInHand();
            if (weapon != null && weapon.getDurability() != 0 && weapon.getMaxStackSize() == 1) {
                playerAttacker.setItemInHand(ArmorUtil.addDurability(weapon, 1));
                playerAttacker.updateInventory();
            }
        }
    }

    public void applyToolEffect(Player player, Block block, int enchantLevel, BlockEvent event) {
        if (event instanceof org.bukkit.event.block.BlockBreakEvent) {
            Player playerAttacker = player;
            double random = Math.random();
            double chance = 0.08D * enchantLevel;
            if (random < chance) {
                ItemStack weapon = playerAttacker.getItemInHand();
                if (weapon != null && weapon.getDurability() != 0 && weapon.getMaxStackSize() == 1) {
                    playerAttacker.setItemInHand(ArmorUtil.addDurability(weapon, 1));
                    playerAttacker.updateInventory();
                }
            }
        }
    }
}
