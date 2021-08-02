package com.oneshotmc.enchants.enchants.tools;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.oneshotmc.enchants.OneShotEnchants;
import com.oneshotmc.enchants.enchantmentapi.CustomEnchantment;
import com.oneshotmc.enchants.enchantmentapi.GeneralUtils;
import com.oneshotmc.enchants.utils.BlockUtils;
import com.oneshotmc.enchants.utils.ParticleEffect;
import com.oneshotmc.enchants.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;

public class Detonate extends CustomEnchantment {
    public Detonate() {
        super("Detonate", GeneralUtils.tools, 6);
        this.max = 9;
        this.base = 10.0D;
        this.interval = 10.0D;
        pickaxeMaterials = new ArrayList<>(Arrays.asList(new Material[] {
                Material.SANDSTONE, Material.STONE, Material.COBBLESTONE, Material.MOSSY_COBBLESTONE, Material.SMOOTH_BRICK, Material.OBSIDIAN, Material.MOB_SPAWNER, Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE,
                Material.DIAMOND_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE, Material.EMERALD_ORE, Material.NETHERRACK }));
        spadeMaterials = new ArrayList<>(Arrays.asList(new Material[] { Material.DIRT, Material.SAND, Material.GRAVEL, Material.GRASS, Material.MYCEL, Material.CLAY }));
        Bukkit.getPluginManager().registerEvents(new DetonateListener(), OneShotEnchants.getInstance());
    }

    public void applyToolEffect(Player player, Block block, int enchantLevel, BlockEvent event) {
        if (event instanceof BlockBreakEvent) {
            BlockBreakEvent bbe = (BlockBreakEvent)event;
            if (!bbe.isCancelled()) {
                Material type = block.getType();
                if (type == Material.REDSTONE_COMPARATOR || type == Material.SPONGE || type == Material.REDSTONE_COMPARATOR_ON || type == Material.REDSTONE_COMPARATOR_OFF)
                    return;
                explode(player, bbe.getBlock(), enchantLevel);
            }
        }
    }

    public static void updateSavedBlockFace(Player p, BlockFace b) {
        loggedBlockFaces.put(p.getUniqueId(), b);
    }

    private BlockFace getBlockFace(Player p) {
        return loggedBlockFaces.containsKey(p.getUniqueId()) ? loggedBlockFaces.get(p.getUniqueId()) : null;
    }

