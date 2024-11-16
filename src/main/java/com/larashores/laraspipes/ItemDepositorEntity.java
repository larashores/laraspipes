package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;


public class ItemDepositorEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String  FILTER_TAG = "filters";
    public final ItemStackHandler filters = new ItemDepositorItemStackHandler(this, 6 * 9);

    public ItemDepositorEntity(BlockPos pos, BlockState state) {
        super(Main.DEPOSITOR_ENTITY.get(), pos, state);
        LOGGER.info("ItemDepositorEntity({}, {})", pos, state);
    }

    public Set<Item> getFilters() {
        var items = new HashSet<Item>();
        for (var slot = 0; slot < filters.getSlots(); slot++) {
            var stack = filters.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                items.add(stack.getItem());
            }
        }
        return items;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(FILTER_TAG)) {
            filters.deserializeNBT(tag.getCompound(FILTER_TAG));
        }

    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(FILTER_TAG, filters.serializeNBT());
    }
}
