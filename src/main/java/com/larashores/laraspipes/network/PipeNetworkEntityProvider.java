package com.larashores.laraspipes.network;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface PipeNetworkEntityProvider<T extends PipeNetworkEntity> {
    T create(BlockPos pos, BlockState state);
}
