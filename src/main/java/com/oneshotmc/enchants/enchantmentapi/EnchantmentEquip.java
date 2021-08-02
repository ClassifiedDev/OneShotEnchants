package com.oneshotmc.enchants.enchantmentapi;

import java.util.Hashtable;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.ENameParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class EnchantmentEquip extends BukkitRunnable {
    static Hashtable<String, ItemStack[]> equipment = (Hashtable)new Hashtable<>();

    Player player;

    public static void loadPlayer(Player player) {
        equipment.put(player.getName(), player.getEquipment().getArmorContents());
    }

    public static void clearPlayer(Player player) {
        equipment.remove(player.getName());
    }

    public static void unequipPlayer(Player player) {
        unequipPlayer(player, false);
    }

    public static void unequipPlayer(Player player, boolean equipmentWearing) {
        ItemStack[] cached = equipment.remove(player.getName());
        ItemStack[] equipped = player.getInventory().getArmorContents();
        if (cached == null)
            cached = equipped;
        if (cached != null)
            for (ItemStack item : cached) {
                if (item != null && item.getType() != Material.AIR)
                    OneShotEnchants.getEnchantments(item).forEach((enchantment, level) -> applyEnchantUnequip(enchantment, player, level.intValue(), item));
            }
        if (equipmentWearing && equipment != null)
            for (ItemStack equip : equipped) {
                if (equip != null && equip.getType() != Material.AIR)
                    OneShotEnchants.getEnchantments(equip).forEach((enchantment, level) -> applyEnchantUnequip(enchantment, player, level.intValue(), equip));
            }
    }

    private static void applyEnchantUnequip(CustomEnchantment enchantment, Player player, int level, ItemStack item) {
        if (enchantment.name().equals("Repair Guard")) {
            enchantment.applyUnequipEffect(player, level, item);
        } else {
            enchantment.applyUnequipEffect(player, level);
        }
    }

    public static void clear() {
        equipment.clear();
    }

    public EnchantmentEquip(Player player) {
        this.player = player;
    }

    public void run() {
        ItemStack[] equips = this.player.getEquipment().getArmorContents();
        ItemStack[] previous = equipment.get(this.player.getName());
        try {
            for (int i = 0; i < equips.length; i++) {
                ItemStack equipped = equips[i];
                ItemStack previouslyEquipped = (previous != null) ? previous[i] : null;
                if (equipped == null && previouslyEquipped != null) {
                    doUnequip((LivingEntity)this.player, previous[i]);
                } else if (equipped != null && (previous == null || previouslyEquipped == null)) {
                    doEquip((LivingEntity)this.player, equipped);
                } else if (previous != null &&
                        !isEqual(equipped, previouslyEquipped)) {
                    doUnequip((LivingEntity)this.player, previouslyEquipped);
                    doEquip((LivingEntity)this.player, equipped);
                }
            }
        } catch (Exception exception) {}
        equipment.put(this.player.getName(), equips);
    }

    private boolean isEqual(ItemStack equipped, ItemStack previouslyEquipped) {
        if ((equipped == null && previouslyEquipped != null) || (equipped != null && previouslyEquipped == null))
            return false;
        if (equipped == null && previouslyEquipped == null)
            return true;
        if (equipped.getType() != previouslyEquipped.getType())
            return false;
        ItemMeta im;
        ItemMeta equippedIm;
        if (equipped.hasItemMeta() == previouslyEquipped.hasItemMeta() && (

                !equipped.hasItemMeta() || ((im = equipped.getItemMeta()).hasDisplayName() == (equippedIm = previouslyEquipped.getItemMeta()).hasDisplayName() && (
                        !im.hasDisplayName() || im.getDisplayName().equals(equippedIm.getDisplayName())) && im
                        .hasLore() == equippedIm.hasLore() && (!im.hasLore() || im.getLore().equals(equippedIm.getLore())))))
            return true;
        return false;
    }

    private void doEquip(LivingEntity le, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        le.removeMetadata("armorLock", OneShotEnchants.getInstance());
        if (meta == null)
            return;
        if (!meta.hasLore())
            return;
        for (String lore : meta.getLore()) {
            String name = ENameParser.parseName(lore);
            int level = ENameParser.parseLevel(lore);
            if (name != null && level != 0 &&

                    OneShotEnchants.isRegistered(name)) {
                int tier = TieredEnchantments.getEnchantmentTier(name);
                CustomEnchantment ce = OneShotEnchants.getEnchantment(name);
                if (level > ce.getMaxLevel() && (le == null || !(le instanceof Player) ||
                        !((Player)le).isOp())) {
                    Bukkit.getLogger().info("[OneShot Enchants | Equip] Impossible enchantment level: " + level + " (" + ce.name() + ") found on entity: " + le.getCustomName() + ", processing as level: " + ce.getMaxLevel());
                    level = ce.getMaxLevel();
                }
                if (name.equals("Repair Guard")) {
                    ce.applyEquipEffect(this.player, level, item);
                    continue;
                }
                ce.applyEquipEffect(this.player, level);
            }
        }
    }

    private void doUnequip(LivingEntity le, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        le.removeMetadata("armorLock", OneShotEnchants.getInstance());
        if (meta == null)
            return;
        if (!meta.hasLore())
            return;
        for (String lore : meta.getLore()) {
            String name = ENameParser.parseName(lore);
            int level = ENameParser.parseLevel(lore);
            if (name != null && level != 0 &&

                    OneShotEnchants.isRegistered(name)) {
                CustomEnchantment ce = OneShotEnchants.getEnchantment(name);
                if (level > ce.getMaxLevel() && (le == null || !(le instanceof Player) ||
                        !((Player)le).isOp())) {
                    Bukkit.getLogger().info("[OneShot Enchants | Equip] Impossible enchantment level: " + level + " (" + ce.name() + ") found on entity: " + le + ", processing as level: " + ce.getMaxLevel());
                    level = ce.getMaxLevel();
                }
                if (name.equals("Repair Guard")) {
                    ce.applyUnequipEffect(this.player, level, item);
                    continue;
                }
                ce.applyUnequipEffect(this.player, level);
            }
        }
    }
}
