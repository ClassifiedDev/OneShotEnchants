package com.oneshotmc.enchants.enchants.weapons;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;

public class Training extends CustomEnchantment {
    public Training() {
        super("Training", GeneralUtils.weapons, 2);
        this.max = 10;
        this.base = 15.0D;
        this.interval = 6.0D;
        Bukkit.getPluginManager().registerEvents(new TrainingListener(), OneShotEnchants.getInstance());
    }
}
