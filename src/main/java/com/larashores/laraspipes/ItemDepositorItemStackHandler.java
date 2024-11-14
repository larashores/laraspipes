package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.items.ItemStackHandler;
import org.slf4j.Logger;

public class ItemDepositorItemStackHandler extends ItemStackHandler {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final ItemDepositorEntity entity;

    public ItemDepositorItemStackHandler(ItemDepositorEntity entity, int slots) {
        super(slots);
        this.entity = entity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        entity.setChanged();
        LOGGER.info("onContentsChanged({})", slot);
    }
}
