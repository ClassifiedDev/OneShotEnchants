package com.oneshotmc.enchants.enchantmentapi;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ENameParser {
    public static String parseName(String lore) {
        if (!lore.contains(" "))
            return null;
        String[] pieces = lore.split(" ");
        String name = "";
        for (int i = 0; i < pieces.length - 1; i++)
            name = name + pieces[i] + ((i < pieces.length - 2) ? " " : "");
        name = ChatColor.stripColor(name);
        return name;
    }

    public static int parseLevel(String lore) {
        if (!lore.contains(" "))
            return 0;
        String[] pieces = lore.split(" ");
        return RomanNumeral.getValueOf(pieces[pieces.length - 1]);
    }

    public static String getName(ItemStack item) {
        if (item.hasItemMeta()) {
            if (item.getItemMeta().hasEnchants())
                return null;
            if (item.getItemMeta().hasDisplayName())
                return null;
        }
        return getName(item.getType());
    }

    public static String getName(Material item) {
        String name = item.name().toLowerCase();
        String[] pieces = name.split("_");
        name = "";
        for (int i = 0; i < pieces.length; i++) {
            name = name + pieces[i].substring(0, 1).toUpperCase() + pieces[i].substring(1);
            if (i < pieces.length - 1)
                name = name + " ";
        }
        if (differentNames.containsKey(name))
            return ChatColor.AQUA + (String)differentNames.get(name);
        return ChatColor.AQUA + name;
    }

    public static String getVanillaName(Enchantment enchant) {
        for (Map.Entry<String, String> entry : eNames.entrySet()) {
            if (((String)entry.getValue()).equals(enchant.getName())) {
                if (((String)entry.getKey()).contains(" ")) {
                    String[] parts = ((String)entry.getKey()).split(" ");
                    String result = parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1);
                    for (int i = 1; i < parts.length; i++)
                        result = result + " " + parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
                    return result;
                }
                return ((String)entry.getKey()).substring(0, 1).toUpperCase() + ((String)entry.getKey()).substring(1);
            }
        }
        return enchant.getName();
    }

    public static String getBukkitName(String name) {
        if (eNames.containsKey(name.toLowerCase()))
            return eNames.get(name.toLowerCase());
        return name;
    }

    private static final Map<String, String> eNames = new HashMap<String, String>() {

    };

    private static final Hashtable<String, String> differentNames = new Hashtable<String, String>() {

    };
}
