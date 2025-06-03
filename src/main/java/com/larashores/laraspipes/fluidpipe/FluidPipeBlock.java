package com.larashores.laraspipes.fluidpipe;

import com.larashores.laraspipes.network.PipeNetworkBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;


/**
 * Pipe block that can transfer items. It is meant to vaguely resemble lead plumbing pipes. See {@link FluidPipeEntity}
 * for more details on behavior.
 */
public class FluidPipeBlock extends PipeNetworkBlock<FluidPipeEntity> implements EntityBlock {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Creates a new {@link FluidPipeBlock}.
     */
    public FluidPipeBlock() {
        super(
            Properties.of()
            .noOcclusion()
            .strength(0.5F)
            .mapColor(MapColor.STONE),
            FluidPipeEntity::new,
            "fluid"
        );
    }
}
