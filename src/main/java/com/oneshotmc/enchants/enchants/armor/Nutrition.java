package com.oneshotmc.enchants.enchants.armor;

import java.util.HashMap;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Nutrition extends CustomEnchantment {
    public Nutrition() {
        super("Nutrition", GeneralUtils.leggings, 9);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 5.0D;
        Bukkit.getPluginManager().registerEvents(new NutritionEvents(), OneShotEnchants.getInstance());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        NutritionEvents.playerLevels.put(player.getUniqueId().toString(), Integer.valueOf(enchantLevel));
        player.setMetadata("nutritionEnchantment", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(enchantLevel)));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        NutritionEvents.playerLevels.remove(player.getUniqueId().toString());
        player.removeMetadata("nutritionEnchantment", OneShotEnchants.getInstance());
    }

    private static class NutritionEvents implements Listener {
        private NutritionEvents() {}

        @EventHandler
        public void onPlayerFoodLevelChange(FoodLevelChangeEvent e) {
            if (e.getEntity() instanceof Player) {
                Player p = (Player)e.getEntity();
                if (e.getFoodLevel() > p.getFoodLevel() && playerLevels.containsKey(p.getUniqueId().toString())) {
                    int nutritionLevel = ((Integer)playerLevels.get(p.getUniqueId().toString())).intValue();
                    double modifier = 1.1D + nutritionLevel * 0.3D;
                    e.setFoodLevel((int)Math.round(e.getFoodLevel() * modifier));
                }
            }
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent e) {
            playerLevels.remove(e.getPlayer().getUniqueId().toString());
        }

        private static HashMap<String, Integer> playerLevels = new HashMap<>();
    }
}
