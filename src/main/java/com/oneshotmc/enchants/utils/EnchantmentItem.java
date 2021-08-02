package com.oneshotmc.enchants.utils;

import org.bukkit.Material;

public class EnchantmentItem {
    Material material;

    Short data;

    String name;

    String[] lore;

    String applyLore;

    int success;

    int destroy;

    String targets;

    String color;

    public EnchantmentItem(Material material, Short data, String name, String[] lore, String applyLore, int success, int destroy, String targets, String color) {
        this.material = material;
        this.data = data;
        this.name = name;
        this.lore = lore;
        this.applyLore = applyLore;
        this.success = success;
        this.destroy = destroy;
        this.targets = targets;
        this.color = color;
    }

    public Material getMaterial() {
        return this.material;
    }

    public Short getData() {
        return this.data;
    }

    public String getName() {
        return this.name;
    }

    public String[] getLore() {
        return this.lore;
    }

    public String getApplyLore() {
        return this.applyLore;
    }

    public int getSuccess() {
        return this.success;
    }

    public void setSuccess(int s) {
        this.success = s;
    }

    public int getDestroy() {
        return this.destroy;
    }

    public void setDestroy(int d) {
        this.destroy = d;
    }

    public String getTargets() {
        return this.targets;
    }

    public String getColor() {
        return this.color;
    }
}
