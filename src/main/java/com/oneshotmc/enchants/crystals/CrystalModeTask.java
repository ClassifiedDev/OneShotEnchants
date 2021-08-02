package com.oneshotmc.enchants.crystals;

import java.util.Random;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class CrystalModeTask implements Runnable {
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (CrystalAPI.hasCrystalMode(p)) {
                if (!CrystalAPI.hasCrystalAmount(p, 4)) {
                    CrystalAPI.toggleCrystalMode(p);
                    p.sendMessage("");
                    p.sendMessage(Utils.color("&c&lMYTHIC MODE: &c&l&nOFF&r &c&l"));
                            p.sendMessage(Utils.color("&7You have no crystals left!"));
                    p.sendMessage("");
                    p.playSound(p.getLocation(), Sound.FIRE_IGNITE, 1.0F, 1.2F);
                    continue;
                }
                int cost = getSoulCostPerSecond(p);
                if (cost > 0) {
                    CrystalAPI.removeCyrstalsFromGems(p, cost);
                    p.setMetadata("lastCrystalRemoveEvent", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
                    if (!p.hasMetadata("spectator"))
                        //ParticleEffect.SPELL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.5F, 50, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
                    p.playSound(p.getLocation(), Sound.EAT, 0.4F, 0.2F);
                    if (CrystalAPI.getAllCrystals(p) % 100 != 0)
                        continue;
                    p.sendMessage(Utils.color("&e&lCRYSTALS: &n" + CrystalAPI.getAllCrystals(p) + "&r&e&l "));
                    continue;
                }
                if (p.hasMetadata("spectator"))
                    continue;
                //ParticleEffect.ENCHANTMENT_TABLE.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 1.5F, 25, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
            }
        }
    }

    private int getSoulCostPerSecond(Player p) {
        int x = 0;
        ItemStack hand = p.getItemInHand();
        if (hand != null &&
                OneShotEnchants.itemHasEnchantment(hand, "Divine Immolation"))
            x += 5;
        return x;
    }
}
