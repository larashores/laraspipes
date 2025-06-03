package com.larashores.laraspipes.fluiddepositor;

import com.larashores.laraspipes.fluidpipe.FluidPipeBlock;
import com.larashores.laraspipes.network.PipeNetworkDirectedBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import org.slf4j.Logger;



/**
 * Block that can connect to accept fluids transferred through {@link FluidPipeBlock}s. See {@link FluidDepositorEntity}
 * for more details on its behavior.
 */
public class FluidDepositorBlock extends PipeNetworkDirectedBlock<FluidDepositorEntity> implements EntityBlock {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Creates the {@link FluidDepositorBlock}.
     */
    public FluidDepositorBlock() {
        super(
            Properties.of()
            .noOcclusion()
            .strength(1.5F)
            .sound(SoundType.METAL),
            FluidDepositorEntity::new,
            "fluid"
        );
    }
}
