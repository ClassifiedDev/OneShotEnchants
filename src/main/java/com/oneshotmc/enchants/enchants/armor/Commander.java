package com.oneshotmc.enchants.enchants.armor;

import java.util.concurrent.ConcurrentHashMap;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Commander extends CustomEnchantment {
    protected static ConcurrentHashMap<String, Integer> commanders;

    private CommanderTask commanderTask;

    public Commander() {
        super("Commander", GeneralUtils.armor, 2);
        this.max = 5;
        this.base = 20.0D;
        this.interval = 5.0D;
        commanders = new ConcurrentHashMap<>();
        Bukkit.getPluginManager().registerEvents(new CommanderListener(), OneShotEnchants.getInstance());
        (this.commanderTask = new CommanderTask()).runTaskTimer(OneShotEnchants.getInstance(), 20L, 160L);
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        commanders.put(player.getName(), Integer.valueOf(enchantLevel));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        commanders.remove(player.getName());
    }
}
