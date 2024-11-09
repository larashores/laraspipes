package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;


public class ItemExtractorEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemExtractorEntity(BlockPos pos, BlockState state) {
        super(Main.EXTRACTOR_ENTITY.get(), pos, state);
        LOGGER.info("ItemExtractorEntity({}, {})", pos, state);
    }

    public void handleTick(Level level, BlockPos pos, BlockState state) {
        if (level.getGameTime() % 100 == 0) {
            LOGGER.info("handleTick({}, {}, {})", level, pos, state);
            var nextToChest = Utils.isChestAdjacent(level, pos);
            LOGGER.info("nextToChest == {}", nextToChest);
        }
    }

}
