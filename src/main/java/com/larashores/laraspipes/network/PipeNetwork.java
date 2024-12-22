package com.larashores.laraspipes.network;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Used to group a set of {@link PipeNetworkEntity}s that are connected. Entities within the network can move items to
 * any other item accepting entity within the network.
 */
public class PipeNetwork {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private final HashSet<PipeNetworkEntity> entities = new HashSet<>();
    private final HashSet<PipeNetworkEntity> itemAcceptors = new HashSet<>();

    /**
     * Whether a {@link PipeNetworkEntity} is part of this network.
     * @param entity The entity to check.
     *
     * @return Whether the entity is part of the network.
     */
    public boolean contains(PipeNetworkEntity entity) {
        return entities.contains(entity);
    }

    /**
     * Adds an entity to the network.
     *
     * @param entity The entity to add.
     */
    public void add(PipeNetworkEntity entity) {
        entities.add(entity);
        if (entity.acceptsItems()) {
            itemAcceptors.add(entity);
        }
        entity.setNetwork(this);
    }

    /**
     * Adds all entities from another {@link PipeNetwork} into this network.
     *
     * @param network Network to merge into existing network.
     * @param seen List of blocks that this network has already searched. The blocks of the network being merged will be
     *             added to the set of already searched blocks.
     */
    public void merge(PipeNetwork network, HashSet<BlockPos> seen) {
        entities.addAll(network.entities);
        itemAcceptors.addAll(network.itemAcceptors);
        for (var entity: network.entities) {
            entity.setNetwork(this);
            seen.add(entity.getBlockPos());
        }
    }

    /**
     * Removes all entities from the network.
     */
    public void clear() {
        for (var entity: entities) {
            entity.clearNetwork();
        }
        entities.clear();
        itemAcceptors.clear();
    }

    /**
     * Given a start position, creates a new {@link PipeNetwork}. Searches the start position for a
     * {@link PipeNetworkEntity}. If the entity has an existing {@link PipeNetwork}, the new network is merged into the
     * existing network. Otherwise, adds the entity to the newly created network. Then, adjacent blocks are searched for
     * additional {@link PipeNetworkEntity}s and the process is continued, adding each discovered entity to a
     * continuous network
     *
     * @param level The level the start position belongs to.
     * @param start The start position to search from.
     */
    public static void discover(Level level, BlockPos start) {
        var network = new PipeNetwork();
        var queue = new ArrayList<BlockPos>();
        var seen = new HashSet<BlockPos>();
        queue.add(start);
        while (!queue.isEmpty()) {
            var pos = queue.remove(0);
            if (seen.contains(pos)) {
                continue;
            }
            seen.add(pos);

            var entity = level.getBlockEntity(pos);
            if (entity instanceof PipeNetworkEntity networkEntity) {
                var otherNetwork = networkEntity.getNetwork();
                if (otherNetwork != null && otherNetwork != network) {
                    otherNetwork.merge(network, seen);
                    network = otherNetwork;
                }
                network.add(networkEntity);
                queue.addAll(networkEntity.getNeighbors());
            }
        }
    }

    /**
     * Returns a sorted list of all {@link PipeNetworkEntity}s that can accept items. Entities that are closer to a
     * block position are returned first in the list.
     *
     * @param pos The position used to sort the returned entities.
     *
     * @return List of PipeNetworkEntities that can accept items.
     */
    public ArrayList<PipeNetworkEntity> getItemAcceptors(BlockPos pos) {
        var acceptors = new ArrayList<>(itemAcceptors);
        acceptors.sort((e1, e2) -> e1.compare(e2, pos));
        return acceptors;
    }
}
