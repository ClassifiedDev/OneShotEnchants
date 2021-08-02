package com.oneshotmc.enchants.enchantmentapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.EnchantmentBook;
import org.bukkit.inventory.ItemStack;

public enum TieredEnchantments {
    naturewrath(6),
    divineimmolation(6),
    phoenix(6),
    immortal(6),
    enchantreflect(5),
    deathbringer(5),
    enlighted(5),
    gears(5),
    overload(5),
    disarmor(5),
    lifesteal(5),
    rage(5),
    drunk(5),
    clarity(5),
    devour(5),
    bloodlust(5),
    destruction(5),
    protection(5),
    sniper(5),
    inquisitive(5),
    killaura(5),
    barbarian(5),
    insanity(5),
    inversion(5),
    doublestrike(5),
    bloodlink(5),
    blacksmith(5),
    aegis(5),
    deathgod(5),
    silence(5),
    leadership(5),
    armored(5),
    antigank(5),
    diminish(5),
    bossslayer(5),
    hex(5),
    avengingangel(4),
    obsidianshield(4),
    bleed(4),
    cleave(4),
    piercing(4),
    detonate(4),
    arrowlifesteal(4),
    lucky(4),
    guardians(4),
    blessed(4),
    enderwalker(4),
    hellfire(4),
    tank(4),
    arrowdeflect(4),
    block(4),
    creeperarmor(4),
    unfocus(4),
    longbow(4),
    heavy(4),
    enrage(4),
    spirits(4),
    sticky(4),
    pacify(4),
    dimensionrift(4),
    marksman(4),
    arrowbreak(4),
    assassin(4),
    dominate(4),
    ghost(4),
    eagleeye(4),
    disintegrate(4),
    fuse(4),
    valor(4),
    iceaspect(4),
    implants(4),
    dodge(4),
    metaphysical(4),
    angelic(4),
    ragdoll(4),
    corrupt(4),
    snare(3),
    voodoo(3),
    springs(3),
    antigravity(3),
    shockwave(3),
    cactus(3),
    vampire(3),
    teleportation(3),
    infernal(3),
    poisoned(3),
    execute(3),
    trap(3),
    stormcaller(3),
    smokebomb(3),
    undeadruse(3),
    trickster(3),
    rocketescape(3),
    pummel(3),
    spiritlink(3),
    frozen(3),
    poison(3),
    solitude(3),
    hijack(3),
    blind(3),
    wither(3),
    venom(3),
    paralyze(3),
    repairguard(3),
    greatsword(3),
    demonforged(3),
    hardened(3),
    shackle(3),
    farcast(3),
    resilience(3),
    reforged(3),
    molten(2),
    curse(2),
    endershift(2),
    commander(2),
    plaguecarrier(2),
    lifebloom(2),
    deepwounds(2),
    ravenous(2),
    berserk(2),
    featherweight(2),
    cowification(2),
    explosive(2),
    obsidiandestroyer(2),
    selfdestruct(2),
    telepathy(2),
    nimble(2),
    nutrition(2),
    virus(2),
    skillswipe(2),
    training(2),
    skilling(2),
    glowing(1),
    lightning(1),
    confusion(1),
    insomnia(1),
    thunderingblow(1),
    obliterate(1),
    healing(1),
    headless(1),
    decapitation(1),
    haste(1),
    epicness(1),
    oxygenate(1),
    autosmelt(1),
    experience(1),
    aquatic(1);

    int enchantTier;

    private static List<CustomEnchantment> commonEnchants;

    private static List<CustomEnchantment> uncommonEnchants;

    private static List<CustomEnchantment> rareEnchants;

    private static List<CustomEnchantment> ultimateEnchants;

    private static List<CustomEnchantment> legendaryEnchants;

    private static List<CustomEnchantment> mythicEnchants;

    TieredEnchantments(int tier) {
        this.enchantTier = tier;
    }

    public static int getEnchantmentTier(String enchantmentName) {
        try {
            return (valueOf(enchantmentName.toLowerCase().replace("_", "").replace(" ", ""))).enchantTier;
        } catch (Exception err) {
            return 0;
        }
    }

    public static List<String> getAllEnchantmentNamesInTier(int tier) {
        List<String> enchantmentNames = new ArrayList<>();
        for (CustomEnchantment ce : OneShotEnchants.getEnchantments()) {
            if (getEnchantmentTier(ce.name()) == tier)
                enchantmentNames.add(ce.name());
        }
        return enchantmentNames;
    }

    static {
        commonEnchants = new ArrayList<>();
        uncommonEnchants = new ArrayList<>();
        rareEnchants = new ArrayList<>();
        ultimateEnchants = new ArrayList<>();
        legendaryEnchants = new ArrayList<>();
        mythicEnchants = new ArrayList<>();
    }

    private static List<CustomEnchantment> getEnchantmentsTierCacheList(int tier) {
        if (tier == 1)
            return commonEnchants;
        if (tier == 2)
            return uncommonEnchants;
        if (tier == 3)
            return rareEnchants;
        if (tier == 4)
            return ultimateEnchants;
        if (tier == 5)
            return legendaryEnchants;
        if (tier == 6)
            return mythicEnchants;
        return new ArrayList<>();
    }

    public static List<CustomEnchantment> getAllEnchantmentsInTier(int tier) {
        List<CustomEnchantment> enchantments = new ArrayList<>();
        for (CustomEnchantment ce : OneShotEnchants.getEnchantments()) {
            if (getEnchantmentTier(ce.name()) == tier)
                enchantments.add(ce);
        }
        return enchantments;
    }

    public static CustomEnchantment getRandomEnchantment(int tier) {
        List<CustomEnchantment> enchants = getAllEnchantmentsInTier(tier);
        CustomEnchantment ce = enchants.get((new Random()).nextInt(enchants.size()));
        return ce;
    }

    public static ItemStack getRandomEnchantmentBook(int tier) {
        CustomEnchantment ce = getRandomEnchantment(tier);
        return EnchantmentBook.getEnchantmentBook(ce, (new Random()).nextInt(ce.getMaxLevel()) + 1);
    }

    public static int getBookTier(ItemStack i) {
        if (i == null || !i.hasItemMeta() || !i.getItemMeta().hasDisplayName())
            return 0;
        String displayName = i.getItemMeta().getDisplayName();
        if (displayName.startsWith("&7"))
            return 1;
        if (displayName.startsWith("&a"))
            return 2;
        if (displayName.startsWith("&b"))
            return 3;
        if (displayName.startsWith("&e"))
            return 4;
        if (displayName.startsWith("&6"))
            return 5;
        if (displayName.startsWith("&c"))
            return 6;
        return 0;
    }

    public static String getTierColor(CustomEnchantment ce) {
        int tier = getEnchantmentTier(ce.name());
        if (tier == 1)
            return "&7";
        if (tier == 2)
            return "&a";
        if (tier == 3)
            return "&b";
        if (tier == 4)
            return "&e";
        if (tier == 5)
            return "&6";
        if (tier == 6)
            return "&c";
        return "&f";
    }

    public static Short getTierOrbColor(CustomEnchantment ce) {
        int tier = getEnchantmentTier(ce.name());
        if (tier == 1)
            return Short.valueOf((short)8);
        if (tier == 2)
            return Short.valueOf((short)10);
        if (tier == 3)
            return Short.valueOf((short)12);
        if (tier == 4)
            return Short.valueOf((short)11);
        if (tier == 5)
            return Short.valueOf((short)14);
        if (tier == 6)
            return Short.valueOf((short)1);
        return Short.valueOf((short)7);
    }
}
