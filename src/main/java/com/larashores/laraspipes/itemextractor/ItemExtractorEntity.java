package com.larashores.laraspipes.itemextractor;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.network.PipeNetworkEntity;
import com.larashores.laraspipes.network.PipeNetwork;
import com.larashores.laraspipes.itemdepositor.ItemDepositorEntity;
import com.larashores.laraspipes.itempipe.ItemPipeEntity;
import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;


/**
 * {@link PipeNetworkEntity} that can move items "through" {@link ItemPipeEntity}s into {@link ItemDepositorEntity}s
 * connected to the same {@link PipeNetwork}.
 */
public class ItemExtractorEntity extends PipeNetworkEntity {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Creates a new {@link ItemExtractorEntity}.
     *
     * @param pos The position to create the entity.
     * @param state The state associated with the entity.
     */
    public ItemExtractorEntity(BlockPos pos, BlockState state) {
        super(Registration.EXTRACTOR_ENTITY.get(), pos, state);
    }

    /**
     * Checks if the {@link ItemExtractorEntity} is connected to a chest and tries to transfer items out of it into any
     * connected {@link ItemDepositorEntity}s if possible.
     *
     * @param level The level the entity belongs to.
     * @param pos The position of the entity.
     */
    public void handleTick(Level level, BlockPos pos) {
        if (level.getGameTime() % 20 == 0) {
            var chest = Utils.getFacingChest(level, pos);
            if (chest != null && !chest.isEmpty()) {
                var network = getOrCreateNetwork(level, pos);
                for (var entity : network.getItemAcceptors(pos)) {
                    entity.transferItems(level, chest);
                }
            }
        }
    }
}
