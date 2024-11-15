package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.Set;

public class Utils {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ChestBlockEntity getAdjacentChest(Level level, BlockPos pos) {
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
            BlockState blockState = level.getBlockState(adjacentPos);

            // Check if the adjacent block is a chest
            if (blockState.is(Blocks.CHEST)) {
                // Optionally, verify it's a chest with a BlockEntity (to ensure it's not a custom block)
                BlockEntity blockEntity = level.getBlockEntity(adjacentPos);
                if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                    return chestBlockEntity;
                }
            }
        }
        return null;
    }

    public static void transferItems(Set<Item> filters, ChestBlockEntity from, ChestBlockEntity to) {
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
