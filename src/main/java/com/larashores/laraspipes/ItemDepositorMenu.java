package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;


public class ItemDepositorMenu extends AbstractContainerMenu {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final BlockPos pos;

    public ItemDepositorMenu(int windowId, Player player, BlockPos pos) {
        super(Main.DEPOSITOR_MENU.get(), windowId);
        LOGGER.info("ItemDepositorMenu({}, {}, {})", windowId, player, pos);
        this.pos = pos;
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
