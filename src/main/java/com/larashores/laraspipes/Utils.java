package com.larashores.laraspipes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class Utils {
    public static boolean isChestAdjacent(Level level, BlockPos pos) {
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
                if (blockEntity instanceof ChestBlockEntity) {
                    return true; // A chest is found adjacent to the position
                }
            }
        }
        return false; // No chests found adjacent to the position
    }
}
