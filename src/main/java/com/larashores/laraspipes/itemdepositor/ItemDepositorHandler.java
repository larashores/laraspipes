package com.larashores.laraspipes.itemdepositor;

import net.minecraftforge.items.ItemStackHandler;

/**
 * Handler to act as a callback whenever an {@link ItemDepositorMenu}'s slot is modified.
 */
public class ItemDepositorHandler extends ItemStackHandler {
    private final ItemDepositorEntity entity;

    /**
     * Creates a new handler.
     *
     * @param entity The entity that the slot the handler controls belongs to.
     * @param slots The number of slots the handler should control.
     */
    public ItemDepositorHandler(ItemDepositorEntity entity, int slots) {
        super(slots);
        this.entity = entity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        entity.setChanged();
    }
}
