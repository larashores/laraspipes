package com.larashores.laraspipes.fluidextractor;

import com.larashores.laraspipes.itemdepositor.ItemDepositorEntity;
import com.larashores.laraspipes.itempipe.ItemPipeBlock;
import com.larashores.laraspipes.network.PipeNetworkDirectedBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;


/**
 * Block that can connect to accept items transferred through {@link ItemPipeBlock}s. See {@link ItemDepositorEntity}
 * for more details on its behavior.
 */
public class FluidExtractorBlock extends PipeNetworkDirectedBlock<FluidExtractorEntity> {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Creates the {@link FluidExtractorBlock}.
     */
    public FluidExtractorBlock() {
        super(
            Properties.of()
            .noOcclusion()
            .strength(1.5F)
            .sound(SoundType.METAL),
            FluidExtractorEntity::new,
            "fluid"
        );
    }

    /**
     * Returns a ticker that initiates fluid transfers if possible.
     *
     * @param level The level of the block.
     * @param state The state of the block.
     * @param type The entity associated with the block.
     *
     * @return The ticker, if called on the server side, otherwise null.
     *
     * @param <T> The type of entity associated with the block.
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> type
    ) {
        if (level.isClientSide) {
            return null;
        } else {
            return (lvl, pos, st, blockEntity) -> {
                if (blockEntity instanceof FluidExtractorEntity be) {
                    be.handleTick(lvl, pos);
                }
            };
        }
    }
}