    private void explode(Player pl, Block b, int level) {
        ItemStack pickaxeItem = pl.getItemInHand();
        boolean hasAutoSmelt = OneShotEnchants.itemHasEnchantment(pl.getItemInHand(), "Auto Smelt");
        boolean hasFuse = OneShotEnchants.itemHasEnchantment(pl.getItemInHand(), "Fuse");
        boolean telepathy = OneShotEnchants.itemHasEnchantment(pl.getItemInHand(), "Telepathy");
        boolean autoSell = OneShotEnchants.itemHasEnchantment(pl.getItemInHand(), "Auto Sell");
        boolean pickaxe = pl.getItemInHand().getType().name().contains("_PICKAXE");
        boolean spade = pl.getItemInHand().getType().name().contains("_SPADE");
        double autoSellMoney = 0.0D;
        Set<Block> blocks = BlockUtils.getSquare(b, getBlockFace(pl), level);
        blocks.add(b);
        boolean brokeBlock = false;
        boolean tookDurability = false;
        FPlayer fp = null;
        if (WorldGuardUtils.isProtected(b)) {
            System.out.println("protected");
            return;
        }
        for (Block bl : blocks) {
            Material m = bl.getType();
            if (m == Material.LADDER || m == Material.GLOWSTONE || m == Material.BEDROCK || m == Material.WATER || m == Material.STATIONARY_WATER || m == Material.LAVA || m == Material.STATIONARY_LAVA || m == Material.SIGN || m == Material.SIGN_POST || m == Material.AIR || m == Material.BEDROCK || m == Material.OBSIDIAN || m == Material.SPONGE || m == Material.IRON_DOOR || m == Material.IRON_DOOR_BLOCK || m == Material.WOODEN_DOOR || m == Material.WOOD_DOOR || m == Material.HOPPER || m == Material.ANVIL || m == Material.REDSTONE_COMPARATOR || m == Material.REDSTONE_COMPARATOR_OFF ||
                    m == Material.REDSTONE_COMPARATOR_ON)
                continue;
            if (pickaxeMaterials.contains(m) && !pickaxe)
                continue;
            if (spadeMaterials.contains(m) && !spade)
                continue;
            Block above = bl.getRelative(BlockFace.UP);
            Material aboveType = above.getType();
            if (aboveType == Material.REDSTONE_COMPARATOR || aboveType == Material.REDSTONE_COMPARATOR_OFF || aboveType == Material.REDSTONE_COMPARATOR_ON || aboveType == Material.DIODE || aboveType == Material.DIODE_BLOCK_OFF)
                continue;
            if (aboveType == Material.DIODE_BLOCK_ON)
                continue;
            FLocation floc = new FLocation(bl.getLocation());
            Faction f = Board.getInstance().getFactionAt(floc);
//            if (!f.isNone())
//                continue;
            if (f.isSafeZone() || f.isWarZone() || WorldGuardUtils.isProtected(b))
                continue;
            EntityType spawnerType = null;
            if (m == Material.MOB_SPAWNER) {
                BlockState state = bl.getState();
                if (state instanceof CreatureSpawner)
                    spawnerType = ((CreatureSpawner)state).getSpawnedType();
            }
            //ParticleEffect.EXPLOSION_LARGE.display(0.0F, 0.0F, 0.0F, 0.025F, 1, bl.getLocation().add(0.0D, 0.5D, 0.0D), 100.0D);
            if (bl.getDrops(pl.getItemInHand()).size() > 0) {
                ItemStack loot = null;
                try {
                    loot = (ItemStack)bl.getDrops(pl.getItemInHand()).toArray()[0];
                } catch (Exception err) {
                    continue;
                }
                if (loot.getType().name().startsWith("REDSTONE_COMPARATOR"))
                    loot.setAmount(1);
                if (hasAutoSmelt && (m == Material.IRON_ORE || m == Material.GOLD_ORE) && hasFuse) {
                    if (m == Material.IRON_ORE)
                        loot.setType(Material.IRON_INGOT);
                    if (m == Material.GOLD_ORE)
                        loot.setType(Material.GOLD_INGOT);
                    CustomEnchantment enchantment = OneShotEnchants.getEnchantment("Auto Smelt");
                    Map<CustomEnchantment, Integer> enchants = OneShotEnchants.getEnchantments(pl.getItemInHand());
                    if (enchantment != null && enchants != null) {
                        Integer autoSmeltLevel = enchants.get(enchantment);
                        if (autoSmeltLevel != null)
                            loot.setAmount(autoSmeltLevel.intValue());
                    }
                }
                brokeBlock = true;
                bl.setTypeIdAndData(0, (byte)0, false);
                if (loot.getType() == Material.RAILS)
                    continue;
                if (telepathy && pl.getInventory().firstEmpty() != -1) {
                    pl.getInventory().addItem(new ItemStack[] { loot });
                } else {
                    pl.getWorld().dropItem(pl.getLocation(), loot);
                }
            } else {
                bl.breakNaturally(pl.getItemInHand());
                tookDurability = true;
            }
            if (m != Material.MOB_SPAWNER || f == null || !f.isNormal() || spawnerType == null);
        }
        if (brokeBlock && !tookDurability && pickaxe)
            if (pickaxeItem.getDurability() >= pickaxeItem.getType().getMaxDurability() - 1) {
                if (pickaxeItem.equals(pl.getItemInHand())) {
                    pl.setItemInHand((ItemStack)null);
                } else {
                    pl.getInventory().removeItem(new ItemStack[] { pickaxeItem });
                }
                pl.updateInventory();
                pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
                Bukkit.getLogger().info("Destroying pickaxe " + pickaxeItem + " for " + pl.getName() + " due to durability: " + pickaxeItem.getDurability());
            } else {
                pickaxeItem.setDurability((short)(pickaxeItem.getDurability() + 1));
            }
    }

    private static ConcurrentHashMap<UUID, BlockFace> loggedBlockFaces = new ConcurrentHashMap<>();

    protected static List<Material> pickaxeMaterials;

    protected static List<Material> spadeMaterials;
}
