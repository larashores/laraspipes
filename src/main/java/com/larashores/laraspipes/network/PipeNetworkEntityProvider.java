package com.larashores.laraspipes.network;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Interface that specifies a function that can create a {@link PipeNetworkEntity}.
 *
 * @param <T> The created {@link PipeNetworkEntity}.
 */
@FunctionalInterface
public interface PipeNetworkEntityProvider<T extends PipeNetworkEntity> {
    /**
     * Creates the {@link PipeNetworkEntity}.
     *
     * @param pos The position to create the entity at.
     * @param state The initial state of the entity.
     *
     * @return The created entity.
     */
    T create(BlockPos pos, BlockState state);
}
