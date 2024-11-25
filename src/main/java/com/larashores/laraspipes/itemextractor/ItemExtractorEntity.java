package com.larashores.laraspipes.itemextractor;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.itemdepositor.ItemDepositorEntity;
import com.larashores.laraspipes.network.PipeNetworkEntity;
import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;


public class ItemExtractorEntity extends PipeNetworkEntity {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemExtractorEntity(BlockPos pos, BlockState state) {
        super(Registration.EXTRACTOR_ENTITY.get(), pos, state);
        LOGGER.info("ItemExtractorEntity({}, {})", pos, state);
    }

    public void handleTick(Level level, BlockPos pos) {
        if (level.getGameTime() % 20 == 0) {
            var chest = Utils.getAdjacentChest(level, pos);
            if (chest != null && !chest.isEmpty()) {
                var network = getOrCreateNetwork(level, pos);
                LOGGER.info("Network: {}", network);
                for (var entity : network.itemAcceptors) {
                    LOGGER.info("    Entity: {}", entity);
                    entity.transferItems(level, chest);
                }
            }
        }
    }
}
