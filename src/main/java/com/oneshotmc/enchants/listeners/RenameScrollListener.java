package me.classified.enchants.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.oneshotmc.enchants.utils.Config;
import com.oneshotmc.enchants.utils.CustomItems;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameScrollListener implements Listener {
    ArrayList<UUID> step1 = new ArrayList<>();

    HashMap<UUID, String> step2 = new HashMap<>();

    @EventHandler
    public void onRenameScroll(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack hand = p.getItemInHand();
        if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Config.renameScrollMaterial) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.renameScrollName))) || (e
                .getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Config.renameScrollMaterial) && hand.getItemMeta().getDisplayName().equals(Utils.color(Config.renameScrollName)))) {
            if (hand.getAmount() > 1) {
                hand.setAmount(hand.getAmount() - 1);
            } else {
                p.setItemInHand(new ItemStack(Material.AIR, 1));
            }
            p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 1.0F);
            this.step1.add(p.getUniqueId());
            p.sendMessage(Utils.color(""));
            p.sendMessage(Utils.color("          &f&l &e&lRENAME SCROLL USAGE &f&l"));
                    p.sendMessage(Utils.color("   &e&l&7Hold the item you want to rename."));
            p.sendMessage(Utils.color("   &e&l&7Type the name with color codes in chat."));
            p.sendMessage(Utils.color("   &e&l&7Type &aconfirm &7or &ccancel &7 to rename item. "));
            p.sendMessage(Utils.color(""));
            return;
        }
    }

    @EventHandler
    public void onRenameScrollChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        ItemStack hand = p.getItemInHand();
        String name = e.getMessage();
        if (this.step1.contains(p.getUniqueId())) {
            e.setCancelled(true);
            if (name.equalsIgnoreCase("cancel")) {
                this.step1.remove(p.getUniqueId());
                p.getInventory().addItem(new ItemStack[] { CustomItems.renameScrollItem });
                p.sendMessage(Utils.color("&c&l(!) &cCanceled item rename. Your rename scroll has been placed in your inventory."));
                return;
            }
            if (hand.getType().toString().contains("HELMET") || hand
                    .getType().toString().contains("CHESTPLATE") || hand
                    .getType().toString().contains("LEGGINGS") || hand
                    .getType().toString().contains("BOOTS") || hand
                    .getType().toString().contains("SWORD") || hand
                    .getType().toString().contains("AXE") || hand
                    .getType().toString().contains("PICKAXE") || hand
                    .getType().toString().contains("SHOVEL") || hand
                    .getType().toString().contains("BOW") || hand
                    .getType().toString().contains("HOE")) {
                if (name.length() > 64) {
                    p.sendMessage(Utils.color("&c&l(!) &cYour custom name can not be longer than 64 characters."));
                    return;
                }
                if (name.contains("&k") || name.contains("&K")) {
                    p.sendMessage(Utils.color("&c&l(!) &cYour custom name can not contain the") + " &k " + Utils.color("&ccolor code."));
                    return;
                }
                if (name.contains("[") || name.contains("]") || name.contains("{") || name.contains("}") || name.contains("(") || name.contains(")")) {
                    p.sendMessage(Utils.color("&c&l(!) &cYour custom name can not contain the following symbols: [, ], {, }, (, )"));
                    return;
                }
                p.sendMessage("");
                p.sendMessage(Utils.color("&a&l(!) &7Item name preview:&r " + name));
                p.sendMessage(Utils.color("&a&l(!) &7Please type '&aconfirm&7' to rename item or '&ccancel&7' to have your name tag returned."));
                p.sendMessage("");
                this.step1.remove(p.getUniqueId());
                this.step2.put(p.getUniqueId(), name);
                return;
            }
            p.sendMessage(Utils.color("&c&l(!) &cThis item can not be renamed!"));
            return;
        }
        if (this.step2.containsKey(p.getUniqueId())) {
            e.setCancelled(true);
            if (hand.getType().toString().contains("HELMET") || hand
                    .getType().toString().contains("CHESTPLATE") || hand
                    .getType().toString().contains("LEGGINGS") || hand
                    .getType().toString().contains("BOOTS") || hand
                    .getType().toString().contains("SWORD") || hand
                    .getType().toString().contains("AXE") || hand
                    .getType().toString().contains("PICKAXE") || hand
                    .getType().toString().contains("SHOVEL") || hand
                    .getType().toString().contains("BOW") || hand
                    .getType().toString().contains("HOE")) {
                if (e.getMessage().equalsIgnoreCase("cancel")) {
                    this.step2.remove(p.getUniqueId());
                    p.getInventory().addItem(new ItemStack[] { CustomItems.renameScrollItem });
                    p.sendMessage(Utils.color("&c&l(!) &cCanceled item rename. Your Rename Scroll has been returned to your inventory."));
                    return;
                }
                if (e.getMessage().equalsIgnoreCase("confirm")) {
                    p.sendMessage("");
                    p.sendMessage(Utils.color("&a&l(!) &7Your item has been &asuccessfully &7renamed to:&r " + (String)this.step2.get(p.getUniqueId())));
                    p.sendMessage("");
                    ItemMeta im = hand.getItemMeta();
                    im.setDisplayName(Utils.color(this.step2.get(p.getUniqueId())));
                    hand.setItemMeta(im);
                    p.updateInventory();
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                    this.step2.remove(p.getUniqueId());
                } else {
                    p.sendMessage("");
                    p.sendMessage(Utils.color("&a&l(!) &7Please type '&aconfirm&7' to rename item or '&ccancel&7' to start over."));
                    p.sendMessage("");
                    return;
                }
                return;
            }
            p.sendMessage(Utils.color("&c&l(!) &cThis item can not be renamed, please try a different item."));
            return;
        }
    }
}
