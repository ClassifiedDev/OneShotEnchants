package com.oneshotmc.enchants.enchants.armor;


import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchantmentapi.RomanNumeral;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Obsidianshield extends CustomEnchantment {
    public Obsidianshield() {
        super("Obsidianshield", GeneralUtils.armor, 2);
        this.max = 1;
        this.base = 30.0D;
        this.interval = 0.0D;
    }

    public void applyEquipEffect(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2147483647, level - 1));
        player.sendMessage(Utils.color("&a[+] Obsidianshield " + RomanNumeral.numeralOf(level) + ": &7applying FIRE_RESISTANCE " + RomanNumeral.numeralOf(level)));
    }

    public void applyUnequipEffect(Player player, int level) {
        if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE))
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        player.sendMessage(Utils.color("&c[-] Obsidianshield " + RomanNumeral.numeralOf(level) + ": &7removing FIRE_RESISTANCE " + RomanNumeral.numeralOf(level)));
    }
}
