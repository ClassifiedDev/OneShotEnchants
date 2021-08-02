package com.oneshotmc.enchants.enchants.armor;

import java.util.concurrent.ConcurrentHashMap;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchants.armor.LeadershipListener;
import com.oneshotmc.enchants.enchants.armor.LeadershipTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Leadership extends CustomEnchantment {
    protected static ConcurrentHashMap<String, Integer> Leaderships;

    private com.oneshotmc.enchants.enchants.armor.LeadershipTask LeadershipTask;

    public Leadership() {
        super("Leadership", GeneralUtils.armor, 2);
        this.max = 5;
        this.base = 20.0D;
        this.interval = 5.0D;
        Leaderships = new ConcurrentHashMap<>();
        Bukkit.getPluginManager().registerEvents(new LeadershipListener(), OneShotEnchants.getInstance());
        (this.LeadershipTask = new LeadershipTask()).runTaskTimer(OneShotEnchants.getInstance(), 20L, 120L);
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        Leaderships.put(player.getName(), Integer.valueOf(enchantLevel));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        Leaderships.remove(player.getName());
    }
}
