package com.larashores.laraspipes.itempipe;

import com.larashores.laraspipes.network.PipeNetworkBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;


/**
 * Pipe block that can transfer items. It is meant to vaguely resemble lead plumbing pipes. See {@link ItemPipeEntity}
 * for more details on behavior.
 */
public class ItemPipeBlock extends PipeNetworkBlock<ItemPipeEntity> implements EntityBlock {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Creates a new {@link ItemPipeBlock}.
     */
    public ItemPipeBlock() {
        super(
            BlockBehaviour.Properties.of()
            .noOcclusion()
            .strength(0.5F)
            .mapColor(MapColor.STONE),
            ItemPipeEntity::new
        );
    }
}
