package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Optional;

public class ItemDepositorSlot extends SlotItemHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemDepositorSlot(IItemHandler handler, int index, int xPosition, int yPosition) {
        super(handler, index, xPosition, yPosition);
    }

    @Override
    @NotNull
    public ItemStack safeInsert(ItemStack stack, int count) {
        LOGGER.info("safeInsert({}, {})", stack, count);
        setByPlayer(stack.copyWithCount(1));
        return stack;
    }

    @Override
    @NotNull
    public Optional<ItemStack> tryRemove(int amount, int min, Player player) {
        LOGGER.info("tryRemove({}, {}, {})", amount, min, player);
        setByPlayer(ItemStack.EMPTY);
        return Optional.empty();
    }
}
