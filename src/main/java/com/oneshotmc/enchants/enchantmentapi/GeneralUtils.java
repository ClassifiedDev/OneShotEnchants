package com.oneshotmc.enchants.enchantmentapi;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.oneshotmc.enchants.OneShotEnchants;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

public class GeneralUtils {
    public static Material[] swords = new Material[] { Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD };

    public static Material[] swords_and_bow = new Material[] { Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD, Material.BOW };

    public static Material[] armor = new Material[] {
            Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS };

    public static Material[] helmets = new Material[] { Material.LEATHER_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET };

    public static Material[] helmets_and_boots = new Material[] { Material.LEATHER_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET, Material.LEATHER_BOOTS, Material.GOLD_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS };

    public static Material[] chestplates = new Material[] { Material.LEATHER_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE };

    public static Material[] leggings = new Material[] { Material.LEATHER_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS };

    public static Material[] boots = new Material[] { Material.LEATHER_BOOTS, Material.GOLD_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS };

    public static Material[] pickaxe = new Material[] { Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE };

    public static Material[] tools = new Material[] {
            Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE, Material.WOOD_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLD_HOE, Material.DIAMOND_HOE,
            Material.WOOD_SPADE, Material.STONE_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE, Material.DIAMOND_SPADE, Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE,
            Material.FISHING_ROD };

    public static Material[] axe = new Material[] { Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE };

    public static Material[] weapons = new Material[] {
            Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD, Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE,
            Material.BOW };

    public static Material[] weapons_and_tools = new Material[] {
            Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD, Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE,
            Material.BOW, Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE, Material.WOOD_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLD_HOE,
            Material.DIAMOND_HOE, Material.WOOD_SPADE, Material.STONE_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE, Material.DIAMOND_SPADE, Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE,
            Material.DIAMOND_AXE };

    public static boolean healPlayer(LivingEntity entity, double healAmount) {
        return setPlayerHealth(entity, entity.getHealth() + healAmount);
    }

    public static boolean reducePlayerHealth(Player entity, double damageAmount) {
        return setPlayerHealth((LivingEntity)entity, Math.max(0.0D, entity.getHealth() - damageAmount));
    }

    public static boolean setPlayerHealth(LivingEntity entity, double newHealth) {
        double health = entity.getHealth();
        if ((health <= 0.0D || entity.isDead()) && entity instanceof Player)
            return false;
        if (newHealth > entity.getMaxHealth())
            newHealth = entity.getMaxHealth();
        entity.setHealth(newHealth);
        return true;
    }

    public static ItemStack getPlayerHead(String p_name) {
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta sm = (SkullMeta)is.getItemMeta();
        sm.setOwner(p_name);
        sm.setDisplayName(ChatColor.WHITE + "Skull of " + p_name);
        is.setItemMeta((ItemMeta)sm);
        return is;
    }

    public static boolean isArmor(ItemStack i) {
        for (Material m : helmets) {
            if (i.getType() == m)
                return true;
        }
        for (Material m : chestplates) {
            if (i.getType() == m)
                return true;
        }
        for (Material m : leggings) {
            if (i.getType() == m)
                return true;
        }
        for (Material m : boots) {
            if (i.getType() == m)
                return true;
        }
        return false;
    }

    public static boolean isWeapon(ItemStack i) {
        for (Material m : weapons) {
            if (i.getType() == m)
                return true;
        }
        return false;
    }

    public static boolean isSword(ItemStack i) {
        for (Material m : swords) {
            if (i.getType() == m)
                return true;
        }
        return false;
    }

    public static boolean isTool(ItemStack i) {
        for (Material m : tools) {
            if (i.getType() == m)
                return true;
        }
        return false;
    }

    public static boolean isAlly(Player p1, Player p2) {
        if (p1 == null || p2 == null)
            return false;
        if (p1.hasMetadata("in_duel") || p2.hasMetadata("in_duel"))
            return false;
        FPlayer fp1 = FPlayers.getInstance().getByPlayer(p1);
        FPlayer fp2 = FPlayers.getInstance().getByPlayer(p2);
        if (!fp1.hasFaction() || !fp2.hasFaction())
            return false;
        Relation r1 = fp1.getFaction().getRelationTo((RelationParticipator)fp2.getFaction());
        Relation r2 = fp2.getFaction().getRelationTo((RelationParticipator)fp1.getFaction());
        if ((r1.isAlly() || r1.isMember()) && (r2.isAlly() || r2.isMember()))
            return true;
        return false;
    }

