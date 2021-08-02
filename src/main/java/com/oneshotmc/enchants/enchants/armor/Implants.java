package com.oneshotmc.enchants.enchants.armor;

import com.google.common.collect.HashMultimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Implants extends CustomEnchantment {
    public Implants() {
        super("Implants", GeneralUtils.helmets, 2);
        this.healthTickedPlayers = new HashSet<>();
        this.implantedPlayers = HashMultimap.create();
        this.max = 2;
        this.base = 10.0D;
        this.interval = 5.0D;
        for (int i = 1; i <= this.max; i++) {
            final int finalI = i;
            BukkitTask existing = implantTasks.get(Integer.valueOf(i));
            if (existing != null)
                existing.cancel();
            BukkitTask task = (new BukkitRunnable() {
                public void run() {
                    Collection<Player> leveledPlayers = Implants.this.implantedPlayers.get(Integer.valueOf(finalI));
                    if (leveledPlayers == null || leveledPlayers.isEmpty())
                        return;
                    leveledPlayers.forEach(player -> {
//                        if (Implants.this.healthTickedPlayers.remove(player.getUniqueId())) {
//                            if (player.getHealth() + 1.0D < player.getMaxHealth() && player.getHealth() > 0.0D && !player.isDead()) {
//                                player.setHealth(player.getHealth() + 1.0D);
//                                player.playEffect(player.getLocation().add(0.0D, 1.7D, 0.0D), Effect.STEP_SOUND, 80);
//                            }
//                        } else {
//                            Implants.this.healthTickedPlayers.add(player.getUniqueId());
//                        }
                        if (player.getFoodLevel() < 20 && player.getHealth() > 0.0D && !player.isDead()) {
                            player.setFoodLevel(20);
                            player.playEffect(player.getLocation().add(0.0D, 1.7D, 0.0D), Effect.STEP_SOUND, 57);
                        }
                    });
                }
            }).runTaskTimer(OneShotEnchants.getInstance(), (60 * 3 / i), (60 * 3 / i));
            implantTasks.put(Integer.valueOf(i), task);
        }
    }

    public void applyEquipEffect(Player player, int level) {
        player.setMetadata(name() + "_enchant", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Integer.valueOf(level)));
        this.implantedPlayers.put(Integer.valueOf(level), player);
    }

    public void applyUnequipEffect(Player player, int level) {
        player.removeMetadata(name() + "_enchant", OneShotEnchants.getInstance());
        this.implantedPlayers.remove(Integer.valueOf(level), player);
        this.healthTickedPlayers.remove(player.getUniqueId());
    }

    private static HashMap<Integer, BukkitTask> implantTasks = new HashMap<>();

    private Set<UUID> healthTickedPlayers;

    private HashMultimap<Integer, Player> implantedPlayers;
}
