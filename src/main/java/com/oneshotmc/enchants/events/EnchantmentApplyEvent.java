package com.oneshotmc.enchants.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class EnchantmentApplyEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private boolean isCancelled;

    Player p;

    ItemStack itemStack;

    private EnchantmentApplyOutcome outcome;

    public EnchantmentApplyEvent(Player p, ItemStack itemStack, EnchantmentApplyOutcome outcome) {
        this.p = p;
        this.itemStack = itemStack;
        this.outcome = outcome;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public enum EnchantmentApplyOutcome {
        SUCCESS, FAIL, DESTROY;
    }
}
