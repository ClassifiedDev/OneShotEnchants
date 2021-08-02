package com.oneshotmc.enchants;

import com.gmail.nossr50.listeners.PlayerListener;
import com.oneshotmc.enchants.commands.*;
import com.oneshotmc.enchants.crystals.CrystalModeTask;
import com.oneshotmc.enchants.crystals.listeners.CrystalListener;
import com.oneshotmc.enchants.crystals.listeners.CrystalModeListener;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.ENameParser;
import com.oneshotmc.enchants.enchantmentapi.EnchantmentEquip;
import com.oneshotmc.enchants.enchantmentapi.RomanNumeral;
import com.oneshotmc.enchants.enchants.armor.*;
import com.oneshotmc.enchants.enchants.axes.*;
import com.oneshotmc.enchants.enchants.bow.*;
import com.oneshotmc.enchants.enchants.swords.*;
import com.oneshotmc.enchants.enchants.tools.AutoSmelt;
import com.oneshotmc.enchants.enchants.tools.Detonate;
import com.oneshotmc.enchants.enchants.tools.Haste;
import com.oneshotmc.enchants.enchants.tools.ObsidianDestroyer;
import com.oneshotmc.enchants.events.armorequip.ArmorListener;
import com.oneshotmc.enchants.listeners.*;
import jdk.nashorn.internal.ir.Block;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.defaults.GiveCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OneShotEnchants extends JavaPlugin implements Listener {
    public static Hashtable<String, CustomEnchantment> enchantments = new Hashtable<>();

    public static Plugin instance;

    public static Economy econ = null;

    public static File fileF;

    public static FileConfiguration file;

    public static File configF;

    public static FileConfiguration config;

    public static File descriptionsF;

    public static FileConfiguration descriptions;

    private static ConcurrentHashMap<String, Player> asyncPlayerMap;

    private static ConcurrentHashMap<String, Location> playerLocations;

    public static ConcurrentHashMap<String, Player> getAsyncPlayerMap() {
        return asyncPlayerMap;
    }

    public static ConcurrentHashMap<String, Location> getPlayerLocations() {
        return playerLocations;
    }

    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();
        instance = this;
        generateConfigs();
        registerTasks();
        registerEvents();
        registerCommands();
        enchantments.clear();
        EnchantmentEquip.clear();
        registerCustomEnchantments(new Deathbringer(), new Tank(), new Valor(), new Enlighted(), new Armored(), new Hardened(), new Clarity(), new Implants(), new Glowing(),
                new Drunk(), new Aquatic(), new Frozen(), new Dodge(), new Overload(), new ArrowDeflect(), new Cactus(), new Voodoo(), new Obsidianshield(), new Gears(),
                new Springs(), new EnderWalker(), new Rage(), new Decapitation(), new Shackle(), new Inquisitive(), new Featherweight(), new Poison(),
                new Silence(), new Trap(), new DeepWounds(), new Blind(), new com.oneshotmc.enchants.enchants.swords.Block(), new DoubleStrike(), new Enrage(), new Execute(), new Obliterate(), new Insomnia(),
                new ThunderingBlow(), new Barbarian(), new Berserk(), new Blessed(), new Cleave(), new Insanity(), new Pummel(), new Ravenous(), new Confusion(), new Hellfire(),
                new Piercing(), new Snare(), new Sniper(), new com.oneshotmc.enchants.enchants.bow.Explosive(), new Teleportation(), new Venom(), new AutoSmelt(), new ObsidianDestroyer(), new Detonate(), new Haste());
        if (!setupEconomy()) {
            logger.severe(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { pdfFile.getName() }));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        asyncPlayerMap = new ConcurrentHashMap<>();
        playerLocations = new ConcurrentHashMap<>();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            asyncPlayerMap.put(pl.getName(), pl);
            playerLocations.put(pl.getName(), pl.getLocation());
            EnchantmentEquip.loadPlayer(pl);
        }
        (new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers())
                    OneShotEnchants.playerLocations.put(p.getName(), p.getLocation());
            }
        }).runTaskTimer(this, 0L, 40L);
        logger.info(pdfFile.getName() + " has been Enabled v" + pdfFile.getVersion() + "(Developed by: " + pdfFile.getAuthors() + ")");
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();
        EnchantmentEquip.clear();
        logger.info(pdfFile.getName() + " has been Disabled v" + pdfFile.getVersion() + "(Developed by: " + pdfFile.getAuthors() + ")");
    }

    public static Plugin getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        econ = rsp.getProvider();
        return (econ != null);
    }

    public void registerTasks() {
        Bukkit.getServer().getScheduler().runTaskTimer(this, new CrystalModeTask(), 5L, 5L);
    }

    public void registerCommands() {
        getCommand("bless").setExecutor(new BlessCommand());
        getCommand("apply").setExecutor(new ApplyCommand());
        getCommand("cedebug").setExecutor(new DebugCommand());
        getCommand("giveenchant").setExecutor(new com.oneshotmc.enchants.commands.GiveCommand());
        getCommand("tinkerer").setExecutor(new TinkererCommand());
        getCommand("enchants").setExecutor(new EnchantsCommand());
        getCommand("enchanter").setExecutor(new EnchanterCommand());
        getCommand("giveenchantitem").setExecutor(new com.oneshotmc.enchants.commands.GiveCommand());
        getCommand("givetracker").setExecutor(new GiveTrackerCommand());
        getCommand("givecrystal").setExecutor(new GiveCrystalCommand());
        getCommand("enchantadmin").setExecutor(new EnchantAdminCommand());
        getCommand("splitcrystals").setExecutor(new WithdrawCrystalsCommand());
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new DamageCapListener(), this);
        pm.registerEvents(new CrystalListener(), this);
        pm.registerEvents(new com.oneshotmc.enchants.crystals.listeners.PlayerListener(), this);
        pm.registerEvents(new com.oneshotmc.enchants.crystals.listeners.ItemListener(), this);
        pm.registerEvents(new CrystalModeListener(), this);
        pm.registerEvents(new DustOpenListener(), this);
        pm.registerEvents(new DustApplyListener(), this);
        pm.registerEvents(new EnchantmentListener(), this);
        pm.registerEvents(new me.classified.enchants.listeners.RenameScrollListener(), this);
        pm.registerEvents(new TinkererClickListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new VanillaDamageListener(), this);
        pm.registerEvents(new EnchantmentApplyListener(), this);
        pm.registerEvents(new EnchantmentOpenListener(), this);
        pm.registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
    }

    public static boolean isRegistered(String enchantmentName) {
        return enchantments.containsKey(enchantmentName.toUpperCase());
    }

    public static CustomEnchantment getEnchantment(String name) {
        return enchantments.get(name.toUpperCase());
    }

    public static Set<String> getEnchantmentNames() {
        return enchantments.keySet();
    }

    public static Collection<CustomEnchantment> getEnchantments() {
        return enchantments.values();
    }

    public static boolean registerCustomEnchantment(CustomEnchantment enchantment) {
        if (enchantments.containsKey(enchantment.name().toUpperCase()))
            return false;
        if (!enchantment.isEnabled())
            return false;
        enchantments.put(enchantment.name().toUpperCase(), enchantment);
        return true;
    }

    public static void registerCustomEnchantments(CustomEnchantment... enchantments) {
        int enchantCount = 0;
        for (CustomEnchantment enchantment : enchantments) {
            registerCustomEnchantment(enchantment);
            enchantCount++;
        }
        Bukkit.getLogger().log(Level.INFO, "[OneShot Enchants] Successfully registered " + enchantCount + " custom enchantments.");
    }

    public static boolean unregisterCustomEnchantment(String enchantmentName) {
        if (enchantments.containsKey(enchantmentName.toUpperCase())) {
            enchantments.remove(enchantmentName.toUpperCase());
            return true;
        }
        return false;
    }

    public static Map<CustomEnchantment, Integer> getEnchantments(ItemStack item) {
        HashMap<CustomEnchantment, Integer> list = new HashMap<>();
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return list;
        if (!meta.hasLore())
            return list;
        for (String lore : meta.getLore()) {
            String name = ENameParser.parseName(lore);
            int level = ENameParser.parseLevel(lore);
            if (name != null && level != 0 &&

                    isRegistered(name))
                list.put(getEnchantment(name), Integer.valueOf(level));
        }
        return list;
    }

    public static Map<CustomEnchantment, Integer> getAllEnchantments(ItemStack item) {
        Map<CustomEnchantment, Integer> map = getEnchantments(item);
        if (item.hasItemMeta() && item.getItemMeta().hasEnchants())
            for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet())
                map.put(getEnchantment(entry.getKey().getName()), entry.getValue());
        if (item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)item.getItemMeta();
            for (Map.Entry<Enchantment, Integer> entry : meta.getStoredEnchants().entrySet())
                map.put(getEnchantment(entry.getKey().getName()), entry.getValue());
        }
        return map;
    }

    public static boolean itemHasEnchantment(ItemStack item, String enchantmentName) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return false;
        if (!meta.hasLore())
            return false;
        for (String lore : meta.getLore()) {
            if (lore.contains(enchantmentName) && ENameParser.parseLevel(lore) > 0)
                return true;
        }
        return false;
    }

    public static ItemStack removeEnchantments(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return item;
        if (!meta.hasLore())
            return item;
        List<String> lore = meta.getLore();
        for (Map.Entry<CustomEnchantment, Integer> entry : getEnchantments(item).entrySet())
            lore.remove((new StringBuilder()).append(entry.getKey()).append(entry.getKey().name()).append(" ").append(RomanNumeral.numeralOf(entry.getValue().intValue())).toString());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private void generateConfigs() {
        saveDefaultConfig();
        configF = new File(getDataFolder(), "config.yml");
        config = new YamlConfiguration();
        descriptionsF = new File(getDataFolder(), "descriptions.yml");
        descriptions = new YamlConfiguration();
        boolean datab = false;
        for (int i = 1; i <= 3; i++) {
            if (i == 1) {
                fileF = configF;
                file = config;
            }
            if (i == 2) {
                fileF = descriptionsF;
                file = descriptions;
            }
            if (!datab) {
                if (!fileF.exists()) {
                    fileF.getParentFile().mkdirs();
                    saveResource(fileF.getName(), false);
                }
                try {
                    file.load(fileF);
                } catch (IOException |org.bukkit.configuration.InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            } else {
                if (!fileF.exists()) {
                    fileF.getParentFile().mkdirs();
                    saveResource(fileF.getName(), false);
                }
                try {
                    file.load(fileF);
                } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            }
            datab = false;
        }
    }

    public static FileConfiguration getConfigF() {
        return config;
    }

    public static FileConfiguration getDescriptions() {
        return descriptions;
    }

    public static void saveConfigF() {
        try {
            config.save(configF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
