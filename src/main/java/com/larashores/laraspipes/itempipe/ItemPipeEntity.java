package com.larashores.laraspipes.itempipe;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.network.PipeNetwork;
import com.larashores.laraspipes.network.PipeNetworkEntity;
import com.larashores.laraspipes.itemdepositor.ItemDepositorEntity;
import com.larashores.laraspipes.itemextractor.ItemExtractorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;


/**
 * Simple {@link PipeNetworkEntity} used to connect {@link ItemExtractorEntity}s to {@link ItemDepositorEntity}s to form
 * a {@link PipeNetwork}. Items technically don't move through the pipes themselves, but the pipes act as a connection
 * allowing items to be teleported between other entities in the network.
 */
public class ItemPipeEntity extends PipeNetworkEntity {
    /**
     * Creates a new {@link ItemPipeEntity}
     *
     * @param pos The position to create the entity.
     * @param state The state associated with the entity.
     */
    public ItemPipeEntity(BlockPos pos, BlockState state) {
        super(Registration.PIPE_ENTITY.get(), pos, state);
    }
}
