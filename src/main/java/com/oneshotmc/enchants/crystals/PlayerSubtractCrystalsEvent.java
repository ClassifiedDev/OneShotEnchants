package com.oneshotmc.enchants.crystals;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSubtractCrystalsEvent extends Event implements Cancellable {
    public PlayerSubtractCrystalsEvent(Player player, int soulsToSubtract) {
        this.cancelled = false;
        this.player = player;
        this.soulsToSubtract = soulsToSubtract;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getSoulsToSubtract() {
        return this.soulsToSubtract;
    }

    public void setSoulsToSubtract(int soulsToSubtract) {
        this.soulsToSubtract = soulsToSubtract;
    }

    private static HandlerList handlerList = new HandlerList();

    private boolean cancelled;

    private Player player;

    private int soulsToSubtract;
}
