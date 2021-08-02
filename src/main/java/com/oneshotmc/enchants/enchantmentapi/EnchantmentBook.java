package com.oneshotmc.enchants.enchantmentapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.utils.Config;
import com.oneshotmc.enchants.utils.Utils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentBook {
    private static List<Material> armorMaterials = new ArrayList<>(Arrays.asList(GeneralUtils.armor));

    private static String getRoman(int number) {
        String[] riman = {
                "M", "XM", "CM", "D", "XD", "CD", "C", "XC", "L", "XL",
                "X", "IX", "V", "IV", "I" };
        int[] arab = {
                1000, 990, 900, 500, 490, 400, 100, 90, 50, 40,
                10, 9, 5, 4, 1 };
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (number > 0 || arab.length == i - 1) {
            while (number - arab[i] >= 0) {
                number -= arab[i];
                result.append(riman[i]);
            }
            i++;
        }
        return result.toString();
    }

    private static List<String> formatLore(String s) {
        List<String> lore_l = new ArrayList<>();
        if (s == null)
            return lore_l;
        if (s.length() <= 38) {
            lore_l.add(ChatColor.GRAY + s);
            return lore_l;
        }
        if (s.contains(" ")) {
            String line = ChatColor.GRAY.toString();
            for (String word : s.split(" ")) {
                if (line.length() - 1 + word.length() <= 38) {
                    line = line + word + " ";
                } else {
                    if (line.endsWith(" "))
                        line = line.substring(0, line.length() - 1);
                    lore_l.add(line);
                    line = ChatColor.GRAY + word + " ";
                }
            }
            if (ChatColor.stripColor(line).length() > 0)
                lore_l.add(line);
        } else {
            lore_l.add(s);
            return lore_l;
        }
        return lore_l;
    }

    private static String toProperCase(String s) {
        String temp = s.trim();
        String spaces = "";
        if (temp.length() != s.length()) {
            int startCharIndex = s.charAt(temp.indexOf(String.valueOf(false)));
            spaces = s.substring(0, startCharIndex);
        }
        temp = temp.substring(0, 1).toUpperCase() + spaces + temp.substring(1).toLowerCase() + " ";
        return temp;
    }

    private static String toCamelCase(String s) {
        String[] parts = s.split(" ");
        String camelCaseString = "";
        for (String part : parts) {
            if (part != null && part.trim().length() > 0) {
                camelCaseString = camelCaseString + toProperCase(part);
            } else {
                camelCaseString = camelCaseString + part + " ";
            }
        }
        return camelCaseString.contains(" ") ? camelCaseString.substring(0, camelCaseString.length() - 1) : camelCaseString;
    }

    private static int decodeSingle(char letter) {
        switch (letter) {
            case 'M':
                return 1000;
            case 'D':
                return 500;
            case 'C':
                return 100;
            case 'L':
                return 50;
            case 'X':
                return 10;
            case 'V':
                return 5;
            case 'I':
                return 1;
        }
        return 0;
    }

    public static int decode(String roman) {
        int result = 0;
        String uRoman = roman.toUpperCase();
        for (int i = 0; i < uRoman.length() - 1; i++) {
            if (decodeSingle(uRoman.charAt(i)) < decodeSingle(uRoman.charAt(i + 1))) {
                result -= decodeSingle(uRoman.charAt(i));
            } else {
                result += decodeSingle(uRoman.charAt(i));
            }
        }
        result += decodeSingle(uRoman.charAt(uRoman.length() - 1));
        return result;
    }

    private static String getEnchantmentType(Material[] nm) {
        if (nm == GeneralUtils.armor)
            return "Armor";
        if (nm == GeneralUtils.boots)
            return "Boots";
        if (nm == GeneralUtils.chestplates)
            return "Chestplate";
        if (nm == GeneralUtils.helmets)
            return "Helmet";
        if (nm == GeneralUtils.pickaxe)
            return "Pickaxe";
        if (nm == GeneralUtils.weapons)
            return "Weapon";
        if (nm == GeneralUtils.axe)
            return "Axe";
        if (nm == GeneralUtils.swords)
            return "Sword";
        if (nm[0] == Material.BOW)
            return "Bow";
        Material m = nm[0];
        if (m.name().contains("_"))
            return toCamelCase(m.name().split("_")[1]);
        return toCamelCase(m.name());
    }

    public static org.bukkit.inventory.ItemStack getEnchantmentBook(CustomEnchantment ench, int level) {
        ItemStack itemStack = Utils.itemBuilder(Material.INK_SACK, TieredEnchantments.getTierOrbColor(ench), Utils.color(TieredEnchantments.getTierColor(ench) + "&l&n" + ench.name() + " " + getRoman(level)), new ArrayList(), Boolean.valueOf(true));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Utils.color("&aSuccess Rate: &7" + Utils.randomInt(1, 100) + "%"));
        lore.add(Utils.color("&cDestroy Rate: &7" + Utils.randomInt(1, 100) + "%"));
        lore.add("");
        String desc = EnchantmentDescriptions.getDescription(ench);
        for (String s : formatLore(desc))
            lore.add(s);
        lore.add("");
        lore.add(Utils.color("&9Enchant Target(s): &7" + getEnchantmentType(ench.getNaturalMaterials())));
        ItemMeta im = itemStack.getItemMeta();
        im.setLore(lore);
        itemStack.setItemMeta(im);
        net.minecraft.server.v1_8_R3.ItemStack nonStackable = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = (nonStackable.getTag() != null) ? nonStackable.getTag() : new NBTTagCompound();
        tagCompound.setLong("NONSTACK", Utils.randomInt(11111111, 99999999));
        return (ItemStack)CraftItemStack.asCraftMirror(nonStackable);
    }

    public static ItemStack getEnchantmentBook(CustomEnchantment ench, int level, int success, int destroy) {
        ItemStack itemStack = Utils.itemBuilder(Material.INK_SACK, TieredEnchantments.getTierOrbColor(ench), Utils.color(TieredEnchantments.getTierColor(ench) + "&l&n" + ench.name() + " " + getRoman(level)), new ArrayList(), Boolean.valueOf(true));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Utils.color("&aSuccess Rate: &7" + success + "%"));
        lore.add(Utils.color("&cDestroy Rate: &7" + destroy + "%"));
        lore.add("");
        String desc = EnchantmentDescriptions.getDescription(ench);
        for (String s : formatLore(desc))
            lore.add(s);
        lore.add("");
        lore.add(Utils.color("&9Enchant Target(s): &7" + getEnchantmentType(ench.getNaturalMaterials())));
        ItemMeta im = itemStack.getItemMeta();
        im.setLore(lore);
        itemStack.setItemMeta(im);
        net.minecraft.server.v1_8_R3.ItemStack nonStackable = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = (nonStackable.getTag() != null) ? nonStackable.getTag() : new NBTTagCompound();
        tagCompound.setLong("NONSTACK", Utils.randomInt(11111111, 99999999));
        return (ItemStack)CraftItemStack.asCraftMirror(nonStackable);
    }

    public static ItemStack getEnchantmentListItem(CustomEnchantment ench) {
        ItemStack itemStack = Utils.itemBuilder(Material.INK_SACK, TieredEnchantments.getTierOrbColor(ench), Utils.color(TieredEnchantments.getTierColor(ench) + "&l&n" + ench.name() + "&r &8[&7I-" + getRoman(ench.getMaxLevel()) + "&8]"), new ArrayList(), Boolean.valueOf(true));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Utils.color("&f&lDescription:"));
        String desc = EnchantmentDescriptions.getDescription(ench);
        for (String s : formatLore(desc))
            lore.add(s);
        lore.add("");
        lore.add(Utils.color("&f&lEnchant Type(s): &7" + getEnchantmentType(ench.getNaturalMaterials())));
        ItemMeta im = itemStack.getItemMeta();
        im.setLore(lore);
        itemStack.setItemMeta(im);
        net.minecraft.server.v1_8_R3.ItemStack nonStackable = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = (nonStackable.getTag() != null) ? nonStackable.getTag() : new NBTTagCompound();
        tagCompound.setLong("NONSTACK", Utils.randomInt(11111111, 99999999));
        return (ItemStack)CraftItemStack.asCraftMirror(nonStackable);
    }

    public static boolean isEquipment(ItemStack is) {
        return (armorMaterials.contains(is.getType()) || is
                .getType() == Material.BOW || is
                .getType().name().toLowerCase().contains("_sword") || is
                .getType().name().toLowerCase().contains("_axe") || is
                .getType().name().toLowerCase().contains("_pickaxe") || is
                .getType().name().toLowerCase().contains("_hoe") || is
                .getType().name().toLowerCase().contains("_spade") || is
                .getType() == Material.FISHING_ROD);
    }

    public static boolean isArmor(ItemStack is) {
        return armorMaterials.contains(Boolean.valueOf((is
                .getType().name().toLowerCase().contains("_helmet") || is
                .getType().name().toLowerCase().contains("_chestplate") || is
                .getType().name().toLowerCase().contains("_leggings") || is
                .getType().name().toLowerCase().contains("_boots") || is
                .getType().name().toLowerCase().contains("_spade"))));
    }

    public static boolean isWhitescroll(ItemStack is) {
        return (is != null && is
                .hasItemMeta() && is
                .getItemMeta().hasDisplayName() && is
                .getItemMeta().getDisplayName().equals(Utils.color("&f&lProtection Scroll")) && is
                .getItemMeta().hasLore() && ((String)is
                .getItemMeta().getLore().get(0)).equals(Utils.color("&7Apply this protection scroll to your")));
    }

    public static boolean isEnchantmentBook(ItemStack is) {
        if (is != null && is.getType() == Material.INK_SACK && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().hasLore())
            try {
                if (!is.getItemMeta().getDisplayName().contains(ChatColor.BOLD.toString()) || !is.getItemMeta().getDisplayName().contains(ChatColor.UNDERLINE.toString()))
                    return false;
                String displayName = ChatColor.stripColor(is.getItemMeta().getDisplayName());
                String enchName = displayName.replace("Book of ", "");
                enchName = enchName.substring(0, enchName.lastIndexOf(" "));
                if (OneShotEnchants.isRegistered(enchName))
                    return true;
            } catch (Exception err) {
                err.printStackTrace();
                return false;
            }
        return false;
    }

    public static CustomEnchantment getEnchantment(ItemStack ench_book) {
        if (!isEnchantmentBook(ench_book))
            return null;
        String displayName = ChatColor.stripColor(ench_book.getItemMeta().getDisplayName());
        if (displayName.contains("[I"))
            displayName = displayName.replace("[I", "").replace("]", "");
        String enchName = displayName.replace("Book of ", "");
        enchName = enchName.substring(0, enchName.lastIndexOf(" "));
        CustomEnchantment ce = OneShotEnchants.getEnchantment(enchName);
        return ce;
    }

    public static int getEnchantmentLevel(ItemStack ench_book) {
        if (!isEnchantmentBook(ench_book))
            return 0;
        int level = decode(ChatColor.stripColor(ench_book
                .getItemMeta().getDisplayName().substring(ench_book.getItemMeta().getDisplayName().lastIndexOf(" "), ench_book.getItemMeta().getDisplayName().length())));
        return level;
    }

    public static int getEnchantmentBookSuccessRate(ItemStack is) {
        return Integer.parseInt(ChatColor.stripColor(((String)is.getItemMeta().getLore().get(1)).replace("Success Rate: ", "").replace("%", "")));
    }

    public static int getEnchantmentBookDestructionRate(ItemStack is) {
        return Integer.parseInt(ChatColor.stripColor(((String)is.getItemMeta().getLore().get(2)).replace("Destroy Rate: ", "").replace("%", "")));
    }

    public static boolean isProtected(ItemStack is) {
        if (is != null && is.hasItemMeta() && is.getItemMeta().hasLore())
            for (String s : is.getItemMeta().getLore()) {
                if (s.equals(Utils.color(Config.protectedLore)))
                    return true;
            }
        return false;
    }

    public static void removeWhitescroll(ItemStack is) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = im.getLore();
        lore.remove(Utils.color(Config.protectedLore));
        im.setLore(lore);
        is.setItemMeta(im);
    }

    public static int getCustomEnchantmentCount(ItemStack is) {
        return OneShotEnchants.getEnchantments(is).size();
    }

    public static int getMaxEnchantmentCountAllowed(Player pl) {
        if (pl.hasPermission("oneshotenchants.apply.admin"))
            return 100;
        if (pl.hasPermission("oneshotenchants.apply.12"))
            return 11;
        if (pl.hasPermission("oneshotenchants.apply.11"))
            return 10;
        if (pl.hasPermission("oneshotenchants.apply.10"))
            return 9;
        if (pl.hasPermission("oneshotenchants.apply.9"))
            return 8;
        if (pl.hasPermission("oneshotenchants.apply.8"))
            return 7;
        if (pl.hasPermission("oneshotenchants.apply.7"))
            return 6;
        if (pl.hasPermission("oneshotenchants.apply.6"))
            return 5;
        if (pl.hasPermission("oneshotenchants.apply.5"))
            return 4;
        if (pl.hasPermission("oneshotenchants.apply.4"))
            return 3;
        if (pl.hasPermission("oneshotenchants.apply.3"))
            return 2;
        if (pl.hasPermission("oneshotenchants.apply.2"))
            return 1;
        return 3;
    }

    public static boolean applyEnchantmentBook(Player pl, ItemStack ench_book, ItemStack item) {
        CustomEnchantment ce = getEnchantment(ench_book);
        int level = getEnchantmentLevel(ench_book);
        int success_rate = getEnchantmentBookSuccessRate(ench_book);
        int success_rate_mod = 0;
        int destroy_rate = getEnchantmentBookDestructionRate(ench_book);
        if (pl.hasPermission("oneshotenchants.bookbonus"))
            success_rate_mod += 5;
        if (ce == null || level == 0)
            return false;
        if (!ce.canEnchantOnto(item))
            return false;
        success_rate += success_rate_mod;
        if (Utils.randomInt(1, 100) > success_rate) {
            if (isProtected(item)) {
                removeWhitescroll(item);
                pl.sendMessage(Utils.color("&a&l(!) &aYour item would have been destroyed, but your Protection Orb saved it!"));
            } else if (Utils.randomInt(1, 100) < destroy_rate) {
                pl.sendMessage(Utils.color("&c&l(!) &cYour item was destroyed by the enchantment orb. Use Protection Orbs to protect your items when applying enchantments."));
                item.setDurability((short)69);
                pl.getOpenInventory().getTopInventory().remove(item);
                pl.getOpenInventory().getBottomInventory().remove(item);
            }
            return false;
        }
        ce.addToItem(item, level);
        return true;
    }
}
