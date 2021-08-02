package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchants.armor.Cactus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

final class CactusListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Cactus.cactus.remove(e.getPlayer().getName());
    }
}
