package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.enchantmentapi.RomanNumeral;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Drunk extends CustomEnchantment {
    public Drunk() {
        super("Drunk", GeneralUtils.helmets, 5);
        this.max = 4;
        this.base = 15.0D;
        this.interval = 6.0D;
    }

    public void applyEquipEffect(Player player, int level) {
        if (player.hasPotionEffect(PotionEffectType.SLOW) && getPotionEffectLevel(player, PotionEffectType.SLOW) <= level / 2)
            player.removePotionEffect(PotionEffectType.SLOW);
        if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING) && getPotionEffectLevel(player, PotionEffectType.SLOW_DIGGING) <= level / 2)
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2147483647, level / 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2147483647, Math.max(level - 2, 0)));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 2147483647, level / 2));
        player.sendMessage(Utils.color("&a[+] Drunk " + RomanNumeral.numeralOf(level) + ": &7applying INCREASE_DAMAGE " + RomanNumeral.numeralOf(level / 2 + 1)));
        player.sendMessage(Utils.color("&a[+] Drunk " + RomanNumeral.numeralOf(level) + ": &7applying SLOW_DIGGING " + RomanNumeral.numeralOf(level / 2 + 1)));
        player.sendMessage(Utils.color("&a[+] Drunk " + RomanNumeral.numeralOf(level) + ": &7applying SLOW " + RomanNumeral.numeralOf(Math.max(level - 1, 0))));
    }

    public void applyUnequipEffect(Player player, int level) {
        if (player.hasPotionEffect(PotionEffectType.SLOW))
            player.removePotionEffect(PotionEffectType.SLOW);
        if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING))
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.sendMessage(Utils.color("&c[-] Drunk " + RomanNumeral.numeralOf(level) + ": &7removing INCREASE_DAMAGE " + RomanNumeral.numeralOf(level / 2 + 1)));
        player.sendMessage(Utils.color("&c[-] Drunk " + RomanNumeral.numeralOf(level) + ": &7removing SLOW_DIGGING " + RomanNumeral.numeralOf(level / 2 + 1)));
        player.sendMessage(Utils.color("&c[-] Drunk " + RomanNumeral.numeralOf(level) + ": &7removing SLOW " + RomanNumeral.numeralOf(Math.max(level - 1, 0))));
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double blackoutChance = level * 0.01D;
        if (Math.random() < blackoutChance) {
            Player p = (Player)player;
            p.sendMessage(Utils.color("&e&l(!) &7You're starting to feel very dizzy..."));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, level * 40, Math.max(0, level - 3)));
        }
    }

    private int getPotionEffectLevel(Player p, PotionEffectType pet) {
        for (PotionEffect pe : p.getActivePotionEffects()) {
            if (pe.getType() == pet)
                return pe.getAmplifier();
        }
        return 0;
    }
}
