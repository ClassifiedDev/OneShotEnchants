package com.oneshotmc.enchants.utils;

import com.oneshotmc.enchants.utils.ArmorSlot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DurabilityUtil {
    public static boolean healLowestArmorPiece(Player p, short amount) {
        ArmorSlot lowest = null;
        short lowestDura = 0;
        for (int i = 0; i < (p.getEquipment().getArmorContents()).length; i++) {
            ItemStack armorPiece = p.getEquipment().getArmorContents()[i];
            if (armorPiece != null && armorPiece.getType() != Material.AIR &&
                    armorPiece.getDurability() > 0 && (
                    lowestDura == 0 || armorPiece.getDurability() < lowestDura)) {
                lowestDura = armorPiece.getDurability();
                lowest = ArmorSlot.getArmorSlotByIndex(i);
            }
        }
        if (lowest != null) {
            ItemStack itemStack = p.getEquipment().getArmorContents()[lowest.getIndex()];
            itemStack.setDurability((short)Math.max(0, (short)(itemStack.getDurability() - amount)));
            return true;
        }
        return false;
    }

    public static boolean healMostDamagedArmorPeice(Player p, short amount) {
        ArmorSlot lowest = null;
        short highestDamagedPeice = 0;
        for (int i = 0; i < (p.getEquipment().getArmorContents()).length; i++) {
            ItemStack armorPiece = p.getEquipment().getArmorContents()[i];
            if (armorPiece != null && armorPiece.getType() != Material.AIR &&
                    armorPiece.getDurability() > 0 && (
                    highestDamagedPeice == 0 || armorPiece.getDurability() > highestDamagedPeice)) {
                highestDamagedPeice = armorPiece.getDurability();
                lowest = ArmorSlot.getArmorSlotByIndex(i);
            }
        }
        if (lowest != null) {
            ItemStack itemStack = p.getEquipment().getArmorContents()[lowest.getIndex()];
            itemStack.setDurability((short)Math.max(0, (short)(itemStack.getDurability() - amount)));
            return true;
        }
        return false;
    }
}
