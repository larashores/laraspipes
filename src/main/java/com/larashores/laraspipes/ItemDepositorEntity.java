package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;


public class ItemDepositorEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemDepositorEntity(BlockPos pos, BlockState state) {
        super(Main.DEPOSITOR_ENTITY.get(), pos, state);
        LOGGER.info("ItemDepositorEntity({}, {})", pos, state);
    }

}
