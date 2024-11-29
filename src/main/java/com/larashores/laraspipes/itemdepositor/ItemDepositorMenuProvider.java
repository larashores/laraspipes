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
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private final BlockPos pos;

    public ItemDepositorMenuProvider(BlockPos pos) {
        this.pos = pos;
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.laraspipes.item_depositor");
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new ItemDepositorMenu(windowId, player, pos);
    }
}
