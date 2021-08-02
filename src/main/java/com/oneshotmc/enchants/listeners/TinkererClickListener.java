package com.oneshotmc.enchants.listeners;

import java.util.ArrayList;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.EnchantmentBook;
import com.oneshotmc.enchants.enchantmentapi.TieredEnchantments;
import com.oneshotmc.enchants.utils.CustomItems;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TinkererClickListener implements Listener {
    public static ArrayList<Player> acceptedTinker = new ArrayList<>();

    @EventHandler
    public void onTinkererClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (p.getOpenInventory().getTopInventory().getTitle().equals(Utils.color(OneShotEnchants.getConfigF().getString("tinkererTitle")))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
                return;
            if (e.getRawSlot() == 31 && e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.color("&8&a&lCLICK TO TINKER ITEMS &8"))) {
                    p.playSound(p.getLocation(), Sound.BLAZE_HIT, 1.0F, 2.0F);
            for (int i = 0; i <= 26; i++) {
                ItemStack itemStack = p.getOpenInventory().getTopInventory().getItem(i);
                if (itemStack != null && itemStack.getType() != Material.AIR &&
                        EnchantmentBook.isEnchantmentBook(itemStack)) {
                    CustomEnchantment customEnchantment = EnchantmentBook.getEnchantment(itemStack);
                    int tier = TieredEnchantments.getEnchantmentTier(customEnchantment.name());
                    itemStack.setType(Material.AIR);
                    p.getInventory().addItem(new ItemStack[] { CustomItems.getMysteryDustItem(tier) });
                }
            }
            acceptedTinker.add(p);
            p.closeInventory();
            p.sendMessage(Utils.color("&a&l(!) &7Tinkerer trade &a&nACCEPTED."));
            e.setCancelled(true);
            return;
        }
        Inventory ti = p.getOpenInventory().getTopInventory();
        Inventory pi = p.getOpenInventory().getBottomInventory();
        if (e.getClickedInventory() == ti &&
                e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE))
            e.setCancelled(true);
        if (e.getClickedInventory() == pi &&
                e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
            if (EnchantmentBook.isEnchantmentBook(e.getCurrentItem())) {
                if (e.getCurrentItem().getAmount() > 1) {
                    p.sendMessage(Utils.color("&c&l(!) &cThe tinkerer does not accept stacked items, please unstack them and try again."));
                    e.setCancelled(true);
                    return;
                }
                return;
            }
            p.sendMessage(Utils.color("&c&l(!) &cThe tinkerer will only accept unused enchantment orbs, please try a different item."));
            e.setCancelled(true);
            return;
        }
        return;
    }
}

    @EventHandler
    public void onTinkererClose(InventoryCloseEvent e) {
        Player p = (Player)e.getPlayer();
        if (p.getOpenInventory().getTopInventory().getTitle().equals(Utils.color(OneShotEnchants.getConfigF().getString("tinkererTitle")))) {
            Inventory topInv = p.getOpenInventory().getTopInventory();
            boolean hasItems = false;
            if (acceptedTinker.contains(p)) {
                acceptedTinker.remove(p);
                return;
            }
            for (int i = 0; i <= 26; i++) {
                if (EnchantmentBook.isEnchantmentBook(topInv.getItem(i))) {
                    p.getInventory().addItem(topInv.getItem(i));
                    hasItems = true;
                }
            }
            p.getOpenInventory().getTopInventory().clear();
            if (hasItems)
                p.sendMessage(Utils.color("&c&l(!) &cTrade canceled, your items have been returned to your inventory."));
        }
    }
}
