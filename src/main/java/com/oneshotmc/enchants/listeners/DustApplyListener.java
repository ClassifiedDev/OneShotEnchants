package com.oneshotmc.enchants.listeners;

import org.bukkit.Material;
import com.oneshotmc.enchants.utils.Config;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

public class DustApplyListener implements Listener {
    @EventHandler
    public void onDustApply(InventoryClickEvent e) {
        if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE))
            return;
        Player p = (Player)e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        ItemStack target = e.getCurrentItem();
        if (cursor.hasItemMeta() &&
                cursor.getItemMeta().hasDisplayName() && cursor.getItemMeta().hasLore() &&
                Utils.removeColor(cursor.getItemMeta().getDisplayName()).contains("Enchantment Dust") && (
                (String)cursor.getItemMeta().getLore().get(4)).contains(Utils.color("&7&oDrag n drop onto orb to apply.")) &&
                target.hasItemMeta() && target.getItemMeta().hasLore() && (
                (String)target.getItemMeta().getLore().get(1)).contains(Utils.color("&aSuccess Rate:")) &&
                target.getType().toString().contains("SACK")) {
            int targetTier = 0;
            int cursorTier = 0;
            if (target.getAmount() > 1)
                return;
            if (target.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier1BookColor + "&l&n")))
                targetTier = 1;
            if (target.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier2BookColor + "&l&n")))
                targetTier = 2;
            if (target.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier3BookColor + "&l&n")))
                targetTier = 3;
            if (target.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier4BookColor + "&l&n")))
                targetTier = 4;
            if (target.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier5BookColor + "&l&n")))
                targetTier = 5;
            if (target.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier6BookColor + "&l&n")))
                targetTier = 6;
            if (cursor.getItemMeta().getDisplayName().startsWith(Utils.color("&f")))
                cursorTier = 1;
            if (cursor.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier2BookColor)))
                cursorTier = 2;
            if (cursor.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier3BookColor)))
                cursorTier = 3;
            if (cursor.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier4BookColor)))
                cursorTier = 4;
            if (cursor.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier5BookColor)))
                cursorTier = 5;
            if (cursor.getItemMeta().getDisplayName().startsWith(Utils.color(Config.tier6BookColor)))
                cursorTier = 6;
            if (targetTier != cursorTier)
                return;
            int toAdd = 0;
            int current = 0;
            int total = 0;
            String toAddString = cursor.getItemMeta().getLore().get(2);
            toAddString = Utils.removeColor(toAddString);
            toAddString = toAddString.replaceAll("orb's success rate by ", "");
            toAddString = toAddString.replaceAll("%", "");
            toAdd = Integer.parseInt(toAddString);
            String currentString = target.getItemMeta().getLore().get(1);
            currentString = Utils.removeColor(currentString);
            currentString = currentString.replaceAll("Success Rate: ", "");
            currentString = currentString.replaceAll("%", "");
            current = Integer.parseInt(currentString);
            if (current == 100)
                return;
            if (toAdd + current >= 100) {
                total = 100;
            } else {
                total = toAdd + current;
            }
            ItemMeta tim = target.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            for (String s : tim.getLore())
                lore.add(s);
            lore.set(1, Utils.color("&aSuccess Rate: &7" + total + "%"));
            tim.setLore(lore);
            target.setItemMeta(tim);
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 8.0F);
            //ParticleEffect.SPELL.display((new Random()).nextFloat(), (new Random()).nextFloat(), (new Random()).nextFloat(), 0.8F, 85, p.getLocation().add(0.0D, 1.0D, 0.0D), 100.0D);
            if (cursor.getAmount() > 1) {
                cursor.setAmount(cursor.getAmount() - 1);
            } else {
                e.setCursor(new ItemStack(Material.AIR));
            }
            e.setCancelled(true);
            p.updateInventory();
            return;
        }
    }
}
