package com.oneshotmc.enchants.enchants.tools;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ObsidianDestroyer extends CustomEnchantment {
    public ObsidianDestroyer() {
        super("Obsidian Destroyer", GeneralUtils.pickaxe, 6);
        this.max = 5;
        this.base = 10.0D;
        this.interval = 10.0D;
    }

    public void applyMiscEffect(final Player player, int enchantLevel, final PlayerInteractEvent event) {
        Player pl = player;
        final double level = enchantLevel;
        if (event.isCancelled())
            return;
        if (WorldGuardUtils.isProtected(event.getClickedBlock()))
            return;
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.hasBlock() && event.getClickedBlock().getType() == Material.OBSIDIAN)
            Bukkit.getScheduler().runTaskAsynchronously(OneShotEnchants.getInstance(), new Runnable() {
                public void run() {
                    FLocation faction_loc = new FLocation(event.getClickedBlock().getLocation());
                    Faction l_faction = Board.getInstance().getFactionAt(faction_loc);
                    boolean can_build = false;
                    if (l_faction == null || l_faction.isNone())
                        can_build = true;
                    if (!can_build) {
                        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
                        if (l_faction.playerHasOwnershipRights(fplayer, faction_loc))
                            can_build = true;
                    }
                    if (!can_build || l_faction.isWarZone() || WorldGuardUtils.isProtected(event.getClickedBlock()))
                        return;
                    double chance = level * 20.0D / 100.0D;
                    if (Math.random() < chance)
                        Bukkit.getScheduler().runTask(OneShotEnchants.getInstance(), new Runnable() {
                            public void run() {
                                Block b = event.getClickedBlock();
                                b.breakNaturally(player.getItemInHand());
                            }
                        });
                }
            });
    }
}