    public static boolean isAtleastTruce(Player p1, Player p2) {
        if (p1 == null || p2 == null)
            return false;
        if (p1.hasMetadata("in_duel") || p2.hasMetadata("in_duel"))
            return false;
        FPlayer fp1 = FPlayers.getInstance().getByPlayer(p1);
        FPlayer fp2 = FPlayers.getInstance().getByPlayer(p2);
        if (!fp1.hasFaction() || !fp2.hasFaction())
            return false;
        Relation r1 = fp1.getFaction().getRelationTo((RelationParticipator)fp2.getFaction());
        Relation r2 = fp2.getFaction().getRelationTo((RelationParticipator)fp1.getFaction());
        if ((r1.isAlly() || r1.isMember() || r1.isTruce()) && (r2.isAlly() || r2.isMember() || r2.isTruce()))
            return true;
        return false;
    }

    public static boolean isAtleastTruce(Faction faction, Player p2) {
        if (p2 == null)
            return false;
        if (p2.hasMetadata("in_duel"))
            return false;
        FPlayer fp2 = FPlayers.getInstance().getByPlayer(p2);
        if (faction == null || !fp2.hasFaction())
            return false;
        Relation r1 = fp2.getFaction().getRelationTo((RelationParticipator)faction);
        if (r1.isAlly() || r1.isMember() || r1.isTruce())
            return true;
        return false;
    }

    public static boolean canEffectEntity(Entity entity) {
        return !(entity instanceof org.bukkit.entity.EnderDragon);
    }

    public static boolean isEnemy(Faction faction, Player p2) {
        if (p2 == null)
            return true;
        if (p2.hasMetadata("in_duel"))
            return true;
        FPlayer fp2 = FPlayers.getInstance().getByPlayer(p2);
        if (faction == null || !fp2.hasFaction())
            return true;
        Relation r2 = fp2.getFaction().getRelationTo((RelationParticipator)faction);
        return r2.isEnemy();
    }

    public static boolean isEnemy(Player p1, Player p2) {
        if (p1 == null || p2 == null)
            return true;
        if (p1.hasMetadata("in_duel") || p2.hasMetadata("in_duel"))
            return true;
        FPlayer fp1 = FPlayers.getInstance().getByPlayer(p1);
        FPlayer fp2 = FPlayers.getInstance().getByPlayer(p2);
        if (!fp1.hasFaction() || !fp2.hasFaction())
            return true;
        Relation r1 = fp1.getFaction().getRelationTo((RelationParticipator)fp2.getFaction());
        Relation r2 = fp2.getFaction().getRelationTo((RelationParticipator)fp1.getFaction());
        if (r1.isEnemy() || r2.isEnemy())
            return true;
        return false;
    }

    public static boolean isEffectedByRage(LivingEntity le) {
        return (le.hasMetadata("effectedByRage") && System.currentTimeMillis() - ((MetadataValue)le.getMetadata("effectedByRage").get(0)).asLong() <= 200L);
    }

    public static boolean isEffectedByDevour(LivingEntity le) {
        return (le.hasMetadata("effectedByDevour") && System.currentTimeMillis() - ((MetadataValue)le.getMetadata("effectedByDevour").get(0)).asLong() <= 200L);
    }

    public static void pushBackEntity(LivingEntity damager, Entity toPush, int knockbackLevel) {
        Vector velocity = toPush.getVelocity();
        float yaw = damager.getLocation().getYaw();
        Vector push = new Vector((-MathHelper.sin(yaw * 3.1415927F / 180.0F) * knockbackLevel * 0.5F), 0.1D, (MathHelper.cos(yaw * 3.1415927F / 180.0F) * knockbackLevel * 0.5F));
        velocity.add(push);
        if (velocity.getX() != Double.NaN)
            toPush.setVelocity(velocity);
    }

    public static void pushAwayEntity(LivingEntity center, Entity entity, double speed) {
        pushAwayEntity(center.getLocation(), entity, speed);
    }

    public static void pushAwayEntity(Location center, Entity entity, double speed) {
        entity.setMetadata("pushAwayEntityEvent", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(getCurrentServerTick())));
        Vector unitVector = entity.getLocation().toVector().subtract(center.toVector());
        if (unitVector.length() != 0.0D)
            unitVector.normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }

    public static int getCurrentServerTick() {
        return MinecraftServer.currentTick;
    }

    public static boolean isRunning() {
        return MinecraftServer.getServer().isRunning();
    }
}
