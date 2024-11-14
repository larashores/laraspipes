package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.slf4j.Logger;


public class ItemDepositorEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    public final ItemStackHandler filters = new ItemDepositorItemStackHandler(this, 6 * 9);

    public ItemDepositorEntity(BlockPos pos, BlockState state) {
        super(Main.DEPOSITOR_ENTITY.get(), pos, state);
        LOGGER.info("ItemDepositorEntity({}, {})", pos, state);
    }
}
