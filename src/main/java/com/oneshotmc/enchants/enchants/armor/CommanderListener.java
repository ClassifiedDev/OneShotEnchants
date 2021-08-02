package com.oneshotmc.enchants.enchants.armor;

import com.oneshotmc.enchants.enchants.armor.Commander;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

final class CommanderListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Commander.commanders.remove(e.getPlayer().getName());
    }
}
