package com.larashores.laraspipes.network;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * BlockEntity for a block that belongs to a {@link PipeNetwork}.
 */
public abstract class PipeNetworkEntity extends BlockEntity {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private PipeNetwork network;

    /**
     * Creates a new {@link PipeNetworkEntity}.
     *
     * @param block Block type associated with the entity.
     * @param pos Position to add the entity at.
     * @param state Block state of the block the entity is attached to.
     */
    public PipeNetworkEntity(BlockEntityType<?> block, BlockPos pos, BlockState state) {
        super(block, pos, state);
    }

    /**
     * Called when this entity is added to a world. Adds the entity to an adjacent {@link PipeNetwork} or creates a new
     * one.
     */
    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null) {
            PipeNetwork.discover(level, worldPosition);
        }
    }

    /**
     * Called when this entity is removed from a world. Removes the entity from the pipe network, if it exists.
     */
    @Override
    public void setRemoved() {
        super.setRemoved();
        var network = getNetwork();
        if (network != null) {
            network.clear();
        }
    }

    /**
     * Gets the {@link PipeNetwork} that this entity belongs to, if it exists. If it does not exist, returns null.
     *
     * @return The potentially null PipeNetwork that this entity belongs to.
     */
    public PipeNetwork getNetwork() {
        if (network != null && !network.contains(this)) {
            network = null;
        }
        return network;
    }

    /**
     * Sets the {@link PipeNetwork} that this entity is a part of.
     *
     * @param network The network to add the entity to.
     */
    public void setNetwork(PipeNetwork network) {
        this.network = network;
    }

    /**
     * Removes this entity from its {@link PipeNetwork}.
     */
    public void clearNetwork() {
        this.network = null;
    }

    /**
     * Gets the {@link PipeNetwork} that the entity belongs to if it exists. If it doesn't exist, creates a new network.
     *
     * @param level The level the position belongs to.
     * @param pos The position of the entity.
     *
     * @return The {@link PipeNetwork} that the entity belongs to.
     */
    public PipeNetwork getOrCreateNetwork(Level level, BlockPos pos) {
        if (network == null) {
            PipeNetwork.discover(level, pos);
        }
        return getNetwork();
    }

    /**
     * Meant to be defined by child classes. Determines if the entity can accept items from within a
     * {@link PipeNetwork}.
     *
     * @return Whether the entity can accept items.
     */
    public boolean acceptsItems() {
        return false;
    }

    /**
     * If the {@link #acceptsItems()} method of a child class True, this method should be implemented such that it
     * transfers items out of the specified container.
     *
     * @param level The level the container belongs to.
     * @param from The item handler to transfer items out of.
     */
    public void transferItems(Level level, IItemHandler from) {

    }

    /**
     * Used for sorting. Compares a {@link PipeNetworkEntity} to another {@link PipeNetworkEntity} relative to a
     * specified BlockPos. The entity closer to the specified block is considered the smallest.
     *
     * @param other The other {@link PipeNetworkEntity} to compare to.
     * @param pos The BlockPos to compare from
     *
     * @return -1 if the {@link PipeNetworkEntity} is less than the other {@link PipeNetworkEntity}, 1 if it is greater,
     *         otherwise 0 if they are equal.
     */
    public int compare(PipeNetworkEntity other, BlockPos pos) {
        // Consider closer entities before farther entities
        return (int) (pos.distSqr(worldPosition) - pos.distSqr(other.worldPosition));
    }

    /**
     * Returns all BlockPos's that neighbor a {@link PipeNetworkEntity}. Used for searching connected blocks when
     * creating or adding to  a {@link PipeNetwork}.
     *
     * @return The neighboring BlockPos's.
     */
    public List<BlockPos> getNeighbors() {
        var neighbors = new ArrayList<BlockPos>();
        if (level != null) {
            var state = level.getBlockState(worldPosition);
            for (var entry: PipeNetworkBlock.CONNECTED.entrySet()) {
                var direction = entry.getKey();
                var property = entry.getValue();
                var connected = state.getOptionalValue(property);
                if (connected.isPresent() && connected.get()) {
                    neighbors.add(worldPosition.relative(direction));
                }
            }
        }
        return neighbors;
    }
}
