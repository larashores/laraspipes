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

/**
 * Provider used to create {@link ItemDepositorMenu}s.
 */
public class ItemDepositorMenuProvider implements MenuProvider {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private final BlockPos pos;

    /**
     * Creates a new provider.
     *
     * @param pos The position that the created menu should be located at.
     */
    public ItemDepositorMenuProvider(BlockPos pos) {
        this.pos = pos;
    }

    /**
     * Display name of the menu that displays in the {@link ItemDepositorScreen}.
     *
     * @return The display name.
     */
    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.laraspipes.item_depositor");
    }

    /**
     * Creates the menu.
     *
     * @param windowId ??
     * @param inventory Inventory that the menu controls.
     * @param player Player using the menu.
     *
     * @return The newly created menu.
     */
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
        return new ItemDepositorMenu(windowId, player, pos);
    }
}
