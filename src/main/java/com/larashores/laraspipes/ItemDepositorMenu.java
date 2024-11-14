package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.slf4j.Logger;


public class ItemDepositorMenu extends AbstractContainerMenu {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final BlockPos pos;

    public ItemDepositorMenu(int windowId, Player player, BlockPos pos) {
        super(Main.DEPOSITOR_MENU.get(), windowId);
        LOGGER.info("ItemDepositorMenu({}, {}, {})", windowId, player, pos);
        this.pos = pos;

        // Add items for filters
        if (player.level().getBlockEntity(pos) instanceof ItemDepositorEntity entity) {
            for (var row = 0; row < 6; row++) {
                for (var col = 0; col < 9; col++) {
                    var x = 8 + col * 18;
                    var y = 17 + row * 18;
                    addSlot(new SlotItemHandler(entity.filters, row * 9 + col, x, y));
                }
            }
        }

        // Add slots for player's hotbar
        for (var col = 0; col < 9; col++) {
            var x = 8 + col * 18;
            var y = 196;
            addSlot(new Slot(player.getInventory(), col, x, y));
        }

        // Add slots for player's inventory
        for (var row = 0; row < 3; row++) {
            for (var col = 0; col < 9; col++) {
                var x = 8 + col * 18;
                var y = 138  + row * 18;
                addSlot(new Slot(player.getInventory(), (row + 1) * 9 + col, x, y));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        LOGGER.info("quickMoveStack({}, {})", player, index);
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, Main.DEPOSITOR.get());
    }
}
