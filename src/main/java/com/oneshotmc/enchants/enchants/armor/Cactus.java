package com.oneshotmc.enchants.enchants.armor;

import java.util.concurrent.ConcurrentHashMap;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Cactus extends CustomEnchantment {
    protected static ConcurrentHashMap<String, Integer> cactus;

    public Cactus() {
        super("Cactus", GeneralUtils.armor, 2);
        this.max = 2;
        this.base = 20.0D;
        this.interval = 5.0D;
        cactus = new ConcurrentHashMap<>();
        Bukkit.getPluginManager().registerEvents(new CactusListener(), OneShotEnchants.getInstance());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        cactus.put(player.getName(), Integer.valueOf(enchantLevel));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        cactus.remove(player.getName());
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            Player p = (Player)player;
            if (cactus.containsKey(p.getName()) && attacker != null && attacker instanceof Player) {
                Player pAttacker = (Player)attacker;
                if (pAttacker != null) {
                    pAttacker.damage(0.5D * level);
                    Location l = p.getLocation();
                    //ParticleEffect.BLOCK_CRACK.display(new ParticleEffect.BlockData(Material.CACTUS, (byte)0), (float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0F, 50, l, 75.0D);
                }
            }
        }
    }
}
