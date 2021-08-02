package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.utils.ArmorSlot;
import com.oneshotmc.enchants.utils.ArmorUtil;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

final class ImmortalListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            for (ItemStack i : p.getInventory().getArmorContents()) {
                if (i != null && OneShotEnchants.itemHasEnchantment(i, "Immortal")) {
                    int enchLevel = ((Integer)OneShotEnchants.getEnchantments(i).get(OneShotEnchants.getEnchantment("Immortal"))).intValue();
                    int soulCost = 5 - enchLevel;
                    soulCost = Math.max(1, soulCost);
                    if (CrystalAPI.hasCrystalAmount(p, soulCost)) {
                        if (p.hasMetadata("soulTrap") && ((MetadataValue)p.getMetadata("soulTrap").get(0)).asLong() > System.currentTimeMillis())
                            return;
                        CrystalAPI.removeCyrstalsFromGems(p, soulCost);
                        if (CrystalAPI.getAllCrystals(p) % 20 == 0) {
                            p.sendMessage("");
                            p.sendMessage(Utils.color("&6&lIMMORTAL "));
                                    p.sendMessage(Utils.color("&8&l&7You have &n" + CrystalAPI.getAllCrystals(p) + "&r&7 crystals left."));
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 0.65F);
                            p.sendMessage("");
                        }
                        ArmorUtil.modifyPlayerArmor(p, ArmorSlot.HELMET, 2);
                        ArmorUtil.modifyPlayerArmor(p, ArmorSlot.CHESTPLATE, 2);
                        ArmorUtil.modifyPlayerArmor(p, ArmorSlot.LEGGINGS, 2);
                        ArmorUtil.modifyPlayerArmor(p, ArmorSlot.BOOTS, 2);
                        if (e instanceof EntityDamageByEntityEvent) {
                            EntityDamageByEntityEvent ed = (EntityDamageByEntityEvent)e;
                            if (ed.getDamager() instanceof Player) {
                                Player pAttacker = (Player)ed.getDamager();
                                ArmorSlot slot = ArmorSlot.getArmorType(i);
                                if (slot != null);
                            }
                        }
                        return;
                    }
                    if (!p.hasMetadata("outOfSoulsMessage") || ((MetadataValue)p.getMetadata("outOfSoulsMessage").get(0)).asLong() < System.currentTimeMillis()) {
                        //ParticleEffect.LAVA.display((float)Math.random(), (float)Math.random(), (float)Math.random(), 0.4F, 20, p.getEyeLocation(), 100.0D);
                        p.playSound(p.getLocation(), Sound.ITEM_BREAK, 0.7F, 0.4F);
                        p.sendMessage(Utils.color("&c&lOUT OF CRYSTALS "));
                                p.setMetadata("outOfSoulsMessage", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis() + 15000L)));
                        return;
                    }
                }
            }
        }
    }
}
