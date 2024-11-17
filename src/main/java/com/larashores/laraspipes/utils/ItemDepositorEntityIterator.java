package com.larashores.laraspipes.utils;

import com.larashores.laraspipes.itemdepositor.ItemDepositorBlock;
import com.larashores.laraspipes.itemdepositor.ItemDepositorEntity;
import com.larashores.laraspipes.itemextractor.ItemExtractorBlock;
import com.larashores.laraspipes.itempipe.ItemPipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.*;

public class ItemDepositorEntityIterator implements Iterator<ItemDepositorEntity> {
    private final Level level;
    private final List<ItemDepositorEntity> entityQueue = new LinkedList<>();
    private final List<BlockPos> positionQueue = new LinkedList<>();
    private final Set<BlockPos> traversed = new HashSet<>();

    public ItemDepositorEntityIterator(Level level, BlockPos start) {
        this.level = level;
        positionQueue.add(start);
        traversed.add(start);
    }

    @Override
    public boolean hasNext() {
        if (!entityQueue.isEmpty()) {
            return true;
        }
        while (!positionQueue.isEmpty()) {
            var current = positionQueue.remove(0);
            BlockPos[] positions = new BlockPos[]{
                current.north(),
                current.south(),
                current.east(),
                current.west(),
                current.above(),
                current.below()
            };
            for (BlockPos position : positions) {
                if (!traversed.contains(position)) {
                    traversed.add(position);
                    var state = level.getBlockState(position);
                    var block = state.getBlock();

                    if (
                        block instanceof ItemPipe ||
                        block instanceof ItemExtractorBlock ||
                        block instanceof ItemDepositorBlock
                    ) {
                        positionQueue.add(position);
                    }

                    var entity = level.getBlockEntity(position);
                    if (entity instanceof ItemDepositorEntity depositorEntity) {
                        entityQueue.add(depositorEntity);
                    }
                }
            }
            if (!entityQueue.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemDepositorEntity next() {
        return entityQueue.remove(0);
    }
}
