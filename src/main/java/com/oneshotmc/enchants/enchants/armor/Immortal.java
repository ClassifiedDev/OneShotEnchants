package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;

public class Immortal extends CustomEnchantment {
    public Immortal() {
        super("Immortal", GeneralUtils.armor, 2);
        this.max = 4;
        this.base = 15.0D;
        this.interval = 3.0D;
        Bukkit.getPluginManager().registerEvents(new ImmortalListener(), OneShotEnchants.getInstance());
    }
}
