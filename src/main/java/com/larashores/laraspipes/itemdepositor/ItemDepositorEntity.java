package com.larashores.laraspipes.itemdepositor;

import com.larashores.laraspipes.Registration;
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
    private static final String TAG_FILTERS = "filters";
    public final ItemStackHandler filters = new ItemDepositorHandler(this, 6 * 9);

    public ItemDepositorEntity(BlockPos pos, BlockState state) {
        super(Registration.DEPOSITOR_ENTITY.get(), pos, state);
        LOGGER.info("ItemDepositorEntity({}, {})", pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_FILTERS)) {
            filters.deserializeNBT(tag.getCompound(TAG_FILTERS));
        }

    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(TAG_FILTERS, filters.serializeNBT());
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
}
