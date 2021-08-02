package com.oneshotmc.enchants.utils;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class ArmorUtil {
    public static float getArmorDamageNullificationPercent(float armor_val) {
        if (armor_val == 0.0F)
            return 1.0F;
        return 1.0F - armor_val * 0.04F;
    }

    public static boolean hasArmorPiece(Player player, ArmorSlot slot) {
        ItemStack retr = player.getEquipment().getArmorContents()[slot.getIndex()];
        return (retr != null && retr.getType() != Material.AIR);
    }

    public static ItemStack damagePlayerArmorByPercent(Player player, ArmorSlot slot, double percent) {
        EntityEquipment equipment = player.getEquipment();
        ItemStack item = equipment.getArmorContents()[slot.getIndex()];
        if (item == null || item.getType() == Material.AIR)
            return null;
        int maxDurability = item.getType().getMaxDurability();
        if (maxDurability <= 0)
            return null;
        double amount = (int)(maxDurability * 0.01D * percent);
        return modifyPlayerArmor(player, slot, (int)-amount, false);
    }

    public static ItemStack modifyPlayerArmor(Player player, ArmorSlot slot, int amount) {
        return modifyPlayerArmor(player, slot, amount, false);
    }

    public static ItemStack addDurability(ItemStack itemStack, int durability) {
        CraftItemStack craftItemStack = CraftItemStack.asCraftCopy(itemStack);
        craftItemStack.setDurability((short)(craftItemStack.getDurability() + durability));
        return craftItemStack;
    }

    public static ItemStack subtractDurability(ItemStack itemStack, int durability) {
        CraftItemStack craftItemStack = CraftItemStack.asCraftCopy(itemStack);
        craftItemStack.setDurability((short)(craftItemStack.getDurability() - durability));
        return craftItemStack;
    }

    public static ItemStack modifyPlayerArmor(Player player, ArmorSlot slot, int amount, boolean checkNoDura) {
        EntityEquipment equipment = player.getEquipment();
        ItemStack item = equipment.getArmorContents()[slot.getIndex()];
        if (item != null) {
            if (checkNoDura && item.getDurability() <= 0)
                return item;
            if (amount > 0) {
                item = addDurability(item, amount);
            } else {
                item = subtractDurability(item, Math.abs(amount));
            }
            if (item != null && item.getDurability() > item.getType().getMaxDurability()) {
                short dura = item.getDurability();
                item = null;
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 3.0F, 0.8F);
                Bukkit.getLogger().info("[OneShot Enchants] Clearing ArmorSlot " + slot.name() + " for " + player.getName() + " damaged " + amount + " dura, new dura: " + dura);
            }
        }
        if (slot == ArmorSlot.HELMET) {
            player.getInventory().setHelmet(item);
        } else if (slot == ArmorSlot.CHESTPLATE) {
            player.getInventory().setChestplate(item);
        } else if (slot == ArmorSlot.LEGGINGS) {
            player.getInventory().setLeggings(item);
        } else if (slot == ArmorSlot.BOOTS) {
            player.getInventory().setBoots(item);
        }
        return item;
    }

    public static float getEnchantDamageModifier(Player pl) {
        ItemStack weapon = pl.getItemInHand();
        float damage_mod = 0.0F;
        if (weapon != null) {
            if (weapon.getType() != Material.BOW && weapon.getEnchantments().containsKey(Enchantment.DAMAGE_ALL))
                damage_mod = weapon.getEnchantments().get(Enchantment.DAMAGE_ALL) * 0.625F;
            if (weapon.getType() == Material.BOW && weapon.getEnchantments().containsKey(Enchantment.ARROW_DAMAGE))
                damage_mod = 0.25F * (weapon.getEnchantments().get(Enchantment.ARROW_DAMAGE) + 1.0F);
        }
        return damage_mod;
    }

    public static int getArmorValue(Player pl) {
        int armor_val = 0;
        for (net.minecraft.server.v1_8_R3.ItemStack item : (((CraftPlayer)pl).getHandle()).inventory.getArmorContents()) {
            if (item != null) {
                Item items = item.getItem();
                if (items == Items.LEATHER_HELMET)
                    armor_val++;
                if (items == Items.LEATHER_CHESTPLATE)
                    armor_val += 3;
                if (items == Items.LEATHER_LEGGINGS)
                    armor_val += 2;
                if (items == Items.LEATHER_BOOTS)
                    armor_val++;
                if (items == Items.CHAINMAIL_HELMET)
                    armor_val += 2;
                if (items == Items.CHAINMAIL_CHESTPLATE)
                    armor_val += 5;
                if (items == Items.CHAINMAIL_LEGGINGS)
                    armor_val += 4;
                if (items == Items.CHAINMAIL_BOOTS)
                    armor_val++;
                if (items == Items.IRON_HELMET)
                    armor_val += 2;
                if (items == Items.IRON_CHESTPLATE)
                    armor_val += 6;
                if (items == Items.IRON_LEGGINGS)
                    armor_val += 5;
                if (items == Items.IRON_BOOTS)
                    armor_val += 2;
                if (items == Items.DIAMOND_HELMET)
                    armor_val += 3;
                if (items == Items.DIAMOND_CHESTPLATE)
                    armor_val += 8;
                if (items == Items.DIAMOND_LEGGINGS)
                    armor_val += 6;
                if (items == Items.DIAMOND_BOOTS)
                    armor_val += 3;
                if (items == Items.GOLDEN_HELMET)
                    armor_val += 2;
                if (items == Items.GOLDEN_CHESTPLATE)
                    armor_val += 5;
                if (items == Items.GOLDEN_LEGGINGS)
                    armor_val += 3;
                if (items == Items.GOLDEN_BOOTS)
                    armor_val++;
            }
        }
        return armor_val;
    }
}