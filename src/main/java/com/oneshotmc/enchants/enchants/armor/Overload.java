package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchantmentapi.RomanNumeral;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Overload extends CustomEnchantment {
    public Overload() {
        super("Overload", GeneralUtils.armor, 5);
        this.max = 1;
        this.base = 15.0D;
        this.interval = 6.0D;
    }

    public void applyEquipEffect(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 2147483647, level - 1));
        player.sendMessage(Utils.color("&a[+] Overload " + RomanNumeral.numeralOf(level) + ": &7applying HEALTH_BOOST " + RomanNumeral.numeralOf(level)));
    }

    public void applyUnequipEffect(Player player, int level) {
        if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST))
            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
        player.sendMessage(Utils.color("&c[-] Overload " + RomanNumeral.numeralOf(level) + ": &7removing HEALTH_BOOST " + RomanNumeral.numeralOf(level)));
    }
}
