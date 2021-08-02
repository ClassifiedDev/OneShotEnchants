package com.oneshotmc.enchants.utils;

import com.oneshotmc.enchants.utils.Config;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomItems {
    public static ItemStack protectionItem = Utils.itemBuilder(Config.protectionScrollMaterial, Config.protectionScrollData, 1, Config.protectionScrollName, Config.protectionScrollLore, Config.protectionScrollGlowing);

    public static ItemStack organizeScrollItem = Utils.itemBuilder(Config.organizationScrollMaterial, Config.organizationScrollData, 1, Config.organizationScrollName, Config.organizationScrollLore, Config.organizationScrollGlowing);

    public static ItemStack renameScrollItem = Utils.itemBuilder(Config.renameScrollMaterial, Config.renameScrollData, 1, Config.renameScrollName, Config.renameScrollLore, Config.renameScrollGlowing);

    public static ItemStack disenchantItem(int percent) {
        ItemStack is = Utils.itemBuilder(Config.removalScrollMaterial, Config.removalScrollData, 1, Config.removalScrollName, new String[] { "&7Removes a random enchantment", "&7from an item and converts it", "&7into a &f" + percent + "% &7enchantment orb.", " ", "&7&oDrag n drop onto item to use." }, Config.removalScrollGlowing);
        return is;
    }

    public static ItemStack tier1MysteryEnchantmentItem = Utils.itemBuilder(Config.enchantmentBook1Material, Config.enchantmentBook1Data, 1, Config.tier1BookName, Config.tier1BookLore, Config.enchantmentBookGlowing);

    public static ItemStack tier2MysteryEnchantmentItem = Utils.itemBuilder(Config.enchantmentBook2Material, Config.enchantmentBook2Data, 1, Config.tier2BookName, Config.tier2BookLore, Config.enchantmentBookGlowing);

    public static ItemStack tier3MysteryEnchantmentItem = Utils.itemBuilder(Config.enchantmentBook3Material, Config.enchantmentBook3Data, 1, Config.tier3BookName, Config.tier3BookLore, Config.enchantmentBookGlowing);

    public static ItemStack tier4MysteryEnchantmentItem = Utils.itemBuilder(Config.enchantmentBook4Material, Config.enchantmentBook4Data, 1, Config.tier4BookName, Config.tier4BookLore, Config.enchantmentBookGlowing);

    public static ItemStack tier5MysteryEnchantmentItem = Utils.itemBuilder(Config.enchantmentBook5Material, Config.enchantmentBook5Data, 1, Config.tier5BookName, Config.tier5BookLore, Config.enchantmentBookGlowing);

    public static ItemStack tier6MysteryEnchantmentItem = Utils.itemBuilder(Config.enchantmentBook6Material, Config.enchantmentBook6Data, 1, Config.tier6BookName, Config.tier6BookLore, Config.enchantmentBookGlowing);

    public static ItemStack tier1MysteryDustItem = Utils.itemBuilder(Material.FIREBALL, (short) 0, 1, "&f&lCommon Dust Pouch &7(Right-Click)", new String[] { "&7Right click to reveal a either", "&fCommon &7magical dust or useless", "&7black powder." }, false);

    public static ItemStack tier2MysteryDustItem = Utils.itemBuilder(Material.FIREBALL, (short) 0, 1, "&a&lUncommon Dust Pouch &7(Right-Click)", new String[] { "&7Right click to reveal a either", "&aUncommon &7magical dust or useless", "&7black powder." }, false);

    public static ItemStack tier3MysteryDustItem = Utils.itemBuilder(Material.FIREBALL, (short) 0, 1, "&b&lRare Dust Pouch &7(Right-Click)", new String[] { "&7Right click to reveal a either", "&bRare &7magical dust or useless", "&7black powder." }, false);

    public static ItemStack tier4MysteryDustItem = Utils.itemBuilder(Material.FIREBALL, (short) 0, 1, "&e&lUltimate Dust Pouch &7(Right-Click)", new String[] { "&7Right click to reveal a either", "&eUltimate &7magical dust or useless", "&7black powder." }, false);

    public static ItemStack tier5MysteryDustItem = Utils.itemBuilder(Material.FIREBALL, (short) 0, 1, "&6&lLegendary Dust Pouch &7(Right-Click)", new String[] { "&7Right click to reveal a either", "&6Legendary &7magical dust or useless", "&7black powder." }, false);

    public static ItemStack tier6MysteryDustItem = Utils.itemBuilder(Material.FIREBALL, (short) 0, 1, "&c&lMythic Dust Pouch &7(Right-Click)", new String[] { "&7Right click to reveal a either", "&cMythic &7magical dust or useless", "&7black powder." }, false);

    public static ItemStack uselessPowderItem = Utils.itemBuilder(Material.SULPHUR, (short) 0, 1, "&8Useless Black Powder", new String[] { "&7Useless black powder is the failed", "&7bi product from a dust pouch." }, false);

    public static ItemStack getMysteryDustItem(int tier) {
        ItemStack is = null;
        switch (tier) {
            case 1:
                is = tier1MysteryDustItem;
                return is;
            case 2:
                is = tier2MysteryDustItem;
                return is;
            case 3:
                is = tier3MysteryDustItem;
                return is;
            case 4:
                is = tier4MysteryDustItem;
                return is;
            case 5:
                is = tier5MysteryDustItem;
                return is;
            case 6:
                is = tier6MysteryDustItem;
                return is;
        }
        is = tier1MysteryDustItem;
        return is;
    }

    public static ItemStack dustItem(String tier, int percent) {
        ItemStack tier1Dust, tier2Dust, tier3Dust, tier4Dust, tier5Dust, tier6Dust, is = null;
        switch (tier.toLowerCase()) {
            case "tier1":
                tier1Dust = Utils.itemBuilder(Material.SUGAR, (short) 0, 1, "&fCommon Enchantment Dust", new String[] { "&7Apply this &fCommon &7enchantment dust", "&7dust to increase a &fCommon &7enchantment", "&7orb's success rate by &f" + percent + "%", " ", "&7&oDrag n drop onto orb to apply." }, false);
                is = tier1Dust;
                break;
            case "tier2":
                tier2Dust = Utils.itemBuilder(Material.SUGAR, (short) 0, 1, "&aUncommon Enchantment Dust", new String[] { "&7Apply this &aUncommon &7enchantment dust", "&7dust to increase a &aUncommon &7enchantment", "&7orb's success rate by &f" + percent + "%", " ", "&7&oDrag n drop onto orb to apply." }, false);
                is = tier2Dust;
                break;
            case "tier3":
                tier3Dust = Utils.itemBuilder(Material.SUGAR, (short) 0, 1, "&bRare Enchantment Dust", new String[] { "&7Apply this &bRare &7enchantment dust", "&7dust to increase a &bRare &7enchantment", "&7orb's success rate by &f" + percent + "%", " ", "&7&oDrag n drop onto orb to apply." }, false);
                is = tier3Dust;
                break;
            case "tier4":
                tier4Dust = Utils.itemBuilder(Material.SUGAR, (short) 0, 1, "&eUltimate Enchantment Dust", new String[] { "&7Apply this &eUltimate &7enchantment dust", "&7dust to increase a &eUltimate &7enchantment", "&7orb's success rate by &f" + percent + "%", " ", "&7&oDrag n drop onto orb to apply." }, false);
                is = tier4Dust;
                break;
            case "tier5":
                tier5Dust = Utils.itemBuilder(Material.SUGAR, (short) 0, 1, "&6Legendary Enchantment Dust", new String[] { "&7Apply this &6Legendary &7enchantment dust", "&7dust to increase a &6Legendary &7enchantment", "&7orb's success rate by &f" + percent + "%", " ", "&7&oDrag n drop onto orb to apply." }, false);
                is = tier5Dust;
                break;
            case "tier6":
                tier6Dust = Utils.itemBuilder(Material.SUGAR, (short) 0, 1, "&cMythic Enchantment Dust", new String[] { "&7Apply this &cMythic &7enchantment dust", "&7dust to increase a &cMythic &7enchantment", "&7orb's success rate by &f" + percent + "%", " ", "&7&oDrag n drop onto orb to apply." }, false);
                is = tier6Dust;
                break;
        }
        return is;
    }

    public static ItemStack lootingIV = Utils.itemBuilder(Material.ENCHANTED_BOOK, (short) 0, 1, "&d&l&nLooting IV Enchant Book", new String[] { " ", "&7Applies Looting IV to your gear, this", "&7is a vanilla enchantment.", " ", "&9Enchant Target(s): &7Sword, Axe" }, false);

    public static ItemStack lootingV = Utils.itemBuilder(Material.ENCHANTED_BOOK, (short) 0, 1, "&d&l&nLooting V Enchant Book", new String[] { " ", "&7Applies Looting V to your gear, this", "&7is a vanilla enchantment.", " ", "&9Enchant Target(s): &7Sword, Axe" }, false);

    public static ItemStack depthStriderI = Utils.itemBuilder(Material.ENCHANTED_BOOK, (short) 0, 1, "&d&l&nDepth Strider I Enchant Book", new String[] { " ", "&7Applies Depth Strider I to your gear, this", "&7is a vanilla enchantment.", " ", "&9Enchant Target(s): &7Boots" }, false);

    public static ItemStack depthStriderII = Utils.itemBuilder(Material.ENCHANTED_BOOK, (short) 0, 1, "&d&l&nDepth Strider II Enchant Book", new String[] { " ", "&7Applies Depth Strider II to your gear, this", "&7is a vanilla enchantment.", " ", "&9Enchant Target(s): &7Boots" }, false);

    public static ItemStack depthStriderIII = Utils.itemBuilder(Material.ENCHANTED_BOOK, (short) 0, 1, "&d&l&nDepth Strider III Enchant Book", new String[] { " ", "&7Applies Depth Strider III to your gear, this", "&7is a vanilla enchantment.", " ", "&9Enchant Target(s): &7Boots" }, false);
}
