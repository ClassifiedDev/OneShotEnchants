package com.oneshotmc.enchants.utils;

import org.bukkit.inventory.ItemStack;

public enum ArmorSlot {
    HELMET(3),
    CHESTPLATE(2),
    LEGGINGS(1),
    BOOTS(0);

    private int index;

    ArmorSlot(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public static ArmorSlot getArmorSlotByIndex(int i) {
        for (ArmorSlot as : values()) {
            if (as.index == i)
                return as;
        }
        return null;
    }

    public static ArmorSlot getArmorType(ItemStack i) {
        String name = i.getType().name();
        if (name.contains("HELMET"))
            return HELMET;
        if (name.contains("CHEST"))
            return CHESTPLATE;
        if (name.contains("LEG"))
            return LEGGINGS;
        if (name.contains("BOOTS"))
            return BOOTS;
        return null;
    }
}