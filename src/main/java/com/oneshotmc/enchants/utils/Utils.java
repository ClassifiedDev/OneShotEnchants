package com.oneshotmc.enchants.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.oneshotmc.enchants.utils.Config;
import com.oneshotmc.enchants.utils.EnchantmentItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
    public static boolean DEBUG_MODE = true;

    public static HashMap<String, EnchantmentItem> enchants = new HashMap<>();

    public static HashMap<String, EnchantmentItem> dischargeEnchants = new HashMap<>();

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String removeColor(String msg) {
        return ChatColor.stripColor(msg);
    }

    public static int randomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max - min + 1) + min;
        return randomNum;
    }

    public static double randDouble(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    public static void sendDebug(LivingEntity p, Player user, String enchant) {
        p.sendMessage(color("&b&lDEBUG:&8 (&7User: &f" + user.getName() + " ,&7 Enchant: &f" + enchant + "&8)"));
    }

    public static void sendPlayerDebug(Player p, String enchant) {
        p.sendMessage(color("&b&lENCHANTMENT DEBUG&8: &f" + enchant));
    }

    public static String getDateAndTime() {
        DateFormat df = new SimpleDateFormat("MM/dd/yy hh:mm:ss a");
        Date d = new Date();
        String fd = df.format(d);
        String[] fds = fd.split(" ");
        if (fds[0].startsWith("0"))
            fds[0] = fds[0].replaceFirst("0", "");
        if (fds[1].startsWith("0"))
            fds[1] = fds[1].replaceFirst("0", "");
        String ffd = fds[0] + " at " + fds[1] + " " + fds[2];
        return ffd;
    }

    public static boolean isInvEmpty(Player p) {
        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null && !item.getType().equals(Material.AIR))
                return false;
        }
        return true;
    }

    public static boolean isArmorInvEmpty(Player p) {
        for (ItemStack item : p.getInventory().getArmorContents()) {
            if (item != null && !item.getType().equals(Material.AIR))
                return false;
        }
        return true;
    }

    public static int getAvaliableInvSlots(Player p) {
        PlayerInventory pi = p.getInventory();
        int slots = 0;
        for (ListIterator<ItemStack> listIterator = pi.iterator(); listIterator.hasNext(); ) {
            ItemStack is = listIterator.next();
            if (is == null || is.getType().equals(Material.AIR))
                slots++;
        }
        return slots;
    }

    public static String calculateCooldown(long seconds) {
        int daysLeft = (int)TimeUnit.SECONDS.toDays(seconds);
        long hoursLeft = TimeUnit.SECONDS.toHours(seconds) - (daysLeft * 24);
        long minutesLeft = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
        long secondsLeft = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toSeconds(TimeUnit.SECONDS.toMinutes(seconds) * 60L);
        if (minutesLeft <= 0L && hoursLeft <= 0L && daysLeft <= 0)
            return secondsLeft + "s";
        if (hoursLeft <= 0L && daysLeft <= 0)
            return minutesLeft + "m " + secondsLeft + "s";
        if (daysLeft <= 0)
            return hoursLeft + "h " + minutesLeft + "m " + secondsLeft + "s";
        return daysLeft + "d " + hoursLeft + "h " + minutesLeft + "m " + secondsLeft + "s";
    }

    public static ItemStack itemBuilder(Material material, Short Data, String ItemName, ArrayList<String> Lore, Boolean Glowing) {
        ItemStack item = new ItemStack(material, 1, Data.shortValue());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color(ItemName));
        meta.setLore(Lore);
        if (Glowing.booleanValue()) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack itemBuilder(Material item, Short data, int amount, String name, String[] lore, boolean glowing) {
        ArrayList<String> itemLore = new ArrayList<>();
        ItemStack is = new ItemStack(item, amount, data.shortValue());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(color(name));
        for (String s : lore)
            itemLore.add(color(s));
        im.setLore(itemLore);
        if (glowing) {
            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        }
        is.setItemMeta(im);
        return is;
    }

    public static boolean hasEnchant(String enchant, ItemStack is) {
        if (is != null && is.getType() != Material.AIR && is
                .hasItemMeta() && is.getItemMeta().hasLore()) {
            ItemMeta im = is.getItemMeta();
            for (String s : im.getLore()) {
                s = ChatColor.stripColor(s);
                if (s.equalsIgnoreCase(enchant + " I"))
                    return true;
                if (s.equalsIgnoreCase(enchant + " II"))
                    return true;
                if (s.equalsIgnoreCase(enchant + " III"))
                    return true;
                if (s.equalsIgnoreCase(enchant + " IV"))
                    return true;
                if (s.equalsIgnoreCase(enchant + " V"))
                    return true;
                if (s.equalsIgnoreCase(enchant + " VI"))
                    return true;
                if (s.equalsIgnoreCase(enchant + " VII"))
                    return true;
                if (s.equalsIgnoreCase(enchant + " VIII"))
                    return true;
                if (s.equalsIgnoreCase(enchant + " IX"))
                    return true;
                if (s.equalsIgnoreCase(enchant + " X"))
                    return true;
            }
        }
        return false;
    }

    public static int getEnchantLvl(String enchant, ItemStack is) {
        if (is != null && is.getType() != Material.AIR && is
                .hasItemMeta() && is.getItemMeta().hasLore()) {
            ItemMeta im = is.getItemMeta();
            for (String s : im.getLore()) {
                s = removeColor(s);
                if (s.equalsIgnoreCase(enchant + " I"))
                    return 1;
                if (s.equalsIgnoreCase(enchant + " II"))
                    return 2;
                if (s.equalsIgnoreCase(enchant + " III"))
                    return 3;
                if (s.equalsIgnoreCase(enchant + " IV"))
                    return 4;
                if (s.equalsIgnoreCase(enchant + " V"))
                    return 5;
                if (s.equalsIgnoreCase(enchant + " VI"))
                    return 6;
                if (s.equalsIgnoreCase(enchant + " VII"))
                    return 7;
                if (s.equalsIgnoreCase(enchant + " VIII"))
                    return 8;
                if (s.equalsIgnoreCase(enchant + " IX"))
                    return 9;
                if (s.equalsIgnoreCase(enchant + " X"))
                    return 10;
            }
        }
        return 0;
    }

    public static List<Player> getNearbyPlayers(Player player, int distance) {
        List<Player> near = new ArrayList<>();
        int d2 = distance * distance;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld() == player.getWorld() && p.getLocation().distanceSquared(player.getLocation()) <= d2) {
                near.add(p);
                if (near.contains(player))
                    near.remove(player);
            }
        }
        return near;
    }

    public static List<Entity> getNearbyPlayers(Player player, double distance) {
        List<Entity> near = new ArrayList<>();
        double d2 = distance * distance;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld() == player.getWorld() && p.getLocation().distanceSquared(player.getLocation()) <= d2) {
                near.add(p);
                if (near.contains(player))
                    near.remove(player);
            }
        }
        return near;
    }

    public static void giveEnchant(Player p, EnchantmentItem ench, int success, int destroy) {
        ItemStack is = new ItemStack(ench.getMaterial(), 1, ench.getData().shortValue());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(color(ench.getName()));
        ArrayList<String> lore = new ArrayList<>();
        ench.setSuccess(success);
        ench.setDestroy(destroy);
        lore.add("");
        lore.add(color("&aSuccess Rate: &7" + ench.getSuccess() + "%"));
        lore.add(color("&cDestroy Rate: &7" + ench.getDestroy() + "%"));
        lore.add("");
        for (String s : ench.getLore())
            lore.add(color(s));
        lore.add("");
        lore.add(color("&9Enchant Target(s): &7" + ench.getTargets()));
        im.setLore(lore);
        if (Config.enchantmentBookGlowing) {
            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        }
        is.setItemMeta(im);
        if (p.getInventory().firstEmpty() > -1) {
            p.getInventory().addItem(new ItemStack[] { is });
        } else {
            p.sendMessage(color("&c&l(!) &cYour inventory was full so the enchantment has been dropped on the ground."));
            p.getWorld().dropItemNaturally(p.getLocation(), is);
        }
    }

    public static void giveEnchantViaBook(Player p, EnchantmentItem ench, int success, int destroy) {
        ItemStack is = new ItemStack(ench.getMaterial(), 1, ench.getData().shortValue());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(color(ench.getName()));
        ArrayList<String> lore = new ArrayList<>();
        ench.setSuccess(success);
        ench.setDestroy(destroy);
        lore.add("");
        lore.add(color("&aSuccess Rate: &7" + ench.getSuccess() + "%"));
        lore.add(color("&cDestroy Rate: &7" + ench.getDestroy() + "%"));
        lore.add("");
        for (String s : ench.getLore())
            lore.add(color(s));
        lore.add("");
        lore.add(color("&9Enchant Target(s): &7" + ench.getTargets()));
        im.setLore(lore);
        if (Config.enchantmentBookGlowing) {
            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        }
        is.setItemMeta(im);
        if (p.getInventory().firstEmpty() > -1) {
            p.getInventory().addItem(new ItemStack[] { is });
        } else {
            p.sendMessage(color("&c&l(!) &cYour inventory was full so the enchantment has been dropped on the ground."));
            p.getWorld().dropItemNaturally(p.getLocation(), is);
        }
        p.sendMessage(color("&3&l(!) &7You open the enchantment and receive: " + ench.getColor()
                .replaceAll("green", "")
                .replaceAll("teal", "")
                .replaceAll("yellow", "")
                .replaceAll("red", "")
                .replaceAll("pink", "") + removeColor(ench.getName().replaceAll(" Enchant Orb", "").replaceAll("&n&l", "")) + " &8(&7" + ench.getSuccess() + "%&8)"));
    }

    public static ItemStack toItemStack(EnchantmentItem ench, int success, int destroy) {
        ItemStack is = new ItemStack(ench.getMaterial(), 1, ench.getData().shortValue());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(color(ench.getName()));
        ArrayList<String> lore = new ArrayList<>();
        ench.setSuccess(success);
        ench.setDestroy(destroy);
        lore.add("");
        lore.add(color("&aSuccess Rate: &7" + ench.getSuccess() + "%"));
        lore.add(color("&cDestroy Rate: &7" + ench.getDestroy() + "%"));
        lore.add("");
        for (String s : ench.getLore())
            lore.add(color(s));
        lore.add("");
        lore.add(color("&9Enchant Target(s): &7" + ench.getTargets()));
        im.setLore(lore);
        if (Config.enchantmentBookGlowing) {
            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        }
        is.setItemMeta(im);
        return is;
    }

    public static EnchantmentItem getEnchant(ItemStack is) {
        ArrayList<String> lore = new ArrayList<>();
        for (String s : is.getItemMeta().getLore())
            lore.add(s);
        String[] loreArray = lore.<String>toArray(new String[0]);
        String color = color("");
        String pcolor = "";
        String cename = is.getItemMeta().getDisplayName();
        if (cename.startsWith(color("&a&l"))) {
            color = color("&a");
            pcolor = "green";
        }
        if (cename.startsWith(color("&b&l"))) {
            color = color("&b");
            pcolor = "blue";
        }
        if (cename.startsWith(color("&c&l"))) {
            color = color("&c");
            pcolor = "red";
        }
        if (cename.startsWith(color("&d&l"))) {
            color = color("&d");
            pcolor = "pink";
        }
        if (cename.startsWith(color("&e&l"))) {
            color = color("&e");
            pcolor = "yellow";
        }
        if (cename.startsWith(color("&f&l"))) {
            color = color("&f");
            pcolor = "white";
        }
        if (cename.startsWith(color("&1&l"))) {
            color = color("&1");
            pcolor = "deepblue";
        }
        if (cename.startsWith(color("&2&l"))) {
            color = color("&2");
            pcolor = "darkgreen";
        }
        if (cename.startsWith(color("&3&l"))) {
            color = color("&3");
            pcolor = "teal";
        }
        if (cename.startsWith(color("&4&l"))) {
            color = color("&4");
            pcolor = "darkred";
        }
        if (cename.startsWith(color("&5&l"))) {
            color = color("&5");
            pcolor = "purple";
        }
        if (cename.startsWith(color("&6&l"))) {
            color = color("&6");
            pcolor = "orange";
        }
        if (cename.startsWith(color("&7&l"))) {
            color = color("&7");
            pcolor = "lightgray";
        }
        if (cename.startsWith(color("&8&l"))) {
            color = color("&8");
            pcolor = "darkgray";
        }
        if (cename.startsWith(color("&9&l"))) {
            color = color("&9");
            pcolor = "darkblue";
        }
        if (cename.startsWith(color("&0&l"))) {
            color = color("&0");
            pcolor = "black";
        }
        if (cename.startsWith(color("&a&n&l"))) {
            color = color("&a");
            pcolor = "green";
        }
        if (cename.startsWith(color("&b&n&l"))) {
            color = color("&b");
            pcolor = "blue";
        }
        if (cename.startsWith(color("&c&n&l"))) {
            color = color("&c");
            pcolor = "red";
        }
        if (cename.startsWith(color("&d&n&l"))) {
            color = color("&d");
            pcolor = "pink";
        }
        if (cename.startsWith(color("&e&n&l"))) {
            color = color("&e");
            pcolor = "yellow";
        }
        if (cename.startsWith(color("&f&n&l"))) {
            color = color("&f");
            pcolor = "white";
        }
        if (cename.startsWith(color("&1&n&l"))) {
            color = color("&1");
            pcolor = "deepblue";
        }
        if (cename.startsWith(color("&2&n&l"))) {
            color = color("&2");
            pcolor = "darkgreen";
        }
        if (cename.startsWith(color("&3&n&l"))) {
            color = color("&3");
            pcolor = "teal";
        }
        if (cename.startsWith(color("&4&n&l"))) {
            color = color("&4");
            pcolor = "darkred";
        }
        if (cename.startsWith(color("&5&n&l"))) {
            color = color("&5");
            pcolor = "purple";
        }
        if (cename.startsWith(color("&6&n&l"))) {
            color = color("&6");
            pcolor = "orange";
        }
        if (cename.startsWith(color("&7&n&l"))) {
            color = color("&7");
            pcolor = "lightgray";
        }
        if (cename.startsWith(color("&8&n&l"))) {
            color = color("&8");
            pcolor = "darkgray";
        }
        if (cename.startsWith(color("&9&n&l"))) {
            color = color("&9");
            pcolor = "darkblue";
        }
        if (cename.startsWith(color("&0&n&l"))) {
            color = color("&0");
            pcolor = "black";
        }
        cename = removeColor(cename);
        cename = cename.replaceAll(" Enchant Orb", "");
        cename = color + cename;
        String success = lore.get(1);
        success = removeColor(success);
        success = success.replaceAll("Success Rate: ", "");
        success = success.replaceAll("%", "");
        String destroy = lore.get(2);
        destroy = removeColor(destroy);
        destroy = destroy.replaceAll("Destroy Rate: ", "");
        destroy = destroy.replaceAll("%", "");
        String targets = lore.get(lore.size() - 1);
        removeColor(targets);
        targets = targets.replaceAll("Enchant Targets: ", "");
        EnchantmentItem enchant = new EnchantmentItem(is.getType(), Short.valueOf(is.getDurability()), is.getItemMeta().getDisplayName(), loreArray, cename, Integer.parseInt(success), Integer.parseInt(destroy), targets, pcolor);
        return enchant;
    }

    public static void applyEnchant(ItemStack is, EnchantmentItem ench) {
        ItemMeta im = is.getItemMeta();
        ArrayList<String> isLore = new ArrayList<>();
        if (is.getItemMeta().hasLore()) {
            isLore.add("");
            for (String s : is.getItemMeta().getLore())
                isLore.add(color(s));
            String[] enchSplit = ench.getName().split(" ");
            for (int i = 0; i < isLore.size(); i++) {
                if (((String)isLore.get(i)).contains(removeColor(enchSplit[0])))
                    isLore.remove(i);
            }
            isLore.set(0, ench.getApplyLore());
            im.setLore(isLore);
            is.setItemMeta(im);
            return;
        }
        isLore.add(ench.getApplyLore());
        im.setLore(isLore);
        is.setItemMeta(im);
    }

    public static void removeRandomEnchant(ItemStack cursor, ItemStack target, Player p) {
        String percentStr = removeColor(cursor.getItemMeta().getLore().get(2));
        percentStr = percentStr.replaceAll("into a ", "");
        percentStr = percentStr.replaceAll("% enchantment orb.", "");
        int percent = Integer.parseInt(percentStr);
        ArrayList<String> targetLore = new ArrayList<>();
        ItemMeta tm = target.getItemMeta();
        int loreCount = 0;
        for (String s : target.getItemMeta().getLore())
            targetLore.add(s);
        loreCount = targetLore.size() - 1;
        int enchRemoveLine = randomInt(0, loreCount);
        String enchRemove = targetLore.get(enchRemoveLine);
        enchRemove = removeColor(enchRemove);
        if (enchRemove.contains(color("PROTECTED"))) {
            p.sendMessage("1");
            if (target.getItemMeta().getLore().size() == 1) {
                p.sendMessage("2");
                return;
            }
            removeRandomEnchant(cursor, target, p);
            p.sendMessage("3");
            return;
        }
        if (dischargeEnchants.containsKey(enchRemove)) {
            giveEnchant(p, dischargeEnchants.get(enchRemove), percent, 100);
            p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0F, 2.0F);
            targetLore.remove(enchRemoveLine);
            tm.setLore(targetLore);
            target.setItemMeta(tm);
            targetLore.clear();
            return;
        }
    }

    public static boolean containsEnchants(ItemStack target) {
        for (String s : target.getItemMeta().getLore()) {
            if (s.endsWith("I") || s.endsWith("V") || s.endsWith("X"))
                return true;
        }
        return false;
    }
}
