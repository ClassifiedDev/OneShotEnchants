package com.oneshotmc.enchants.enchants.armor;

import java.util.concurrent.ConcurrentHashMap;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CreeperArmor extends CustomEnchantment {
    protected static ConcurrentHashMap<String, Integer> creeperArmor;

    public CreeperArmor() {
        super("Creeper Armor", GeneralUtils.armor, 3);
        this.max = 3;
        this.base = 25.0D;
        this.interval = 0.0D;
        creeperArmor = new ConcurrentHashMap<>();
        Bukkit.getPluginManager().registerEvents(new CreeperArmorListener(), OneShotEnchants.getInstance());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        creeperArmor.put(player.getName(), Integer.valueOf(enchantLevel));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        creeperArmor.remove(player.getName());
    }
}
