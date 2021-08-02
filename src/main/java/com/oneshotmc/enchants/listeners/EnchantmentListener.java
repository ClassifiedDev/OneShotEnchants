package com.oneshotmc.enchants.listeners;

import com.google.common.collect.ArrayListMultimap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;
import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.ENameParser;
import com.oneshotmc.enchants.enchantmentapi.EnchantmentEquip;
import com.oneshotmc.enchants.enchantmentapi.TieredEnchantments;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;

public class EnchantmentListener implements Listener {
    public static Material[] armor = new Material[] {
            Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS };

    public int getReflectLevel(LivingEntity player, String key) {
        List<MetadataValue> found = player.getMetadata(key + "_reflect_enchant");
        if (found != null && !found.isEmpty())
            return ((MetadataValue)found.get(0)).asInt();
        return 0;
    }

    private static boolean enchantmentStacks(CustomEnchantment ce) {
        String n = ce.name();
        if (n.equals("Armored") || n.equals("Tank") || n.equals("Angelic") || n.equals("Heavy") || n.equals("Enlighted"))
            return true;
        if (n.equals("Anti Gravity") || n
                .equals("Aquatic") || n
                .equals("Drunk") || n
                .equals("Gears") || n
                .equals("Glowing") || n
                .equals("Obsidianshield") || n
                .equals("Overload") || n
                .equals("Springs"))
            return true;
        return false;
    }

    private ArrayList<ItemStack> getItems(LivingEntity entity) {
        ItemStack weapon = entity.getEquipment().getItemInHand();
        ArrayList<ItemStack> items = new ArrayList<>(Arrays.asList(entity.getEquipment().getArmorContents()));
        items.add(weapon);
        return items;
    }

