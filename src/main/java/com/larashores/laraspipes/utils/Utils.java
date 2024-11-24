package com.larashores.laraspipes.utils;

import com.larashores.laraspipes.itemdepositor.ItemDepositorBlock;
import com.larashores.laraspipes.itemextractor.ItemExtractorBlock;
import com.larashores.laraspipes.itempipe.ItemPipeBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Utils {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Container getAdjacentChest(Level level, BlockPos pos) {
        // Define the positions of the six adjacent blocks
        BlockPos[] adjacentPositions = new BlockPos[]{
            pos.north(),
            pos.south(),
            pos.east(),
            pos.west(),
            pos.above(),
            pos.below()
        };

        // Check each adjacent block
        for (BlockPos adjacentPos : adjacentPositions) {
            var blockEntity = level.getBlockEntity(adjacentPos);
            var blockState = level.getBlockState(adjacentPos);
            var block = blockState.getBlock();

            // Check if the adjacent block is a chest
            if (
                blockState.is(Blocks.CHEST)
                && blockEntity instanceof ChestBlockEntity
                && block instanceof ChestBlock chestBlock
            ) {
                return ChestBlock.getContainer(chestBlock, blockState, level, adjacentPos, false);
            }
        }
        return null;
    }

    public static void transferItems(Set<Item> filters, Container from, Container to) {
        for (var i = 0; i < from.getContainerSize() && !from.isEmpty(); i++) {
            var fromStack = from.getItem(i);
            if (filters.contains(fromStack.getItem())) {
                for (int j = 0; j < to.getContainerSize() && !fromStack.isEmpty(); j++) {
                    var toStack = to.getItem(j);
                    if (toStack.isEmpty()) {
                        var stack = fromStack.copyAndClear();
                        to.setItem(j, stack);
                    } else if (fromStack.getItem() == toStack.getItem() && fromStack.getTag() == toStack.getTag()) {
                        var count = Math.min(fromStack.getCount(), toStack.getMaxStackSize() - toStack.getCount());
                        toStack.grow(count);
                        fromStack.shrink(count);
                    }
                }
            }
        }
    }

    // Returns amount of rotation needed to turn a block to face a direction, assuming North is no rotation.
    public static ScreenRotation getRotation(Direction direction) {
        LOGGER.info("Direction: {} ({}, {})", direction, direction.getRotation().x(), direction.getRotation().y());
        return new ScreenRotation(
            direction == Direction.UP ? 90 : direction == Direction.DOWN ? -90 : 0,
            direction.getAxis().isVertical() ? 0 : (int) direction.toYRot() % 360
        );
    }

    public static void setAdjacentBlockStates(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            var neighborPos = pos.relative(direction);
            var neighborState = level.getBlockState(neighborPos);
            if (neighborState.getBlock() instanceof ItemPipeBlock) {
                neighborState = ItemPipeBlock.setConnectionStates(level, neighborPos, neighborState);
            } else if (neighborState.getBlock() instanceof ItemExtractorBlock) {
                neighborState = ItemExtractorBlock.setConnectionStates(level, neighborPos, neighborState);
            } else if (neighborState.getBlock() instanceof ItemDepositorBlock) {
                neighborState = ItemDepositorBlock.setConnectionStates(level, neighborPos, neighborState);
            }
            level.setBlock(neighborPos, neighborState, Block.UPDATE_ALL);
        }
    }

    public static List<BlockPos> getAdjacentBlockPositions(BlockPos pos) {
        return Arrays.asList(pos.north(), pos.south(), pos.east(), pos.west(), pos.above(), pos.below());
    }
}
