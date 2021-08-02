package com.oneshotmc.enchants.enchantmentapi;

import com.oneshotmc.enchants.OneShotEnchants;

import java.util.HashMap;


public class EnchantmentDescriptions {
    private static HashMap<String, String> descriptions = null;

    private static void loadConfig() {
        descriptions = new HashMap<>();
        for (String s : OneShotEnchants.getDescriptions().getStringList("descriptions")) {
            if (s != null && s.contains("=")) {
                String key = s.split("=")[0].toLowerCase();
                String value = s.split("=")[1];
                descriptions.put(key, value);
            }
        }
    }

    public static String getDescription(CustomEnchantment ce) {
        if (descriptions == null)
            loadConfig();
        return descriptions.containsKey(ce.name().toLowerCase()) ? descriptions.get(ce.name().toLowerCase()) : "???";
    }
}
