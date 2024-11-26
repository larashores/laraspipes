package com.larashores.laraspipes.itemdepositor;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.network.PipeNetworkEntity;
import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;


public class ItemDepositorEntity extends PipeNetworkEntity {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final String TAG_FILTERS = "filters";
    public final ItemStackHandler filters = new ItemDepositorHandler(this, 6 * 9);

    public ItemDepositorEntity(BlockPos pos, BlockState state) {
        super(Registration.DEPOSITOR_ENTITY.get(), pos, state);
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

    @Override
    public boolean acceptsItems() {
        return true;
    }

    @Override
    public void transferItems(Level level, Container from) {
        var to = Utils.getFacingChest(level, worldPosition);
        if (to != null) {
            Utils.transferItems(getFilters(), from, to);
        }
    }

    public int compare(PipeNetworkEntity other, BlockPos pos) {
        // Consider item depositors with filters before those without them.
        if (other instanceof ItemDepositorEntity entity) {
            var thisHasFilters = !getFilters().isEmpty();
            var otherHasFilters = !entity.getFilters().isEmpty();
            if (thisHasFilters && !otherHasFilters) {
                return -1;
            } else if (!thisHasFilters && otherHasFilters) {
                return 1;
            }
        }
        return super.compare(other, pos);
    }
}
