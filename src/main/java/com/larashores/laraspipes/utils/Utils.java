package com.larashores.laraspipes.utils;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.slf4j.Logger;

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
}
