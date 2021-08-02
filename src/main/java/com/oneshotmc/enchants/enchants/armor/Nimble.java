package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;

public class Nimble extends CustomEnchantment {
    public Nimble() {
        super("Nimble", GeneralUtils.boots, 2);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 6.0D;
        Bukkit.getPluginManager().registerEvents(new NimbleListener(), OneShotEnchants.getInstance());
    }
}
