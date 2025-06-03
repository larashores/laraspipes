package com.larashores.laraspipes.fluidpipe;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.fluiddepositor.FluidDepositorEntity;
import com.larashores.laraspipes.fluidextractor.FluidExtractorEntity;
import com.larashores.laraspipes.network.PipeNetwork;
import com.larashores.laraspipes.network.PipeNetworkEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;


/**
 * Simple {@link PipeNetworkEntity} used to connect {@link FluidExtractorEntity}s to {@link FluidDepositorEntity}s to
 * form a {@link PipeNetwork}. Fluids technically don't move through the pipes themselves, but the pipes act as a
 * connection allowing fluids to be teleported between other entities in the network.
 */
public class FluidPipeEntity extends PipeNetworkEntity {
    /**
     * Creates a new {@link FluidPipeEntity}
     *
     * @param pos The position to create the entity.
     * @param state The state associated with the entity.
     */
    public FluidPipeEntity(BlockPos pos, BlockState state) {
        super(Registration.FLUID_PIPE_ENTITY.get(), pos, state);
    }
}
