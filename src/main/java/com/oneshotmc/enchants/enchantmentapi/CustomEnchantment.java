package com.oneshotmc.enchants.enchantmentapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.utils.Utils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomEnchantment implements Comparable<CustomEnchantment> {
    public static final String DEFAULT_GROUP = "Default";

    protected List<String> suffixGroups = new ArrayList<>();

    protected final String enchantName;

    protected String description;

    protected Material[] naturalItems;

    protected Map<MaterialClass, Integer> weight;

    protected boolean isEnabled;

    protected String group;

    protected double interval;

    protected double base;

    protected int max;

    public int uniqueId;

    public int cachedTier = -1;

    public CustomEnchantment(String name) {
        this(name, null, new Material[0], "Default", 5);
    }

    public CustomEnchantment(String name, String[] naturalItems) {
        this(name, null, MaterialsParser.toMaterial(naturalItems), "Default", 5);
    }

    public CustomEnchantment(String name, Material[] naturalItems) {
        this(name, null, naturalItems, "Default", 5);
    }

    public CustomEnchantment(String name, String description) {
        this(name, description, new Material[0], "Default", 5);
    }

    public CustomEnchantment(String name, String[] naturalItems, int weight) {
        this(name, null, MaterialsParser.toMaterial(naturalItems), "Default", weight);
    }

    public CustomEnchantment(String name, Material[] naturalItems, int weight) {
        this(name, null, naturalItems, "Default", weight);
    }

    public CustomEnchantment(String name, Material[] naturalItems, String group) {
        this(name, null, naturalItems, group, 5);
    }

    public CustomEnchantment(String name, String description, Material[] naturalItems) {
        this(name, description, naturalItems, "Default", 5);
    }

    public CustomEnchantment(String name, String description, String group) {
        this(name, description, new Material[0], group, 5);
    }

    public CustomEnchantment(String name, String description, int weight) {
        this(name, description, new Material[0], "Default", 5);
    }

    public CustomEnchantment(String name, String description, Material[] naturalItems, String group) {
        this(name, description, naturalItems, group, 5);
    }

    public CustomEnchantment(String name, String description, Material[] naturalItems, int weight) {
        this(name, description, naturalItems, "Default", 5);
    }

    public CustomEnchantment(String name, String description, String group, int weight) {
        this(name, description, new Material[0], group, weight);
    }

    public CustomEnchantment(String name, Material[] naturalItems, String group, int weight) {
        this(name, null, naturalItems, group, weight);
    }

    public static int nextId = 0;

    public CustomEnchantment(String name, String description, Material[] naturalItems, String group, int weight) {
        Validate.notEmpty(name, "Your Enchantment needs a name!");
        Validate.notNull(naturalItems, "Input an empty array instead of \"null\"!");
        Validate.isTrue((weight >= 0), "Weight can't be negative!");
        this.enchantName = name;
        this.description = description;
        this.naturalItems = naturalItems;
        this.isEnabled = true;
        this.group = group;
        this.max = 1;
        this.base = 1.0D;
        this.interval = 10.0D;
        this.uniqueId = nextId++;
        this.weight = new HashMap<>();
        this.weight.put(MaterialClass.DEFAULT, Integer.valueOf(weight));
    }

    public String name() {
        return this.enchantName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public int getEnchantLevel(int expLevel) {
        for (int i = this.max; i >= 1; i--) {
            if (expLevel >= this.base + this.interval * (i - 1))
                return i;
        }
        return 0;
    }

    public int getMaxLevel() {
        return this.max;
    }

    public void setMaxLevel(int value) {
        this.max = value;
    }

    public double getBase() {
        return this.base;
    }

    public void setBase(double value) {
        this.base = value;
    }

    public double getInterval() {
        return this.interval;
    }

    public void setInterval(double value) {
        this.interval = value;
    }

    public List<String> getSuffixGroups() {
        return this.suffixGroups;
    }

    public int getCostPerLevel(boolean withBook) {
        int costIndex = ((Integer)this.weight.get(MaterialClass.DEFAULT)).intValue() * this.max;
        int divisor = withBook ? 2 : 1;
        return
                (((Integer)this.weight.get(MaterialClass.DEFAULT)).intValue() == 1) ? (8 / divisor) : ((costIndex < 10) ? (4 / divisor) : ((costIndex < 30) ? (2 / divisor) : 1));
    }

    public int getTier() {
        if (this.cachedTier == -1)
            this.cachedTier = TieredEnchantments.getEnchantmentTier(name());
        return this.cachedTier;
    }

    public void setNaturalMaterials(Material[] materials) {
        this.naturalItems = materials;
    }

    public String[] getNaturalItems() {
        String[] natItems = new String[this.naturalItems.length];
        for (int i = 0; i < this.naturalItems.length; i++)
            natItems[i] = this.naturalItems[i].name();
        return natItems;
    }

    public Material[] getNaturalMaterials() {
        return this.naturalItems;
    }

    public void setWeight(int weight) {
        this.weight.put(MaterialClass.DEFAULT, Integer.valueOf(weight));
    }

    public int getWeight() {
        return ((Integer)this.weight.get(MaterialClass.DEFAULT)).intValue();
    }

    public int getWeight(MaterialClass material) {
        return this.weight.containsKey(material) ? ((Integer)this.weight.get(material)).intValue() : ((Integer)this.weight.get(MaterialClass.DEFAULT)).intValue();
    }

    public boolean canEnchantOnto(ItemStack item) {
        if (this.naturalItems == null || item == null)
            return false;
        for (Material validItem : this.naturalItems) {
            if (item.getType() == validItem)
                return true;
        }
        return (item.getType() == Material.INK_SACK);
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return this.group;
    }

    public boolean conflictsWith(CustomEnchantment enchantment) {
        Validate.notNull(enchantment);
        return (!this.group.equals("Default") && this.group.equalsIgnoreCase(enchantment.group));
    }

    public boolean conflictsWith(List<CustomEnchantment> enchantmentsToCheck) {
        Validate.notNull(enchantmentsToCheck);
        for (CustomEnchantment enchantment : enchantmentsToCheck) {
            if (conflictsWith(enchantment))
                return true;
        }
        return false;
    }

    public boolean conflictsWith(CustomEnchantment... enchantmentsToCheck) {
        Validate.notNull(enchantmentsToCheck);
        for (CustomEnchantment enchantment : enchantmentsToCheck) {
            if (conflictsWith(enchantment))
                return true;
        }
        return false;
    }

    public ItemStack addToItem(ItemStack item, int enchantLevel) {
        Validate.notNull(item);
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            meta = Bukkit.getServer().getItemFactory().getItemMeta(item.getType());
        List<String> metaLore = (meta.getLore() == null) ? new ArrayList<>() : meta.getLore();
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>) OneShotEnchants.getEnchantments(item).entrySet()) {
            if (Utils.removeColor(((CustomEnchantment)entry.getKey()).name()).equals(Utils.removeColor(name())) && (
                    (Integer)entry.getValue()).intValue() <= enchantLevel) {
                metaLore.remove(ChatColor.GRAY + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.WHITE + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.GREEN + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.AQUA + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.YELLOW + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.GOLD + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.RED + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(TieredEnchantments.getTierColor(this) + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
            }
        }
        metaLore.add(0, Utils.color(TieredEnchantments.getTierColor(this) + this.enchantName + " " + RomanNumeral.numeralOf(enchantLevel)));
        meta.setLore(metaLore);
        String name = ENameParser.getName(item);
        if (name != null)
            meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack removeFromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return item;
        if (!meta.hasLore())
            return item;
        List<String> metaLore = meta.getLore();
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)OneShotEnchants.getEnchantments(item).entrySet()) {
            if (ChatColor.stripColor(((CustomEnchantment)entry.getKey()).name()).equals(ChatColor.stripColor(name()))) {
                metaLore.remove(TieredEnchantments.getTierColor(this) + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.GRAY + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.WHITE + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.GREEN + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.AQUA + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.YELLOW + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.GOLD + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
                metaLore.remove(ChatColor.RED + name() + " " + RomanNumeral.numeralOf(((Integer)entry.getValue()).intValue()));
            }
        }
        meta.setLore(metaLore);
        item.setItemMeta(meta);
        return item;
    }

    public boolean equals(Object obj) {
        if (obj instanceof CustomEnchantment)
            return name().equalsIgnoreCase(((CustomEnchantment)obj).name());
        return false;
    }

    public int compareTo(CustomEnchantment customEnchantment) {
        return name().compareTo(customEnchantment.name());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {}

    public void applyDefenseEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageEvent event) {}

    public void applyToolEffect(Player player, Block block, int enchantLevel, BlockEvent event) {}

    public void applyMiscEffect(Player player, int enchantLevel, PlayerInteractEvent event) {}

    public void applyEquipEffect(Player player, int enchantLevel) {}

    public void applyUnequipEffect(Player player, int enchantLevel) {}

    public void applyEntityEffect(Player player, int enchantLevel, PlayerInteractEntityEvent event) {}

    public void applyProjectileEffect(LivingEntity user, int enchantLevel, ProjectileLaunchEvent event) {}

    public void applyEquipEffect(Player player, int level, ItemStack item) {}

    public void applyUnequipEffect(Player player, int level, ItemStack item) {}
}
