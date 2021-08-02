package com.oneshotmc.enchants.enchants.tools;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;

public class Skilling extends CustomEnchantment {
    public Skilling() {
        super("Skilling", GeneralUtils.tools, 2);
        this.max = 10;
        this.base = 15.0D;
        this.interval = 6.0D;
        Bukkit.getPluginManager().registerEvents(new SkillingListener(), OneShotEnchants.getInstance());
    }
}
