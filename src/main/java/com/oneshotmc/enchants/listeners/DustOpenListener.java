package com.oneshotmc.enchants.listeners;

import com.oneshotmc.enchants.utils.CustomItems;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class DustOpenListener implements Listener {
    @EventHandler
    public void onDustOpen(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack hand = p.getItemInHand();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&f&lCommon Dust Pouch &7(Right-Click)"))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&f&lCommon Dust Pouch &7(Right-Click)")))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier1", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier1", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&a&lUncommon Dust Pouch &7(Right-Click)"))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&a&lUncommon Dust Pouch &7(Right-Click)")))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier2", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier2", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&b&lRare Dust Pouch &7(Right-Click)"))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&b&lRare Dust Pouch &7(Right-Click)")))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier3", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier3", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&e&lUltimate Dust Pouch &7(Right-Click)"))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&e&lUltimate Dust Pouch &(Right-Click)")))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier4", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier4", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&6&lLegendary Dust Pouch &7(Right-Click)"))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&6&lLegendary  Dust Pouch &7(Right-Click)")))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier5", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier5", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&c&lMythic Dust Pouch &7(Right-Click)"))) || (e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK) && hand.hasItemMeta() && hand.getType().equals(Material.FIREBALL) && hand.getItemMeta().getDisplayName().equals(Utils.color("&c&lMythic Dust Pouch &7(Right-Click)")))) {
            if (hand.getAmount() > 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    hand.setAmount(hand.getAmount() - 1);
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier6", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            if (hand.getAmount() == 1) {
                if (p.getInventory().firstEmpty() > -1) {
                    p.setItemInHand(new ItemStack(Material.AIR, 1));
                    if (Utils.randomInt(1, 100) <= 50) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 2.0F);
                        p.getInventory().addItem(CustomItems.dustItem("tier6", Utils.randomInt(1, 10)));
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
                    p.getInventory().addItem(CustomItems.uselessPowderItem);
                } else {
                    p.sendMessage(Utils.color("&c&l(!) &cYou can not open this dust pouch because your inventory is full!"));
                    return;
                }
                return;
            }
            return;
        }
    }
}
