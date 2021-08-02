package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchantmentapi.RomanNumeral;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Glowing extends CustomEnchantment {
    public Glowing() {
        super("Glowing", GeneralUtils.helmets, 2);
        this.max = 1;
        this.base = 20.0D;
        this.interval = 0.0D;
    }

    public void applyEquipEffect(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2147483647, level - 1));
        player.sendMessage(Utils.color("&a[+] Glowing " + RomanNumeral.numeralOf(level) + ": &7applying NIGHT_VISION " + RomanNumeral.numeralOf(level)));
    }

    public void applyUnequipEffect(Player player, int level) {
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION))
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        player.sendMessage(Utils.color("&c[-] Glowing " + RomanNumeral.numeralOf(level) + ": &7removing NIGHT_VISION " + RomanNumeral.numeralOf(level)));
    }
}
