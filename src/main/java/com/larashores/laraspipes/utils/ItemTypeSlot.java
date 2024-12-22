package com.larashores.laraspipes.utils;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Optional;

/**
 * SlotItemHandler that is used to specify a type of item rather than store items. When a player tries to store an
 * ItemStack in the slot, a single copy of the item will be added to the ItemStack instead. When a player tries to
 * remove an item from the slot, the slot will be cleared instead of removed.
 */
public class ItemTypeSlot extends SlotItemHandler {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Creates the {@link ItemTypeSlot}.
     *
     * @param handler Handler to run whenever the contents of the slot have changed.
     * @param index Index of the slot within a group of slots.
     * @param xPosition x pixel position of the slot within a screen.
     * @param yPosition y pixel position of the slot within a screen.
     */
    public ItemTypeSlot(IItemHandler handler, int index, int xPosition, int yPosition) {
        super(handler, index, xPosition, yPosition);
    }

    /**
     * Triggered when a player clicks on the slot with an ItemStack. Sets the item type of the slot.
     *
     * @param stack ItemStack the player clicked on.
     * @param count Number of items the player wants to add to the slot (this will always be ignored).
     *
     * @return The same stack that was clicked with, signalling that no items were removed from it.
     */
    @Override
    @NotNull
    public ItemStack safeInsert(ItemStack stack, int count) {
        setByPlayer(stack.copyWithCount(1));
        return stack;
    }

    /**
     * Triggered when a player tries to remove an item from the slot. Clears the slot without removing any items.
     *
     * @param amount The amount of the item the player wants to remove.
     * @param min The minimum amount of item the player wants to remove.
     * @param player The player attempting to remove items.
     *
     * @return An empty item stack, signaling that nothing could be removed.
     */
    @Override
    @NotNull
    public Optional<ItemStack> tryRemove(int amount, int min, Player player) {
        setByPlayer(ItemStack.EMPTY);
        return Optional.empty();
    }
}
