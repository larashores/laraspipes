package com.larashores.laraspipes.itemdepositor;

import net.minecraftforge.items.ItemStackHandler;

public class ItemDepositorHandler extends ItemStackHandler {
    private final ItemDepositorEntity entity;

    public ItemDepositorHandler(ItemDepositorEntity entity, int slots) {
        super(slots);
        this.entity = entity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        entity.setChanged();
    }
}
