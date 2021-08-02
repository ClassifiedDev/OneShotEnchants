package com.oneshotmc.enchants.commands;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.EnchantmentBook;
import com.oneshotmc.enchants.utils.CustomItems;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GiveCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("giveenchant")) {
            if (!sender.hasPermission("oneshotenchants.give")) {
                sender.sendMessage(Utils.color("&c&l(!) &cYou do not have permission to use that command."));
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage(Utils.color("&c&l(!) &cUsage: &7/giveenchant <player> <enchant> <level> <amount> [<success/random> <destroy/random>]"));
                return false;
            }
            if (args.length == 4) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.color("&c&l(!) &cThe specified player could not be found, please try again."));
                    return false;
                }
                PlayerInventory ti = target.getInventory();
                CustomEnchantment ce = OneShotEnchants.getEnchantment(args[1].replace("_", " "));
                if (ce == null) {
                    StringBuilder builder = new StringBuilder();
                    for (CustomEnchantment enchantment : OneShotEnchants.getEnchantments())
                        builder.append(Utils.color(" " + enchantment.name().replaceAll(" ", "_").toUpperCase()));
                    sender.sendMessage(Utils.color("&c&l(!) &cInvalid enchantment name, please try again."));
                    sender.sendMessage(Utils.color("&8&l&aValid Enchantments include:&7" + builder.toString().replaceAll(" ", ", ")).replaceFirst(",", " "));
                    return false;
                }
                int level = Integer.parseInt(args[2]);
                if (level > ce.getMaxLevel()) {
                    sender.sendMessage(Utils.color("&c&l(!) &cMax Enchantment Level: &7" + ce.getMaxLevel()));
                    return false;
                }
                int amount = Integer.parseInt(args[3]);
                for (int i = 0; i < amount; i++) {
                    if (target.getInventory().firstEmpty() != -1) {
                        target.getInventory().addItem(EnchantmentBook.getEnchantmentBook(ce, level));
                        target.updateInventory();
                    } else {
                        target.getWorld().dropItem(target.getLocation(), EnchantmentBook.getEnchantmentBook(ce, level));
                        target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the enchantment has been dropped on the ground."));
                    }
                }
            }
            if (args.length == 6) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.color("&c&l(!) &cThe specified player could not be found, please try again."));
                    return false;
                }
                PlayerInventory ti = target.getInventory();
                CustomEnchantment ce = OneShotEnchants.getEnchantment(args[1].replace("_", " "));
                if (ce == null) {
                    sender.sendMessage(Utils.color("&c&l(!) Invalid enchantment name, please try again."));
                    return false;
                }
                int level = Integer.parseInt(args[2]);
                if (level > ce.getMaxLevel()) {
                    sender.sendMessage(Utils.color("&c&l(!) Max Enchantment Level: &7" + ce.getMaxLevel()));
                    return false;
                }
                int amount = Integer.parseInt(args[3]);
                int success = Integer.parseInt(args[4]);
                int destroy = Integer.parseInt(args[5]);
                for (int i = 0; i < amount; i++) {
                    if (target.getInventory().firstEmpty() != -1) {
                        target.getInventory().addItem(EnchantmentBook.getEnchantmentBook(ce, level, success, destroy));
                        target.updateInventory();
                    } else {
                        target.getWorld().dropItem(target.getLocation(), EnchantmentBook.getEnchantmentBook(ce, level, success, destroy));
                        target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the enchantment has been dropped on the ground."));
                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("giveenchantitem")) {
            if (!sender.hasPermission("oneshotenchants.give")) {
                sender.sendMessage(Utils.color("&c&l(!) &cYou do not have permission to use that command."));
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage(Utils.color("&c&l(!) &cUsage: &7/giveenchantitem <player> <item> <amount> [rarity/percent/amount]"));
                return false;
            }
            if (args.length == 3) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.color("&c&l(!) &cThe specified player could not be found, please try again."));
                    return false;
                }
                PlayerInventory ti = target.getInventory();
                int amount = Integer.parseInt(args[2]);
                if (args[1].equalsIgnoreCase("protection")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.protectionItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.protectionItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("disenchant")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.disenchantItem(Utils.randomInt(25, 100)));
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.disenchantItem(Utils.randomInt(25, 100)));
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("organization")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.organizeScrollItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.organizeScrollItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("rename")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.renameScrollItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.renameScrollItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("common")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier1MysteryEnchantmentItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier1MysteryEnchantmentItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("uncommon")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier2MysteryEnchantmentItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier2MysteryEnchantmentItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("rare")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier3MysteryEnchantmentItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier3MysteryEnchantmentItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("ultimate")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier4MysteryEnchantmentItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier4MysteryEnchantmentItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("legendary")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier5MysteryEnchantmentItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier5MysteryEnchantmentItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("mythic")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier6MysteryEnchantmentItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier6MysteryEnchantmentItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("common_mystery_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier1MysteryDustItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier1MysteryDustItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("uncommon_mystery_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier2MysteryDustItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier2MysteryDustItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("rare_mystery_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier3MysteryDustItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier3MysteryDustItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("ultimate_mystery_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier4MysteryDustItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier4MysteryDustItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("legendary_mystery_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier5MysteryDustItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier5MysteryDustItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("mythic_mystery_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.tier6MysteryDustItem);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.tier6MysteryDustItem);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("looting_IV")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.lootingIV);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.lootingIV);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("looting_V")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.lootingV);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.lootingV);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("depth_Strider_I")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.depthStriderI);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.depthStriderI);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("depth_Strider_II")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.depthStriderII);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.depthStriderII);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("depth_Strider_III")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.depthStriderIII);
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.depthStriderIII);
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                sender.sendMessage(" ");
                sender.sendMessage(Utils.color("&c&l(!) &cInvalid item, an item with that name could not be found!"));
                sender.sendMessage(" ");
                sender.sendMessage(Utils.color("&8&l&aA list of valid items include: &7PROTECTION, DISENCHANT, ORGANIZATION, RENAME, COMMON, UNCOMMON, RARE, ULTIMATE, LEGENDARY, MYTHIC, COMMON_MYSTERY_DUST, UNCOMMON_MYSTERY_DUST, RARE_MYSTERY_DUST, ULTIMATE_MYSTERY_DUST, LEGENDARY_MYSTERY_DUST, MYTHIC_MYSTERY_DUST, COMMON_DUST, UNCOMMON_DUST, RARE_DUST, ULTIMATE_DUST, LEGENDARY_DUST, MYTHIC_DUST, LOOTING_IV, LOOTING_V, DEPTH_STRIDER_I, DEPTH_STRIDER_II, DEPTH_STRIDER_III"));
                return false;
            }
            if (args.length == 4) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Utils.color("&c&l(!) &cPlayer could not be found!"));
                    return false;
                }
                PlayerInventory ti = target.getInventory();
                int amount = Integer.parseInt(args[2]);
                if (args[1].equalsIgnoreCase("disenchant")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.disenchantItem(Integer.parseInt(args[3])));
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.disenchantItem(Integer.parseInt(args[3])));
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("common_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.dustItem("tier1", Integer.parseInt(args[3])));
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.dustItem("tier1", Integer.parseInt(args[3])));
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("uncommon_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.dustItem("tier2", Integer.parseInt(args[3])));
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.dustItem("tier2", Integer.parseInt(args[3])));
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("rare_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.dustItem("tier3", Integer.parseInt(args[3])));
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.dustItem("tier3", Integer.parseInt(args[3])));
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("ultimate_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.dustItem("tier4", Integer.parseInt(args[3])));
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.dustItem("tier4", Integer.parseInt(args[3])));
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("legendary_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.dustItem("tier5", Integer.parseInt(args[3])));
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.dustItem("tier5", Integer.parseInt(args[3])));
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("mythic_dust")) {
                    for (int i = 0; i < amount; i++) {
                        if (target.getInventory().firstEmpty() != -1) {
                            target.getInventory().addItem(CustomItems.dustItem("tier6", Integer.parseInt(args[3])));
                            target.updateInventory();
                        } else {
                            target.getWorld().dropItem(target.getLocation(), CustomItems.dustItem("tier6", Integer.parseInt(args[3])));
                            target.sendMessage(Utils.color("&c&l(!) &cYour inventory was full so the item has been dropped on the ground."));
                        }
                    }
                    return false;
                }
                sender.sendMessage(" ");
                sender.sendMessage(Utils.color("&c&l(!) &cInvalid item, an item with that name could not be found!"));
                sender.sendMessage(" ");
                sender.sendMessage(Utils.color("&8&l&aA list of valid items include: &7PROTECTION, DISENCHANT, ORGANIZATION, RENAME, COMMON, UNCOMMON, RARE, ULTIMATE, LEGENDARY, MYTHIC, COMMON_MYSTERY_DUST, UNCOMMON_MYSTERY_DUST, RARE_MYSTERY_DUST, ULTIMATE_MYSTERY_DUST, LEGENDARY_MYSTERY_DUST, MYTHIC_MYSTERY_DUST, COMMON_DUST, UNCOMMON_DUST, RARE_DUST, ULTIMATE_DUST, LEGENDARY_DUST, MYTHIC_DUST, LOOTING_IV, LOOTING_V, DEPTH_STRIDER_I, DEPTH_STRIDER_II, DEPTH_STRIDER_III"));
                return false;
            }
        }
        return false;
    }
}
