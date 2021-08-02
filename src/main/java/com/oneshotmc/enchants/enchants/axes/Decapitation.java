package com.oneshotmc.enchants.enchants.axes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.TimeZone;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.Utils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Decapitation extends CustomEnchantment {
    public Decapitation() {
        super("Decapitation", GeneralUtils.axe, 7);
        this.max = 3;
        this.base = 30.0D;
        this.interval = 10.0D;
        Bukkit.getPluginManager().registerEvents(new DecapitationEvents(), OneShotEnchants.getInstance());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!(target instanceof Player))
            return;
        Player ptarget = (Player)target;
        double random = Math.random();
        double chance = 0.03D * enchantLevel;
        if (random < chance)
            DecapitationEvents.decapitationEnchant.add(ptarget.getName());
    }

    private static class DecapitationEvents implements Listener {
        private DecapitationEvents() {}

        private static HashSet<String> decapitationEnchant = new HashSet<>();

        private static Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        @EventHandler(priority = EventPriority.LOW)
        public void onPlayerDeath(PlayerDeathEvent e) {
            Player p_dead = e.getEntity();
            if (decapitationEnchant.contains(p_dead.getName())) {
                Bukkit.getLogger().info("HEADLESS ENCHANT: Dropping skull of " + p_dead.getName() + "!");
                if (p_dead.getKiller() != null && p_dead.getKiller() instanceof Player) {
                    e.getDrops().add(getPlayerHead(p_dead, p_dead.getKiller()));
                } else {
                    e.getDrops().add(GeneralUtils.getPlayerHead(p_dead.getName()));
                }
                decapitationEnchant.remove(p_dead.getName());
            }
        }

        private ItemStack getPlayerHead(Player victim, Player killer) {
            ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
            SkullMeta sm = (SkullMeta)is.getItemMeta();
            ArrayList<String> headLore = new ArrayList<>();
            sm.setOwner(victim.getName());
            sm.setDisplayName(Utils.color("&c&l" + victim.getName() + "'s Head"));
            headLore.add(Utils.color("&8&l&cKilled by: &7" + killer.getDisplayName()));
            headLore.add(Utils.color("&8&l&cLocation: &7" + Math.round(killer.getLocation().getX()) + "x, " + Math.round(killer.getLocation().getY()) + "y, " + Math.round(killer.getLocation().getZ()) + "z"));
            headLore.add(Utils.color("&8&l&cTime: &7" + Utils.getDateAndTime()));
            headLore.add(Utils.color("&8&l&9Defeated with: &7" + getItemName(killer.getItemInHand())));
            sm.setLore(headLore);
            is.setItemMeta((ItemMeta)sm);
            return is;
        }

        private String getItemName(ItemStack i) {
            if (i == null || i.getType() == Material.AIR)
                return "Fists";
            if (i.hasItemMeta() && i.getItemMeta().hasDisplayName())
                return i.getItemMeta().getDisplayName();
            return WordUtils.capitalize(i.getType().name());
        }
    }
}
