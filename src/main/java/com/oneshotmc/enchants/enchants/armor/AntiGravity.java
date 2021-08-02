package me.classified.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchantmentapi.RomanNumeral;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AntiGravity extends CustomEnchantment {
    public AntiGravity() {
        super("Anti Gravity", GeneralUtils.boots, 2);
        this.max = 3;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyEquipEffect(Player player, int level) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, level + 2));
        player.sendMessage(Utils.color("&a[+] Anti Gravity " + RomanNumeral.numeralOf(level) + ": &7applying JUMP " + RomanNumeral.numeralOf(level)));
    }

    public void applyUnequipEffect(Player player, int level) {
        if (player.hasPotionEffect(PotionEffectType.JUMP))
            player.removePotionEffect(PotionEffectType.JUMP);
        player.sendMessage(Utils.color("&c[-] Anti Gravity " + RomanNumeral.numeralOf(level) + ": &7removing JUMP " + RomanNumeral.numeralOf(level)));
    }
}
