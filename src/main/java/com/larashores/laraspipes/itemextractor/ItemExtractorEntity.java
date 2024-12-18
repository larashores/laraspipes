package com.larashores.laraspipes.itemextractor;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.utils.ItemDepositorEntityIterable;
import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;


public class ItemExtractorEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemExtractorEntity(BlockPos pos, BlockState state) {
        super(Registration.EXTRACTOR_ENTITY.get(), pos, state);
        LOGGER.info("ItemExtractorEntity({}, {})", pos, state);
    }

    public void handleTick(Level level, BlockPos pos) {
        if (level.getGameTime() % 20 == 0) {
            var extractChest = Utils.getAdjacentChest(level, pos);
            if (extractChest != null) {
                for (var entity : new ItemDepositorEntityIterable(level, pos)) {
                    var depositPos = entity.getBlockPos();
                    var depositChest = Utils.getAdjacentChest(level, depositPos);
                    if (depositChest != null) {
                        Utils.transferItems(entity.getFilters(), extractChest, depositChest);
                    }
                }
            }
        }
    }
}
