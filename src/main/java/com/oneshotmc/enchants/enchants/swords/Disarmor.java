package com.oneshotmc.enchants.enchants.swords;

import java.util.HashSet;
import java.util.Random;

import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class Disarmor extends CustomEnchantment {
    public Disarmor() {
        super("Disarmor", GeneralUtils.swords, 2);
        this.max = 8;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {
        if (!(target instanceof Player))
            return;
        if (target.getHealth() > 10.0D)
            return;
        double random = Math.random();
        double chance = 0.0025D * level;
        if (target.hasMetadata("stickyEnchant")) {
            int stickyLevel = ((MetadataValue)target.getMetadata("stickyEnchant").get(0)).asInt();
            chance -= stickyLevel * 0.0025D;
        }
        if (random < chance) {
            unequipRandomArmor((Player)target, (Player)player);
            Location l = target.getLocation();
            l.getWorld().playSound(l, Sound.ANVIL_BREAK, 1.0F, 0.6F);
            //ParticleEffect.DRIP_LAVA.display(0.0F, 0.0F, 0.0F, 0.075F, 10, l.getBlock().getLocation(), 100.0D);
            //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new //ParticleEffect.BlockData(Material.DIAMOND_ORE, (byte)14), 0.4F, 1.0F, 0.4F, 1.0F, 10, l.getBlock().getLocation(), 150.0D);
            //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new //ParticleEffect.BlockData(Material.DIAMOND_ORE, (byte)14), 0.4F, 1.0F, 0.4F, 1.0F, 10, l.getBlock().getRelative(BlockFace.UP).getLocation(), 150.0D);
            //ParticleEffect.BLOCK_CRACK.display((//ParticleEffect.ParticleData)new //ParticleEffect.BlockData(Material.DIAMOND_ORE, (byte)14), 0.4F, 1.0F, 0.4F, 1.0F, 10, l.getBlock().getRelative(BlockFace.UP, 2).getLocation(), 150.0D);
        }
    }

    private void unequipRandomArmor(Player p, Player playerDisarming) {
        int i = (new Random()).nextInt(4);
        ItemStack armor = null;
        if (i == 1) {
            armor = p.getEquipment().getHelmet();
            p.getEquipment().setHelmet((ItemStack)null);
        } else if (i == 2) {
            armor = p.getEquipment().getChestplate();
            p.getEquipment().setChestplate((ItemStack)null);
        } else if (i == 3) {
            armor = p.getEquipment().getLeggings();
            p.getEquipment().setLeggings((ItemStack)null);
        } else if (i == 4) {
            armor = p.getEquipment().getBoots();
            p.getEquipment().setBoots((ItemStack)null);
        }
        if (armor != null)
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(new ItemStack[] { armor });
            } else {
                p.getWorld().dropItem(p.getLocation(), armor);
            }
        p.sendMessage("");
        p.sendMessage("");
        p.sendMessage("");
        p.sendMessage(Utils.color("&6&lDISARMORED &7(by:" + playerDisarming.getName() + ")"));
        p.sendMessage(ChatColor.GRAY + "A random piece of your armor has been unequipped. Re-equip it quickly before you get dropped!");
        p.sendMessage("");
        p.sendMessage("");
        p.sendMessage("");
        playerDisarming.sendMessage(Utils.color("&a&lDISARMED " + p.getName() + " "));
    }
}
