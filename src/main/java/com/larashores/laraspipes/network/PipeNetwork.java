package com.larashores.laraspipes.network;

import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;

public class PipeNetwork {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public HashSet<PipeNetworkEntity> entities = new HashSet<>();
    public HashSet<PipeNetworkEntity> itemAcceptors = new HashSet<>();

    public boolean contains(PipeNetworkEntity entity) {
        return entities.contains(entity);
    }

    public void add(PipeNetworkEntity entity) {
        entities.add(entity);
        if (entity.acceptsItems()) {
            itemAcceptors.add(entity);
        }
        entity.setNetwork(this);
    }

    public void merge(PipeNetwork network, HashSet<BlockPos> seen) {
        entities.addAll(network.entities);
        itemAcceptors.addAll(network.itemAcceptors);
        for (var entity: network.entities) {
            entity.setNetwork(this);
            seen.add(entity.getBlockPos());
        }
    }

    public void clear() {
        for (var entity: entities) {
            entity.clearNetwork();
        }
        entities.clear();
        itemAcceptors.clear();
    }

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
                if (otherNetwork != null) {
                    otherNetwork.merge(network, seen);
                    network = otherNetwork;
                }
                network.add(networkEntity);
                queue.addAll(Utils.getAdjacentBlockPositions(pos));
            }
        }
    }
}
