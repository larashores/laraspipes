package com.larashores.laraspipes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ItemDepositorEntityIterable implements Iterable<ItemDepositorEntity> {
    private final Level level;
    private final BlockPos start;

    public ItemDepositorEntityIterable(Level level, BlockPos start) {
        this.level = level;
        this.start = start;
    }

    @Override
    public @NotNull Iterator<ItemDepositorEntity> iterator() {
        return new ItemDepositorEntityIterator(level, start);
    }
}
