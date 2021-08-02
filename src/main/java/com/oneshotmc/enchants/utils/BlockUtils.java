package com.oneshotmc.enchants.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockUtils {
    public static List<Block> getCube(Location loc, Integer radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = radius.intValue() * -1 - 1; x <= radius.intValue() + 1; x++) {
            for (int y = radius.intValue() * -1; y <= radius.intValue(); y++) {
                for (int z = radius.intValue() * -1 - 1; z <= radius.intValue() + 1; z++) {
                    Block b = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                    if (!b.getType().equals(Material.AIR) && b != null)
                        blocks.add(b);
                }
            }
        }
        return blocks;
    }

    private static List<Block> getBlocks(Location base, int changeX, int changeY, int changeZ) {
        List<Block> blocks = new ArrayList<>();
        for (int x = base.getBlockX() - changeX; x <= base.getBlockX() + changeX; x++) {
            for (int y = base.getBlockY() - changeY; y <= base.getBlockY() + changeY; y++) {
                for (int z = base.getBlockZ() - changeZ; z <= base.getBlockZ() + changeZ; z++) {
                    Location loc = new Location(base.getWorld(), x, y, z);
                    Block b = loc.getBlock();
                    if (!b.getType().equals(Material.AIR) && b != null)
                        blocks.add(b);
                }
            }
        }
        return blocks;
    }

    private static List<Block> getBlocks(Location base, int changeX, int changeXRight, int changeY, int changeYRight, int changeZRight, int changeZ) {
        List<Block> blocks = new ArrayList<>();
        for (int x = base.getBlockX() - changeX; x <= base.getBlockX() + changeXRight; x++) {
            for (int y = base.getBlockY() - changeY; y <= base.getBlockY() + changeYRight; y++) {
                for (int z = base.getBlockZ() - changeZ; z <= base.getBlockZ() + changeZRight; z++) {
                    if (y > 0) {
                        Location loc = new Location(base.getWorld(), x, y, z);
                        Block b = loc.getBlock();
                        if (!b.getType().equals(Material.AIR) && b != null)
                            blocks.add(b);
                    }
                }
            }
        }
        return blocks;
    }

    private static List<Block> getBlocksUpDown(Location base, int changeX, int changeXRight, int changeY, int changeYRight, int changeZ, int changeZRight, int depthY) {
        List<Block> blocks = new ArrayList<>();
        boolean depthPos = (depthY >= 0);
        Block baseBlock = base.getBlock();
        for (int i = 0; i < Math.abs(depthY); i++) {
            if (i == 0) {
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeXRight, changeY, changeYRight, changeZRight, changeZ));
            } else {
                baseBlock = baseBlock.getLocation().add(0.0D, depthPos ? 1.0D : -1.0D, 0.0D).getBlock();
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeXRight, changeY, changeYRight, changeZRight, changeZ));
            }
        }
        return blocks;
    }

    private static List<Block> getBlocksUpDown(Location base, int changeX, int changeY, int changeZ, int depthY) {
        List<Block> blocks = new ArrayList<>();
        boolean depthPos = (depthY >= 0);
        Block baseBlock = base.getBlock();
        for (int i = 0; i < Math.abs(depthY); i++) {
            if (i == 0) {
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeY, changeZ));
            } else {
                baseBlock = baseBlock.getLocation().add(0.0D, depthPos ? 1.0D : -1.0D, 0.0D).getBlock();
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeY, changeZ));
            }
        }
        return blocks;
    }

    private static List<Block> getBlocksWestEast(Location base, int changeX, int changeY, int changeZ, int depthX) {
        List<Block> blocks = new ArrayList<>();
        boolean depthPos = (depthX >= 0);
        Block baseBlock = base.getBlock();
        for (int i = 0; i < Math.abs(depthX); i++) {
            if (i == 0) {
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeY, changeZ));
            } else {
                baseBlock = baseBlock.getLocation().add(depthPos ? 1.0D : -1.0D, 0.0D, 0.0D).getBlock();
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeY, changeZ));
            }
        }
        return blocks;
    }

    private static List<Block> getBlocksWestEast(Location base, int changeX, int changeXRight, int changeY, int changeYRight, int changeZ, int changeZRight, int depthX) {
        List<Block> blocks = new ArrayList<>();
        boolean depthPos = (depthX >= 0);
        Block baseBlock = base.getBlock();
        for (int i = 0; i < Math.abs(depthX); i++) {
            if (i == 0) {
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeXRight, changeY, changeYRight, changeZRight, changeZ));
            } else {
                baseBlock = baseBlock.getLocation().add(depthPos ? 1.0D : -1.0D, 0.0D, 0.0D).getBlock();
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeXRight, changeY, changeYRight, changeZRight, changeZ));
            }
        }
        return blocks;
    }

    private static List<Block> getBlocksNorthSouth(Location base, int changeX, int changeY, int changeZ, int depthZ) {
        List<Block> blocks = new ArrayList<>();
        boolean depthPos = (depthZ >= 0);
        Block baseBlock = base.getBlock();
        for (int i = 0; i < Math.abs(depthZ); i++) {
            if (i == 0) {
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeY, changeZ));
            } else {
                baseBlock = baseBlock.getLocation().add(0.0D, 0.0D, depthPos ? 1.0D : -1.0D).getBlock();
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeY, changeZ));
            }
        }
        return blocks;
    }

    private static List<Block> getBlocksNorthSouth(Location base, int changeX, int changeXRight, int changeY, int changeYRight, int changeZ, int changeZRight, int depthZ) {
        List<Block> blocks = new ArrayList<>();
        boolean depthPos = (depthZ >= 0);
        Block baseBlock = base.getBlock();
        for (int i = 0; i < Math.abs(depthZ); i++) {
            if (i == 0) {
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeXRight, changeY, changeYRight, changeZRight, changeZ));
            } else {
                baseBlock = baseBlock.getLocation().add(0.0D, 0.0D, depthPos ? 1.0D : -1.0D).getBlock();
                blocks.addAll(getBlocks(baseBlock.getLocation(), changeX, changeXRight, changeY, changeYRight, changeZRight, changeZ));
            }
        }
        return blocks;
    }

    public static List<Block> getSquareRaw(Block b, BlockFace face) {
        List<Block> blocks = new ArrayList<>();
        switch (face) {
            case UP:
                blocks.addAll(getBlocks(b.getLocation(), 1, 0, 1));
                break;
            case DOWN:
                blocks.addAll(getBlocks(b.getLocation(), 1, 0, 1));
                break;
            case EAST:
                blocks.addAll(getBlocks(b.getLocation(), 0, 1, 1));
                break;
            case WEST:
                blocks.addAll(getBlocks(b.getLocation(), 0, 1, 1));
                break;
            case NORTH:
                blocks.addAll(getBlocks(b.getLocation(), 1, 1, 0));
                break;
            case SOUTH:
                blocks.addAll(getBlocks(b.getLocation(), 1, 1, 0));
                break;
        }
        return blocks;
    }

    public static Set<Block> getAtomicSquare(Block b, BlockFace face, int level) {
        int radiusXLeft, radiusXRight, radiusZLeft, radiusZRight;
        Set<Block> blocks = new HashSet<>();
        int radius = 1;
        int depth = getAtomicDepthByLevel(level);
        if (depth == 0)
            return blocks;
        if (level == 1) {
            radiusXLeft = 1;
            radiusXRight = 2;
            radiusZLeft = 1;
            radiusZRight = 2;
        } else if (level == 2) {
            radiusXLeft = 2;
            radiusXRight = 2;
            radiusZLeft = 2;
            radiusZRight = 2;
        } else if (level == 3) {
            radiusXLeft = 2;
            radiusXRight = 3;
            radiusZLeft = 2;
            radiusZRight = 3;
        } else if (level == 4) {
            radiusXLeft = 3;
            radiusXRight = 3;
            radiusZLeft = 3;
            radiusZRight = 3;
        } else {
            radiusXLeft = 2;
            radiusXRight = 1;
            radiusZLeft = 2;
            radiusZRight = 1;
        }
        switch (face) {
            case UP:
                blocks.addAll(getBlocksUpDown(b.getLocation(), radiusXLeft, radiusXRight, 0, 0, radiusZLeft, radiusZRight, -depth));
                break;
            case DOWN:
                blocks.addAll(getBlocksUpDown(b.getLocation(), radiusXLeft, radiusXRight, 0, 0, radiusZLeft, radiusZRight, depth));
                break;
            case EAST:
                blocks.addAll(getBlocksWestEast(b.getLocation(), 0, 0, radiusXLeft, radiusXRight, radiusZLeft, radiusZRight, -depth));
                break;
            case WEST:
                blocks.addAll(getBlocksWestEast(b.getLocation(), 0, 0, radiusXLeft, radiusXRight, radiusZLeft, radiusZRight, depth));
                break;
            case NORTH:
                blocks.addAll(getBlocksNorthSouth(b.getLocation(), radiusXLeft, radiusXRight, radiusZLeft, radiusZRight, 0, 0, depth));
                break;
            case SOUTH:
                blocks.addAll(getBlocksNorthSouth(b.getLocation(), radiusXLeft, radiusXRight, radiusZLeft, radiusZRight, 0, 0, -depth));
                break;
        }
        return blocks;
    }

    public static Set<Block> getSquare(Block b, BlockFace face, int level) {
        Set<Block> blocks = new HashSet<>();
        int radius = 1;
        int depth = getDepthByLevel(level);
        if (depth == 0)
            return blocks;
        switch (face) {
            case UP:
                blocks.addAll(getBlocksUpDown(b.getLocation(), 1, 0, 1, -depth));
                break;
            case DOWN:
                blocks.addAll(getBlocksUpDown(b.getLocation(), 1, 0, 1, depth));
                break;
            case EAST:
                blocks.addAll(getBlocksWestEast(b.getLocation(), 0, 1, 1, -depth));
                break;
            case WEST:
                blocks.addAll(getBlocksWestEast(b.getLocation(), 0, 1, 1, depth));
                break;
            case NORTH:
                blocks.addAll(getBlocksNorthSouth(b.getLocation(), 1, 1, 0, depth));
                break;
            case SOUTH:
                blocks.addAll(getBlocksNorthSouth(b.getLocation(), 1, 1, 0, -depth));
                break;
        }
        return blocks;
    }

    private static int getDepthByLevel(int level) {
        int depth = 0;
        if (level == 1 || level == 2) {
            if (Math.random() >= level * 0.33D)
                return 0;
            depth = 1;
        }
        if (level == 3)
            depth = 1;
        if (level == 4 || level == 5)
            if (Math.random() < (level - 3) * 0.33D) {
                depth = 2;
            } else {
                depth = 1;
            }
        if (level == 6)
            depth = 2;
        if (level == 7 || level == 8)
            if (Math.random() < (level - 6) * 0.33D) {
                depth = 3;
            } else {
                depth = 2;
            }
        if (level == 9)
            depth = 3;
        return depth;
    }

    private static int getAtomicDepthByLevel(int level) {
        return Math.min(9, 3 + level);
    }
}
