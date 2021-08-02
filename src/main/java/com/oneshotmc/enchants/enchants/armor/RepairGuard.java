package com.oneshotmc.enchants.enchants.armor;

import java.util.HashMap;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.MinecraftServerUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class RepairGuard extends CustomEnchantment {
    HashMap<String, BukkitTask> playerTasks;

    public RepairGuard() {
        super("Repair Guard", GeneralUtils.armor, 2);
        this.playerTasks = new HashMap<>();
        this.max = 3;
        this.base = 10.0D;
        this.interval = 5.0D;
    }

    public void applyUnequipEffect(Player player, int level, ItemStack item) {
        double duraPercent = 0.15D;
        if (level == 2)
            duraPercent = 0.2D;
        if (level == 3)
            duraPercent = 0.25D;
        if (getDurabilityPercent(item) <= duraPercent) {
            int cooldownTicks = 600;
            if (player.hasMetadata("lastRepairGuardProc") && MinecraftServerUtils.getCurrentServerTick() - ((MetadataValue)player.getMetadata("lastRepairGuardProc").get(0)).asLong() < 600L)
                return;
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, (2 + level) * 20, 2 + level), true);
            player.setMetadata("lastRepairGuardProc", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick())));
        }
    }

    private float getDurabilityPercent(ItemStack i) {
        if (i.getDurability() == 0 || i.getType().getMaxDurability() == 0)
            return 1.0F;
        return (i.getDurability() / i.getType().getMaxDurability());
    }
}
