package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchantmentapi.RomanNumeral;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Aquatic extends CustomEnchantment {
    public Aquatic() {
        super("Aquatic", GeneralUtils.helmets, 2);
        this.max = 1;
        this.base = 20.0D;
        this.interval = 0.0D;
    }

    public void applyEquipEffect(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 2147483647, level - 1));
        player.sendMessage(Utils.color("&a[+] Aquatic " + RomanNumeral.numeralOf(level) + ": &7applying WATER_BREATHING " + RomanNumeral.numeralOf(level)));
    }

    public void applyUnequipEffect(Player player, int level) {
        if (player.hasPotionEffect(PotionEffectType.WATER_BREATHING))
            player.removePotionEffect(PotionEffectType.WATER_BREATHING);
        player.sendMessage(Utils.color("&c[-] Aquatic " + RomanNumeral.numeralOf(level) + ": &7removing WATER_BREATHING " + RomanNumeral.numeralOf(level)));
    }
}
