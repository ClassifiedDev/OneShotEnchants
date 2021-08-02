package com.oneshotmc.enchants.commands;

import java.util.ArrayList;
import java.util.Map;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.EnchantmentEquip;
import com.oneshotmc.enchants.listeners.EnchantmentListener;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class ApplyCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            boolean op = p.isOp();
            if (!op && p.hasMetadata("lastApplyCommand") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastApplyCommand").get(0)).asLong() < 5000L) {
                p.sendMessage(Utils.color("&c&l(!) &cYou can only use /apply once every 5 seconds."));
                return true;
            }
            p.setMetadata("lastApplyCommand", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
            ArrayList<ItemStack> armor = new ArrayList<>();
            for (ItemStack i : p.getEquipment().getArmorContents())
                armor.add(i);
            for (Map.Entry<CustomEnchantment, Integer> data : (Iterable<Map.Entry<CustomEnchantment, Integer>>) EnchantmentListener.getValidEnchantments((LivingEntity)p, armor).entries())
                ((CustomEnchantment)data.getKey()).applyUnequipEffect(p, ((Integer)data.getValue()).intValue());
            for (Map.Entry<CustomEnchantment, Integer> data : (Iterable<Map.Entry<CustomEnchantment, Integer>>)EnchantmentListener.getValidEnchantments((LivingEntity)p, armor).entries())
                ((CustomEnchantment)data.getKey()).applyEquipEffect(p, ((Integer)data.getValue()).intValue());
            (new EnchantmentEquip(p)).runTaskLater(OneShotEnchants.getInstance(), 20L);
            p.sendMessage(Utils.color("&a&l(!) &aAll your custom enchantment buffs have been refreshed."));
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 0.6F);
        }
        return true;
    }
}
