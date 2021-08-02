package com.oneshotmc.enchants.enchants.axes;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blessed extends CustomEnchantment {
    private static List<PotionEffectType> debuffs;

    public Blessed() {
        super("Blessed", GeneralUtils.axe, 7);
        this.max = 4;
        this.base = 30.0D;
        this.interval = 10.0D;
        debuffs = Lists.newArrayList(new PotionEffectType[] { PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.HARM, PotionEffectType.HUNGER, PotionEffectType.POISON, PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.WEAKNESS, PotionEffectType.WITHER });
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!(target instanceof Player) || !(user instanceof Player))
            return;
        Player playerAttacker = (Player)user;
        double random = Math.random();
        double chance = 0.03D * enchantLevel;
        if (random < chance) {
            if (user.hasMetadata("deepWounds") && ((MetadataValue)user.getMetadata("deepWounds").get(0)).asLong() > System.currentTimeMillis()) {
                playerAttacker.sendMessage(Utils.color("&4&lDEEP WOUNDS &7(NO BLESS)&4&l "));
                return;
            }
            Bleed.clearBleedEffect((LivingEntity)playerAttacker);
            playerAttacker.playSound(playerAttacker.getLocation(), Sound.SPLASH, 1.2F, 2.0F);
            blessPlayer(playerAttacker);
        }
    }

    public static void blessPlayer(Player player) {
        blessPlayer(player, false);
    }

    public static void blessPlayer(Player player, boolean respectDeepWounds) {
        List<PotionEffectType> playerDebuffs = getActiveDebuffs(player);
        if (playerDebuffs.size() > 0) {
            if (respectDeepWounds && player.hasMetadata("deepWounds") && ((MetadataValue)player.getMetadata("deepWounds").get(0)).asLong() > System.currentTimeMillis()) {
                player.sendMessage(Utils.color("&4&lDEEP WOUNDS &7(NO BLESS)&4&l "));
                return;
            }
            player.sendMessage(Utils.color("&e&lBLESSED "));
            for (PotionEffectType effectType : playerDebuffs) {
                if (player.hasPotionEffect(effectType))
                    player.removePotionEffect(effectType);
            }
        }
    }

    public static List<PotionEffectType> getActiveDebuffs(Player p) {
        List<PotionEffectType> result = new ArrayList<>();
        for (PotionEffect pe : p.getActivePotionEffects()) {
            if (debuffs.contains(pe.getType()))
                result.add(pe.getType());
        }
        return result;
    }
}
