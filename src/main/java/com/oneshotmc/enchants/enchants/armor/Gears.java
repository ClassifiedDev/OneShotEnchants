package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchantmentapi.RomanNumeral;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Gears extends CustomEnchantment {
    public Gears() {
        super("Gears", GeneralUtils.boots, 2);
        this.max = 3;
        this.base = 7.0D;
        this.interval = 6.0D;
    }

    public void applyEquipEffect(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, level - 1));
        player.sendMessage(Utils.color("&a[+] Gears " + RomanNumeral.numeralOf(level) + ": &7applying SPEED " + RomanNumeral.numeralOf(level)));
    }

    public void applyUnequipEffect(Player player, int level) {
        if (player.hasPotionEffect(PotionEffectType.SPEED))
            player.removePotionEffect(PotionEffectType.SPEED);
        player.sendMessage(Utils.color("&c[-] Gears " + RomanNumeral.numeralOf(level) + ": &7removing SPEED " + RomanNumeral.numeralOf(level)));
    }
}
