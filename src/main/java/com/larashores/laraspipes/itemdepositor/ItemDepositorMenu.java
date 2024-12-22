package com.larashores.laraspipes.itemdepositor;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.utils.ItemTypeSlot;
import com.larashores.laraspipes.utils.ScreenGrid;
import com.larashores.laraspipes.utils.ScreenPos;
import com.larashores.laraspipes.utils.ScreenSize;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import javax.annotation.Nonnull;


/**
 * Menu for configuring the filters on a {@link ItemDepositorEntity}.
 */
public class ItemDepositorMenu extends AbstractContainerMenu {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ScreenSize SLOT_SIZE = new ScreenSize(18, 18);
    private static final ScreenGrid FILTER_GRID = new ScreenGrid(6, 9);
    private static final ScreenPos FILTER_POS = new ScreenPos(8, 17);
    private static final ScreenGrid INVENTORY_GRID = new ScreenGrid(3, 9);
    private static final ScreenPos INVENTORY_POS = new ScreenPos(8, 138);
    private static final ScreenGrid HOTBAR_GRID = new ScreenGrid(1, 9);
    private static final ScreenPos HOTBAR_POS = new ScreenPos(8, 196);
    private final BlockPos pos;

    /**
     * Creates a new {@link ItemDepositorMenu}.
     *
     * @param windowId ??
     * @param player The player using the menu.
     * @param pos The position to create the menu at.
     */
    public ItemDepositorMenu(int windowId, Player player, BlockPos pos) {
        super(Registration.DEPOSITOR_MENU.get(), windowId);
        this.pos = pos;
        addSlotsFilters(player);
        addSlotsInventory(player);
        addSlotsHotbar(player);
    }

    /**
     * Called when a user shift-clicks a slot in the menu. If the user shift-clicks a slot in the player's inventory,
     * a filter for the item will be added to the menu (unless one already exists). If the use shift-clicks a filter
     * slot, the filter will be removed from the slot.
     *
     * @param player The player shift clicking
     * @param index The index of the slot in the menu.
     *
     * @return A null stack, indicating that no items have moved.
     */
    @Nonnull
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var slot = getSlot(index);
        if (index < FILTER_GRID.rows() * FILTER_GRID.cols()) {
            // Remove filter from stack.
            var stack = slot.getItem();
            slot.tryRemove(stack.getMaxStackSize(), Integer.MAX_VALUE, player);
        } else if (slot.hasItem()){
            // Add filter for selected item from player's inventory.
            var item = slot.getItem();
            if (
                player.level().getBlockEntity(pos) instanceof ItemDepositorEntity entity
                && !entity.getFilters().contains(item.getItem())
            ) {
                outerLoop: for (var row = 0; row < FILTER_GRID.rows(); row++) {
                    for (var col = 0; col < FILTER_GRID.cols(); col++) {
                        var filter = getSlot(row * FILTER_GRID.cols() + col);
                        if (!filter.hasItem()) {
                            filter.safeInsert(item, 1);
                            break outerLoop;
                        }
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * Whether the player is close enough that the menu should stay open.
     *
     * @param player The player using the menu.
     *
     * @return False if the menu should be closed. Otherwise, true.
     */
    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, Registration.DEPOSITOR_BLOCK.get());
    }

    /**
     * Adds slots that represent the filters of the {@link ItemDepositorEntity}.
     *
     * @param player The player using the menu.
     */
    private void addSlotsFilters(Player player) {
        if (player.level().getBlockEntity(pos) instanceof ItemDepositorEntity entity) {
            for (var row = 0; row < FILTER_GRID.rows(); row++) {
                for (var col = 0; col < FILTER_GRID.cols(); col++) {
                    var index = row * FILTER_GRID.cols() + col;
                    var x = FILTER_POS.x() + col * SLOT_SIZE.width();
                    var y = FILTER_POS.y() + row * SLOT_SIZE.height();
                    addSlot(new ItemTypeSlot(entity.filters, index, x, y));
                }
            }
        }
    }

    /**
     * Add slots that represent the non-hotbar items in the player's inventory.
     *
     * @param player The player using the menu.
     */
    private void addSlotsInventory(Player player) {
        for (var row = 0; row < INVENTORY_GRID.rows(); row++) {
            for (var col = 0; col < INVENTORY_GRID.cols(); col++) {
                var x = INVENTORY_POS.x() + col * SLOT_SIZE.width();
                var y = INVENTORY_POS.y()  + row * SLOT_SIZE.height();
                addSlot(new Slot(player.getInventory(), (row + 1) * 9 + col, x, y));
            }
        }
    }

    /**
     * Add slots that represent the hotbar items in the player's inventory.
     *
     * @param player The player using the menu.
     */
    private void addSlotsHotbar(Player player) {
        for (var col = 0; col < HOTBAR_GRID.cols(); col++) {
            var x = HOTBAR_POS.x() + col * SLOT_SIZE.width();
            var y = HOTBAR_POS.y();
            addSlot(new Slot(player.getInventory(), col, x, y));
        }
    }
}
