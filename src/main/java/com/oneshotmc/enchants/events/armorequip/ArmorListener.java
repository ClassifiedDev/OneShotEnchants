package com.oneshotmc.enchants.events.armorequip;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Directional;

public class ArmorListener implements Listener {
    private final List<String> blockedMaterials;

    public ArmorListener(List<String> blockedMaterials) {
        this.blockedMaterials = blockedMaterials;
    }

    @EventHandler
    public final void onInventoryClick(InventoryClickEvent e) {
        boolean shift = false, numberkey = false;
        if (e.isCancelled())
            return;
        if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT))
            shift = true;
        if (e.getClick().equals(ClickType.NUMBER_KEY))
            numberkey = true;
        if ((e.getSlotType() != InventoryType.SlotType.ARMOR || e.getSlotType() != InventoryType.SlotType.QUICKBAR) && !e.getInventory().getType().equals(InventoryType.CRAFTING))
            return;
        if (!(e.getWhoClicked() instanceof Player))
            return;
        if (e.getCurrentItem() == null)
            return;
        ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
        if (!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot())
            return;
        if (shift) {
            newArmorType = ArmorType.matchType(e.getCurrentItem());
            if (newArmorType != null) {
                boolean equipping = true;
                if (e.getRawSlot() == newArmorType.getSlot())
                    equipping = false;
                if ((newArmorType.equals(ArmorType.HELMET) && (equipping ? (e.getWhoClicked().getInventory().getHelmet() != null) : (e.getWhoClicked().getInventory().getHelmet() == null))) || (newArmorType.equals(ArmorType.CHESTPLATE) && (equipping ? (e.getWhoClicked().getInventory().getChestplate() != null) : (e.getWhoClicked().getInventory().getChestplate() == null))) || (newArmorType.equals(ArmorType.LEGGINGS) && (equipping ? (e.getWhoClicked().getInventory().getLeggings() != null) : (e.getWhoClicked().getInventory().getLeggings() == null))) || (newArmorType.equals(ArmorType.BOOTS) && (equipping ? (e.getWhoClicked().getInventory().getBoots() == null) : (e.getWhoClicked().getInventory().getBoots() != null)))) {
                    ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)e.getWhoClicked(), ArmorEquipEvent.EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(), equipping ? e.getCurrentItem() : null);
                    Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
                    if (armorEquipEvent.isCancelled())
                        e.setCancelled(true);
                }
            }
        } else {
            ItemStack newArmorPiece = e.getCursor();
            ItemStack oldArmorPiece = e.getCurrentItem();
            if (numberkey) {
                if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                    ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
                    if (hotbarItem != null) {
                        newArmorType = ArmorType.matchType(hotbarItem);
                        newArmorPiece = hotbarItem;
                        oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
                    } else {
                        newArmorType = ArmorType.matchType((e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) ? e.getCurrentItem() : e.getCursor());
                    }
                }
            } else {
                newArmorType = ArmorType.matchType((e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) ? e.getCurrentItem() : e.getCursor());
            }
            if (newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
                ArmorEquipEvent.EquipMethod method = ArmorEquipEvent.EquipMethod.DRAG;
                if (e.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey)
                    method = ArmorEquipEvent.EquipMethod.HOTBAR_SWAP;
                ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)e.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
                Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
                if (armorEquipEvent.isCancelled())
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL)
            return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Material mat = e.getClickedBlock().getType();
                for (String s : this.blockedMaterials) {
                    if (mat.name().equalsIgnoreCase(s))
                        return;
                }
            }
            ArmorType newArmorType = ArmorType.matchType(e.getItem());
            if (newArmorType != null && ((newArmorType
                    .equals(ArmorType.HELMET) && e.getPlayer().getInventory().getHelmet() == null) || (newArmorType.equals(ArmorType.CHESTPLATE) && e.getPlayer().getInventory().getChestplate() == null) || (newArmorType.equals(ArmorType.LEGGINGS) && e.getPlayer().getInventory().getLeggings() == null) || (newArmorType.equals(ArmorType.BOOTS) && e.getPlayer().getInventory().getBoots() == null))) {
                ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), ArmorEquipEvent.EquipMethod.HOTBAR, ArmorType.matchType(e.getItem()), null, e.getItem());
                Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    e.setCancelled(true);
                    player.updateInventory();
                }
            }
        }
    }

    @EventHandler
    public void dispenserFireEvent(BlockDispenseEvent e) {
        ArmorType type = ArmorType.matchType(e.getItem());
        if (ArmorType.matchType(e.getItem()) != null) {
            Location loc = e.getBlock().getLocation();
            for (Player p : loc.getWorld().getPlayers()) {
                if (loc.getBlockY() - p.getLocation().getBlockY() >= -1 && loc.getBlockY() - p.getLocation().getBlockY() <= 1 && ((p
                        .getInventory().getHelmet() == null && type.equals(ArmorType.HELMET)) || (p.getInventory().getChestplate() == null && type.equals(ArmorType.CHESTPLATE)) || (p.getInventory().getLeggings() == null && type.equals(ArmorType.LEGGINGS)) || (p.getInventory().getBoots() == null && type.equals(ArmorType.BOOTS)))) {
                    Dispenser dispenser = (Dispenser)e.getBlock().getState();
                    Dispenser dis = (Dispenser)dispenser.getData();
                    Directional directional = (Directional)dis;
                    BlockFace directionFacing = directional.getFacing();
                    if ((directionFacing == BlockFace.EAST && p.getLocation().getBlockX() != loc.getBlockX() && p.getLocation().getX() <= loc.getX() + 2.3D && p.getLocation().getX() >= loc.getX()) || (directionFacing == BlockFace.WEST && p.getLocation().getX() >= loc.getX() - 1.3D && p.getLocation().getX() <= loc.getX()) || (directionFacing == BlockFace.SOUTH && p.getLocation().getBlockZ() != loc.getBlockZ() && p.getLocation().getZ() <= loc.getZ() + 2.3D && p.getLocation().getZ() >= loc.getZ()) || (directionFacing == BlockFace.NORTH && p.getLocation().getZ() >= loc.getZ() - 1.3D && p.getLocation().getZ() <= loc.getZ())) {
                        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.DISPENSER, ArmorType.matchType(e.getItem()), null, e.getItem());
                        Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
                        if (armorEquipEvent.isCancelled())
                            e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void itemBreakEvent(PlayerItemBreakEvent e) {
        ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type != null) {
            Player p = e.getPlayer();
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.BROKE, type, e.getBrokenItem(), null);
            Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                ItemStack i = e.getBrokenItem().clone();
                i.setAmount(1);
                i.setDurability((short)(i.getDurability() - 1));
                if (type.equals(ArmorType.HELMET)) {
                    p.getInventory().setHelmet(i);
                } else if (type.equals(ArmorType.CHESTPLATE)) {
                    p.getInventory().setChestplate(i);
                } else if (type.equals(ArmorType.LEGGINGS)) {
                    p.getInventory().setLeggings(i);
                } else if (type.equals(ArmorType.BOOTS)) {
                    p.getInventory().setBoots(i);
                }
            }
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e) {
        Player p = e.getEntity();
        byte b;
        int i;
        ItemStack[] arrayOfItemStack;
        for (i = (arrayOfItemStack = p.getInventory().getArmorContents()).length, b = 0; b < i; ) {
            ItemStack itemStack = arrayOfItemStack[b];
            if (itemStack != null && !itemStack.getType().equals(Material.AIR))
                Bukkit.getServer().getPluginManager().callEvent((Event)new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.DEATH, ArmorType.matchType(itemStack), itemStack, null));
            b = (byte)(b + 1);
        }
    }
}