    private ArrayList<ItemStack> getItemInHand(LivingEntity entity) {
        ItemStack weapon = entity.getEquipment().getItemInHand();
        return new ArrayList<>(Arrays.asList(new ItemStack[] { weapon }));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        boolean offenseProcs = true;
        if (event.getDamager().hasMetadata("shootForce") && ((MetadataValue)event.getDamager().getMetadata("shootForce").get(0)).asFloat() < 0.75F)
            offenseProcs = false;
        LivingEntity damaged = (LivingEntity)event.getEntity();
        LivingEntity damager = (event.getDamager() instanceof LivingEntity) ? (LivingEntity)event.getDamager() : ((event.getDamager() instanceof Projectile && ((Projectile)event.getDamager()).getShooter() instanceof LivingEntity) ? (LivingEntity)((Projectile)event.getDamager()).getShooter() : null);
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)
            return;
        if (damager != null && offenseProcs)
            if (damager instanceof Player) {
                Player pDamager = (Player)damager;
                int reflectLevel = getReflectLevel(damaged, "normal");
                for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(pDamager.getItemInHand(), pDamager).entries()) {
                    CustomEnchantment enchant = entry.getKey();
                    int tier = enchant.getTier();
                    int reflectUsing = 0;
                    boolean reflect = false;
                    if (reflectLevel > 0 && tier <= 5 && reflectLevel >= ((Integer)entry.getValue()).intValue())
                        reflectUsing = reflectLevel;
                    if (reflectUsing > 0 && 0.02D + 0.01D * (reflectUsing / 3) >= Math.random())
                        reflect = true;
                    if (reflect) {
                        if (damaged instanceof Player)
                            enchant.applyEffect(damaged, damager, ((Integer)entry.getValue()).intValue(), event);
                        continue;
                    }
                    enchant.applyEffect(damager, damaged, (Integer) entry.getValue(), event);
                }
            } else {
                for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(damager, getItems(damager)).entries())
                    ((CustomEnchantment)entry.getKey()).applyEffect(damager, damaged, ((Integer)entry.getValue()).intValue(), event);
            }
        boolean silenced = damaged.hasMetadata("noDefenseProcs");
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(damaged, getItems(damaged)).entries()) {
            if (silenced && !((CustomEnchantment)entry.getKey()).name().equals("Phoenix"))
                continue;
            ((CustomEnchantment)entry.getKey()).applyDefenseEffect(damaged, damager, ((Integer)entry.getValue()).intValue(), (EntityDamageEvent)event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamaged(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        LivingEntity damaged = (LivingEntity)event.getEntity();
        if (!(damaged instanceof Player) && (((CraftLivingEntity)damaged).getHandle()).fromMobSpawner)
            return;
        boolean silenced = damaged.hasMetadata("noDefenseProcs");
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(damaged, getItems(damaged)).entries()) {
            if (silenced && !((CustomEnchantment)entry.getKey()).name().equals("Phoenix") &&
                    Math.random() < 0.5D)
                continue;
            ((CustomEnchantment)entry.getKey()).applyDefenseEffect(damaged, null, ((Integer)entry.getValue()).intValue(), event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamaged(EntityDamageByBlockEvent event) {
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        LivingEntity damaged = (LivingEntity)event.getEntity();
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(damaged, getItems(damaged)).entries())
            ((CustomEnchantment)entry.getKey()).applyDefenseEffect(damaged, null, ((Integer)entry.getValue()).intValue(), (EntityDamageEvent)event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamageBlock(BlockDamageEvent event) {
        if (event.getItemInHand().getType() != Material.DIAMOND_PICKAXE && event
                .getItemInHand().getType() != Material.IRON_PICKAXE)
            return;
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments((LivingEntity)event.getPlayer(), getItemInHand((LivingEntity)event.getPlayer())).entries())
            ((CustomEnchantment)entry.getKey()).applyToolEffect(event.getPlayer(), event.getBlock(), ((Integer)entry.getValue()).intValue(), (BlockEvent)event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreakBlock(BlockBreakEvent event) {
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments((LivingEntity)event.getPlayer(), getItemInHand((LivingEntity)event.getPlayer())).entries())
            ((CustomEnchantment)entry.getKey()).applyToolEffect(event.getPlayer(), event.getBlock(), ((Integer)entry.getValue()).intValue(), (BlockEvent)event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent event) {
        if (event.hasBlock() && event.hasItem() && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType() == Material.OBSIDIAN)
            for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments((LivingEntity)event.getPlayer(), getItemInHand((LivingEntity)event.getPlayer())).entries())
                ((CustomEnchantment)entry.getKey()).applyMiscEffect(event.getPlayer(), ((Integer)entry.getValue()).intValue(), event);
        if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem().getMaxStackSize() == 1 && isArmor(event.getItem()))
            (new EnchantmentEquip(event.getPlayer())).runTaskLater(OneShotEnchants.getInstance(), 1L);
    }

    public static boolean isArmor(ItemStack i) {
        for (Material m : armor) {
            if (i.getType() == m)
                return true;
        }
        return false;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEntityEvent event) {
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments((LivingEntity)event.getPlayer(), getItems((LivingEntity)event.getPlayer())).entries())
            ((CustomEnchantment)entry.getKey()).applyEntityEffect(event.getPlayer(), ((Integer)entry.getValue()).intValue(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEquip(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("container.crafting")) {
            if (event.getCurrentItem() == null && event.getCursor() == null)
                return;
            if (!event.isShiftClick()) {
                if (event.getSlotType() != InventoryType.SlotType.ARMOR)
                    return;
            } else if (event.getCursor() == null && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR &&
                    event.getCurrentItem().getMaxStackSize() != 1) {
                return;
            }
            (new EnchantmentEquip(OneShotEnchants.getInstance().getServer().getPlayer(event.getWhoClicked().getName()))).runTaskLater(OneShotEnchants.getInstance(), 1L);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        (new EnchantmentEquip(e.getPlayer())).runTaskLater(OneShotEnchants.getInstance(), 1L);
    }

    @EventHandler
    public void onBreak(PlayerItemBreakEvent event) {
        (new EnchantmentEquip(event.getPlayer())).runTaskLater(OneShotEnchants.getInstance(), 1L);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onConnect(PlayerJoinEvent event) {
        EnchantmentEquip.loadPlayer(event.getPlayer());
        Bukkit.getServer().dispatchCommand((CommandSender)event.getPlayer(), "apply");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDisconnect(PlayerQuitEvent event) {
        EnchantmentEquip.unequipPlayer(event.getPlayer(), false);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onProjectile(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() == null)
            return;
        if (event.getEntity().hasMetadata("shootForce") && ((MetadataValue)event.getEntity().getMetadata("shootForce").get(0)).asFloat() < 0.75F)
            return;
        if (event.getEntity().getShooter() instanceof Player) {
            Player pShooter = (Player)event.getEntity().getShooter();
            if (WorldGuardUtils.isPvPDisabled(pShooter.getLocation()))
                return;
            for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(pShooter.getItemInHand(), pShooter).entries())
                ((CustomEnchantment)entry.getKey()).applyProjectileEffect((LivingEntity)pShooter, ((Integer)entry.getValue()).intValue(), event);
        } else if (event.getEntity().getShooter() instanceof LivingEntity) {
            for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments((LivingEntity)event.getEntity().getShooter(), getItems((LivingEntity)event.getEntity().getShooter())).entries())
                ((CustomEnchantment)entry.getKey()).applyProjectileEffect((LivingEntity)event.getEntity().getShooter(), ((Integer)entry.getValue()).intValue(), event);
        }
    }

    public static Multimap<CustomEnchantment, Integer> getValidEnchantments(LivingEntity le, ArrayList<ItemStack> items) {
        ArrayListMultimap arrayListMultimap = ArrayListMultimap.create();
        for (ItemStack item : items) {
            ItemMeta meta = item.getItemMeta();
            if (meta == null ||
                    !meta.hasLore())
                continue;
            for (String lore : meta.getLore()) {
                String name = "";
                int level = -1;
                try {
                    name = ENameParser.parseName(lore);
                    level = ENameParser.parseLevel(lore);
                } catch (ArrayIndexOutOfBoundsException ex) {}
                if (name != null && level != 0 && OneShotEnchants.isRegistered(name)) {
                    CustomEnchantment ce = OneShotEnchants.getEnchantment(name);
                    int tier = TieredEnchantments.getEnchantmentTier(name);
                    if (arrayListMultimap.containsKey(ce) && !enchantmentStacks(ce))
                        if (((Integer)arrayListMultimap.get(ce).toArray()[0]).intValue() < level) {
                            arrayListMultimap.removeAll(ce);
                        } else {
                            continue;
                        }
                    if (level > ce.getMaxLevel()) {
                        if (le != null && le instanceof Player && ((Player)le).isOp()) {
                            arrayListMultimap.put(ce, Integer.valueOf(level));
                            continue;
                        }
                        Bukkit.getLogger().info("[OneShot Enchants] Impossible enchantment level: " + level + " (" + ce.name() + ") found on entity: " + le.getCustomName() + ", processing as level: " + ce.getMaxLevel());
                        arrayListMultimap.put(ce, Integer.valueOf(Math.min(ce.getMaxLevel(), level)));
                        continue;
                    }
                    arrayListMultimap.put(ce, Integer.valueOf(level));
                }

            }
        }
        return (Multimap<CustomEnchantment, Integer>)arrayListMultimap;
    }

    @Deprecated
    public static Multimap<CustomEnchantment, Integer> getValidEnchantments(ArrayList<ItemStack> items) {
        ArrayListMultimap arrayListMultimap = ArrayListMultimap.create();
        for (ItemStack item : items) {
            ItemMeta meta = item.getItemMeta();
            if (meta == null ||
                    !meta.hasLore())
                continue;
            for (String lore : meta.getLore()) {
                String name = ENameParser.parseName(lore);
                int level = ENameParser.parseLevel(lore);
                if (name != null && level != 0 &&

                        OneShotEnchants.isRegistered(name)) {
                    CustomEnchantment ce = OneShotEnchants.getEnchantment(name);
                    if (arrayListMultimap.containsKey(ce) && !enchantmentStacks(ce))
                        if (((Integer)arrayListMultimap.get(ce).toArray()[0]).intValue() < level) {
                            arrayListMultimap.removeAll(ce);
                        } else {
                            continue;
                        }
                    arrayListMultimap.put(ce, Integer.valueOf(level));
                }
            }
        }
        return (Multimap<CustomEnchantment, Integer>)arrayListMultimap;
    }

    public static Multimap<CustomEnchantment, Integer> getValidEnchantments(ItemStack item, Player p) {
        ArrayListMultimap arrayListMultimap1 = ArrayListMultimap.create();
        ArrayListMultimap arrayListMultimap2 = ArrayListMultimap.create();
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return (Multimap<CustomEnchantment, Integer>)arrayListMultimap1;
        if (!meta.hasLore())
            return (Multimap<CustomEnchantment, Integer>)arrayListMultimap1;
        for (String lore : meta.getLore()) {
            String name = ENameParser.parseName(lore);
            if (name == null || !OneShotEnchants.isRegistered(name))
                continue;
            int level = ENameParser.parseLevel(lore);
            if (level == 0)
                continue;
            int tier = TieredEnchantments.getEnchantmentTier(name);
            CustomEnchantment ce = OneShotEnchants.getEnchantment(name);
            if (arrayListMultimap1.containsKey(ce) && !enchantmentStacks(ce))
                if (((Integer)arrayListMultimap1.get(ce).toArray()[0]).intValue() < level) {
                    arrayListMultimap1.removeAll(ce);
                } else {
                    continue;
                }
            if (level > ce.getMaxLevel()) {
                if (p != null && p.isOp()) {
                    arrayListMultimap1.put(ce, Integer.valueOf(level));
                    continue;
                }
                Bukkit.getLogger().info("[OneShot Enchants] Impossible enchantment level: " + level + " (" + ce.name() + ") found on player: " + p.getName() + ", processing as level: " + ce.getMaxLevel());
                arrayListMultimap1.put(ce, Integer.valueOf(Math.min(ce.getMaxLevel(), level)));
                continue;
            }
            arrayListMultimap1.put(ce, Integer.valueOf(level));
        }
        return (Multimap<CustomEnchantment, Integer>)arrayListMultimap1;
    }
}
