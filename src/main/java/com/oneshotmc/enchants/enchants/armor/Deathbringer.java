package com.oneshotmc.enchants.enchants.armor;

import java.util.HashMap;
import java.util.HashSet;
import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Deathbringer extends CustomEnchantment {
    public Deathbringer() {
        super("Deathbringer", GeneralUtils.armor, 9);
        this.max = 3;
        this.base = 25.0D;
        this.interval = 10.0D;
        Bukkit.getPluginManager().registerEvents(new DeathbringEvents(), OneShotEnchants.getInstance());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        DeathbringEvents.doubleDamage.add(player);
        DeathbringEvents.playerLevels.put(player, Integer.valueOf(enchantLevel));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        DeathbringEvents.doubleDamage.remove(player);
        DeathbringEvents.playerLevels.remove(player);
    }

    private static class DeathbringEvents implements Listener {
        private DeathbringEvents() {}

        @EventHandler
        public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
            Entity damage = e.getDamager();
            if ((damage instanceof Projectile && doubleDamage.contains(((Projectile)damage).getShooter())) || doubleDamage.contains(damage)) {
                double random = Math.random();
                double chance = 0.1D;
                if (playerLevels.containsKey((damage instanceof Projectile) ? ((Projectile) damage).getShooter() : damage)) {
                    chance = chance * playerLevels.get((damage instanceof Projectile) ? ((Projectile) damage).getShooter() : damage);
                }

                if (random < chance)
                    e.setDamage(e.getDamage() * 1.2D);
            }
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent e) {
            doubleDamage.remove(e.getPlayer());
            playerLevels.remove(e.getPlayer());
        }

        private static HashSet<Player> doubleDamage = new HashSet<>();

        private static HashMap<Player, Integer> playerLevels = new HashMap<>();
    }
}
