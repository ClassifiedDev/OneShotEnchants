package com.oneshotmc.enchants.enchants.axes;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.util.player.UserManager;
import java.util.List;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

public class Cleave extends CustomEnchantment {
    boolean mcmmo;

    public Cleave() {
        super("Cleave", GeneralUtils.axe, 2);
        this.mcmmo = false;
        this.max = 7;
        this.base = 15.0D;
        this.interval = 5.0D;
        this.mcmmo = Bukkit.getPluginManager().isPluginEnabled("mcMMO");
        Bukkit.getLogger().info("[Enchantments (Cleave)] mcMMO support = " + this.mcmmo);
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double radius = 0.45D * level;
        double damage = (level <= 3) ? 1.0D : ((level <= 6) ? 2.0D : ((level == 7) ? 3.0D : 0.0D));
        if (player.hasMetadata("previous_cleave_use") && System.currentTimeMillis() - ((MetadataValue)player.getMetadata("previous_cleave_use").get(0)).asLong() <= 1500L)
            return;
        if (player.hasMetadata("skullSplitterProc") && System.currentTimeMillis() - ((MetadataValue)player.getMetadata("skullSplitterProc").get(0)).asLong() <= 1000L)
            return;
        player.setMetadata("previous_cleave_use", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
        if (this.mcmmo) {
            Player p = (Player)player;
            try {
                if (UserManager.getPlayer(p) != null) {
                    McMMOPlayer mcMMOPlayer = UserManager.getPlayer(p);
                    if (mcMMOPlayer.getAxesManager() != null && mcMMOPlayer.getAxesManager().canUseSkullSplitter(playerHit))
                        return;
                }
            } catch (IndexOutOfBoundsException err) {
                Bukkit.getLogger().info("[Enchantments (Cleave)] Player " + p.getName() + " has null mcMMO profile? Assuming skullSplitter=true!");
                return;
            }
        }
        if (damage > 0.0D && e.getDamage() > 0.0D) {
            List<Entity> aoe = e.getEntity().getNearbyEntities(radius, radius, radius);
            for (Entity aoe_ent : aoe) {
                if (aoe_ent.equals(player))
                    continue;
                if (aoe_ent.hasMetadata("spectator"))
                    continue;
                if (!(aoe_ent instanceof LivingEntity))
                    continue;
                if (aoe_ent instanceof Player && isAlly((Player)player, (Player)aoe_ent))
                    continue;
                LivingEntity le_ent = (LivingEntity)aoe_ent;
                if (le_ent.hasMetadata("lastCleaveDamage") && System.currentTimeMillis() - ((MetadataValue)le_ent.getMetadata("lastCleaveDamage").get(0)).asLong() < 1000L)
                    continue;
                le_ent.setMetadata("lastCleaveDamage", (MetadataValue)new FixedMetadataValue(OneShotEnchants.getInstance(), Long.valueOf(System.currentTimeMillis())));
                le_ent.damage(damage);
            }
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtils.isAlly(p1, p2);
    }

    public void pushAwayEntity(Entity pusher, Entity entity, double speed) {
        if (entity instanceof org.bukkit.entity.Horse)
            return;
        Vector unitVector = entity.getLocation().toVector().subtract(pusher.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
