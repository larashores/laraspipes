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


public class ItemDepositorMenu extends AbstractContainerMenu {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ScreenSize SLOT_SIZE = new ScreenSize(18, 18);
    private static final ScreenGrid FILTER_GRID = new ScreenGrid(6, 9);
    private static final ScreenPos FILTER_POS = new ScreenPos(8, 17);
    private static final ScreenGrid INVENTORY_GRID = new ScreenGrid(3, 9);
    private static final ScreenPos INVENTORY_POS = new ScreenPos(8, 138);
    private static final ScreenGrid HOTBAR_GRID = new ScreenGrid(1, 9);
    private static final ScreenPos HOTBAR_POS = new ScreenPos(8, 196);
    private final BlockPos pos;

    public ItemDepositorMenu(int windowId, Player player, BlockPos pos) {
        super(Registration.DEPOSITOR_MENU.get(), windowId);
        LOGGER.info("ItemDepositorMenu({}, {}, {})", windowId, player, pos);
        this.pos = pos;
        addSlotsFilters(player);
        addSlotsInventory(player);
        addSlotsHotbar(player);
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        LOGGER.info("quickMoveStack({}, {})", player, index);
        var slot = getSlot(index);
        if (index < FILTER_GRID.rows() * FILTER_GRID.cols()) {
            var stack = slot.getItem();
            slot.tryRemove(stack.getMaxStackSize(), Integer.MAX_VALUE, player);
        } else if (slot.hasItem()){
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

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, Registration.DEPOSITOR.get());
    }

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

    private void addSlotsInventory(Player player) {
        for (var row = 0; row < INVENTORY_GRID.rows(); row++) {
            for (var col = 0; col < INVENTORY_GRID.cols(); col++) {
                var x = INVENTORY_POS.x() + col * SLOT_SIZE.width();
                var y = INVENTORY_POS.y()  + row * SLOT_SIZE.height();
                addSlot(new Slot(player.getInventory(), (row + 1) * 9 + col, x, y));
            }
        }
    }

    private void addSlotsHotbar(Player player) {
        for (var col = 0; col < HOTBAR_GRID.cols(); col++) {
            var x = HOTBAR_POS.x() + col * SLOT_SIZE.width();
            var y = HOTBAR_POS.y();
            addSlot(new Slot(player.getInventory(), col, x, y));
        }
    }
}
