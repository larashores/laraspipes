package com.larashores.laraspipes.utils;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Optional;

public class ItemTypeSlot extends SlotItemHandler {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemTypeSlot(IItemHandler handler, int index, int xPosition, int yPosition) {
        super(handler, index, xPosition, yPosition);
    }

    @Override
    @NotNull
    public ItemStack safeInsert(ItemStack stack, int count) {
        setByPlayer(stack.copyWithCount(1));
        return stack;
    }

    @Override
    @NotNull
    public Optional<ItemStack> tryRemove(int amount, int min, Player player) {
        setByPlayer(ItemStack.EMPTY);
        return Optional.empty();
    }
}
