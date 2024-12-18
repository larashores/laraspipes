package com.larashores.laraspipes.itemdepositor;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

public class ItemDepositorMenuProvider implements MenuProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final BlockPos pos;

    public ItemDepositorMenuProvider(BlockPos pos) {
        LOGGER.info("ItemDepositorMenuProvider({})", pos);
        this.pos = pos;
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("Item Depositor");
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        LOGGER.info("createMenu({}, {}, {})", windowId, inventory, player);
        return new ItemDepositorMenu(windowId, player, pos);
    }
}
