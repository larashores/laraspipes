package com.larashores.laraspipes.itemdepositor;

import com.larashores.laraspipes.Registration;
import com.larashores.laraspipes.itemextractor.ItemExtractorEntity;
import com.larashores.laraspipes.itempipe.ItemPipeEntity;
import com.larashores.laraspipes.network.PipeNetwork;
import com.larashores.laraspipes.network.PipeNetworkEntity;
import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;


/**
 * {@link PipeNetworkEntity} that can accept items from {@link ItemExtractorEntity}s moved "through"
 * {@link ItemPipeEntity}s connected to the same {@link PipeNetwork}. The entity has a set of associated "filters" that
 * the player chooses that designates which items can be transferred into it. If no filters are selected, all items can
 * be transferred.
 */
public class ItemDepositorEntity extends PipeNetworkEntity {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final String TAG_FILTERS = "filters";

    /** Handler used controlling entity's filters. */
    public final ItemStackHandler filters = new ItemDepositorHandler(this, 6 * 9);

    /**
     * Creates a new {@link ItemDepositorEntity}.
     *
     * @param pos The position to create the entity.
     * @param state The state associated with the entity.
     */
    public ItemDepositorEntity(BlockPos pos, BlockState state) {
        super(Registration.DEPOSITOR_ENTITY.get(), pos, state);
    }

    /**
     * Loads the filters from disk.
     *
     * @param tag Tag storing filter data.
     */
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_FILTERS)) {
            filters.deserializeNBT(tag.getCompound(TAG_FILTERS));
        }
    }

    /**
     * Saves filters to disk.
     *
     * @param tag Tag to add filters to.
     */
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(TAG_FILTERS, filters.serializeNBT());
    }

    /**
     * Specifies that the entity can accept items from other entities in a {@link PipeNetwork}.
     *
     * @return Always returns true.
     */
    @Override
    public boolean acceptsItems() {
        return true;
    }

    /**
     * Transfers items a source chest into the chest connected to the {@link ItemDepositorEntity}, if any.
     *
     * @param level The level the container belongs to.
     * @param from The item handler to transfer items out of.
     */
    @Override
    public void transferItems(Level level, IItemHandler from) {
        var to = Utils.getFacingCapability(ForgeCapabilities.ITEM_HANDLER, level, worldPosition);
        if (to != null) {
            Utils.transferItems(getFilters(), from, to);
        }
    }

    /**
     * Gets the filters that specify which items can be transferred through this entity.
     *
     * @return A set of allowed entities. If empty, any item can be transferred.
     */
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

    /**
     * Used for sorting this entity in a list of other {@link PipeNetworkEntity}s relative to a sort block. Items with
     * filters are always less than items without. This is so items are only transferred to {@link ItemDepositorEntity}s
     * without filters only if there is no entity with a matching filter that can accept it. Otherwise,
     * {@link ItemDepositorEntity}s are sorted from closet to farthest from the source block.
     *
     * @param other The other {@link PipeNetworkEntity} to compare to.
     * @param pos The BlockPos to compare from
     *
     * @return -1 if the entity is less than the other {@link PipeNetworkEntity}, 1 if it is greater, otherwise 0 if
     *         they are equal.
     */
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
