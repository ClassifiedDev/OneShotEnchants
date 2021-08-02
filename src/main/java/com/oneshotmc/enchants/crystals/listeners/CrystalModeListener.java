package com.oneshotmc.enchants.crystals.listeners;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.crystals.CrystalAPI;
import com.oneshotmc.enchants.utils.MinecraftServerUtils;
import com.oneshotmc.enchants.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class CrystalModeListener implements Listener {
    @EventHandler
    public void onPlayerToggleSoulMode(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && CrystalAPI.isMythicCrystal(e.getItem())) {
            Player p = e.getPlayer();
            if (CrystalAPI.hasCrystalAmount(p, 1)) {
                CrystalAPI.toggleCrystalMode(p);
                if (CrystalAPI.hasCrystalMode(p)) {
                    p.sendMessage("");
                    p.sendMessage(Utils.color("&a&lMYTHIC MODE: &a&l&nON&r &a&l"));
                            p.sendMessage(Utils.color("&7Active mythic enchantments will now drain crystals from gems."));
                    p.sendMessage("");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.2F);
                } else {
                    p.sendMessage("");
                    p.sendMessage(Utils.color("&c&lMYTHIC MODE: &c&l&nOFF&r &c&l"));
                            p.sendMessage(Utils.color("&7Mythic enchantments will no longer drain crystals from gems."));
                    p.sendMessage("");
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 1.2F);
                }
            } else {
                p.sendMessage(Utils.color("&c&l(!) &cYou cannot enable mythic mode without any crystals!"));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerItemHeld(PlayerItemHeldEvent e) {
        e.getPlayer().setMetadata("lastItemHeldChange", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick())));
    }
}
