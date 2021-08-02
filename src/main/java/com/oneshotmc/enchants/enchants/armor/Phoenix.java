package com.oneshotmc.enchants.enchants.armor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ArmorUtil;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Phoenix extends CustomEnchantment {
    protected static boolean cosmicOutposts;

    protected static boolean factionUpgrades;

    public Phoenix() {
        super("Phoenix", GeneralUtils.boots, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 3.0D;
        Bukkit.getPluginManager().registerEvents(new PhoenixListener(this), OneShotEnchants.getInstance());
        Bukkit.getScheduler().runTaskTimer(OneShotEnchants.getInstance(), new ReducePhoenixCount(), 12000L, 12000L);
    }

    public static boolean hasPhoenixEquipped(Player player) {
        return phoenixEquipped.containsKey(player.getUniqueId());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        phoenixEquipped.put(player.getUniqueId(), Integer.valueOf(enchantLevel));
    }

    public void applyUnequipEffect(Player player, int level, ItemStack item) {
        phoenixEquipped.remove(player.getUniqueId());
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        applyUnequipEffect(player, enchantLevel, (ItemStack)null);
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            double dmg = e.getDamage();
            dmg *= ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue((Player)player));
            if (procPhoenix((Player)player, attacker, level, dmg)) {
                e.setDamage(0.0D);
                e.setCancelled(true);
            }
        }
    }

    public static boolean procPhoenix(Player player, LivingEntity damager, double dmg) {
        Integer level = phoenixEquipped.get(player.getUniqueId());
        return (level != null && procPhoenix(player, damager, level.intValue(), dmg));
    }

    public static boolean procPhoenix(Player player, LivingEntity damager, int level, double dmg) {
        if (player.getHealth() <= 0.0D)
            return false;
        if (player.getHealth() - dmg <= 0.0D) {
            int soulCost = getPhoenixProcSoulCost(player);
            if (CrystalAPI.hasCrystalAmount(player, soulCost) && canUsePhoenix(player, level)) {
                CrystalAPI.removeCyrstalsFromGems(player, soulCost);
                last_phoenix.put(player.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis()));
                player.setMetadata("phoenixProcs", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(player.hasMetadata("phoenixProcs") ? (((MetadataValue)player.getMetadata("phoenixProcs").get(0)).asInt() + 1) : 1)));
                GeneralUtils.setPlayerHealth((LivingEntity)player, player.getMaxHealth());
                player.sendMessage("");
                player.sendMessage(Utils.color("&6&lPHOENIX "));
                        player.sendMessage(Utils.color("&c- " + soulCost + " Crystals"));
                player.sendMessage(Utils.color("&8&l&7You have &n" + CrystalAPI.getAllCrystals(player) + "&r&7 crystals left."));
                player.sendMessage("");
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.25F);
                for (Entity ent : player.getNearbyEntities(48.0D, 48.0D, 48.0D)) {
                    if (ent instanceof Player) {
                        ((Player)ent).sendMessage(Utils.color("&c&lPHOENIX (&7" + player.getName() + ", -" + soulCost + " souls&c&l) "));
                        ((Player)ent).playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.25F);
                    }
                }
                Location loc = player.getLocation();
                //ParticleEffect.FLAME.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.7F, 80, loc, 100.0D);
                //ParticleEffect.LAVA.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.7F, 20, loc, 100.0D);
                return true;
            }
            if (!player.hasMetadata("outOfSoulsMessage") || ((MetadataValue)player.getMetadata("outOfSoulsMessage").get(0)).asLong() < System.currentTimeMillis()) {
                //ParticleEffect.LAVA.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.4F, 20, player.getEyeLocation(), 100.0D);
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7F, 0.4F);
                player.sendMessage(Utils.color("&c&lOUT OF SOULS "));
                        player.setMetadata("outOfSoulsMessage", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis() + 15000L)));
            }
        }
        return false;
    }

    public static boolean canUsePhoenix(Player pl, int enchantLevel) {
        return (!last_phoenix.containsKey(pl.getUniqueId().toString()) || System.currentTimeMillis() - ((Long)last_phoenix.get(pl.getUniqueId().toString())).longValue() > ((4 - enchantLevel) * 60000));
    }

    public static int getPhoenixProcSoulCost(Player player) {
        int soulCost = 500;
        int procsSinceOutOfCombat = player.hasMetadata("phoenixProcs") ? ((MetadataValue)player.getMetadata("phoenixProcs").get(0)).asInt() : 0;
        while (procsSinceOutOfCombat-- > 0)
            soulCost *= 2;
        soulCost = Math.min(soulCost, 8000);
        return soulCost;
    }

    protected static HashMap<String, Long> last_phoenix = new HashMap<>();

    public static Map<UUID, Integer> phoenixEquipped = new HashMap<>();
}
