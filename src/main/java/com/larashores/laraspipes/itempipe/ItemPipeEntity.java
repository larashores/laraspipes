package com.larashores.laraspipes.itempipe;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.network.PipeNetworkEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;


public class ItemPipeEntity extends PipeNetworkEntity {
    public ItemPipeEntity(BlockPos pos, BlockState state) {
        super(Registration.PIPE_ENTITY.get(), pos, state);
    }
}
