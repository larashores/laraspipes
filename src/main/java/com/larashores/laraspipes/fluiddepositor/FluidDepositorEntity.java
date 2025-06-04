package com.larashores.laraspipes.fluiddepositor;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.fluidextractor.FluidExtractorEntity;
import com.larashores.laraspipes.fluidpipe.FluidPipeEntity;
import com.larashores.laraspipes.network.PipeNetwork;
import com.larashores.laraspipes.network.PipeNetworkEntity;
import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.slf4j.Logger;

/**
 * {@link PipeNetworkEntity} that can accept fluids from {@link FluidExtractorEntity}s moved "through"
 * {@link FluidPipeEntity}s connected to the same {@link PipeNetwork}.
 */
public class FluidDepositorEntity extends PipeNetworkEntity {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Creates a new {@link FluidDepositorEntity}.
     *
     * @param pos The position to create the entity.
     * @param state The state associated with the entity.
     */
    public FluidDepositorEntity(BlockPos pos, BlockState state) {
        super(Registration.FLUID_DEPOSITOR_ENTITY.get(), pos, state);
    }

    /**
     * Specifies that the entity can accept fluids from other entities in a {@link PipeNetwork}.
     *
     * @return Always returns true.
     */
    public boolean acceptsFluids() {
        return true;
    }

    /**
     * Transfers items from a source {@link IFluidHandler} into the {@link IFluidHandler} the {@link FluidDepositorEntity}
     * is facing, if any.
     *
     * @param level The level the container belongs to.
     * @param from The item handler to transfer items out of.
     */
    public void transferFluid(Level level, IFluidHandler from) {
        var to = Utils.getFacingCapability(ForgeCapabilities.FLUID_HANDLER, level, worldPosition);
        if (to != null) {
            var tanks = from.getTanks();
            for (var tank = 0; tank < tanks; tank++) {
                var stack = from.getFluidInTank(tank);
                var fluid = stack.getFluid();
                var draining = new FluidStack(fluid, FluidType.BUCKET_VOLUME);
                var drained = from.drain(draining, IFluidHandler.FluidAction.SIMULATE);
                if (!drained.isEmpty()) {
                    var amount = to.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                    var filled = new FluidStack(stack.getFluid(), amount);
                    from.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                }
            }
        }
    }
}
