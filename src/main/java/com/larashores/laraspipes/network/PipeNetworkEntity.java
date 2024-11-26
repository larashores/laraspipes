package com.larashores.laraspipes.network;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;


public abstract class PipeNetworkEntity extends BlockEntity {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private PipeNetwork network;

    public PipeNetworkEntity(BlockEntityType<?> block, BlockPos pos, BlockState state) {
        super(block, pos, state);
    }

    public PipeNetwork getNetwork() {
        if (network != null && !network.contains(this)) {
            network = null;
        }
        return network;
    }

    public void setNetwork(PipeNetwork network) {
        this.network = network;
    }

    public void clearNetwork() {
        this.network = null;
    }

    public PipeNetwork getOrCreateNetwork(Level level, BlockPos pos) {
        if (network == null) {
            PipeNetwork.discover(level, pos);
        }
        return getNetwork();
    }


    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null) {
            PipeNetwork.discover(level, worldPosition);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        var network = getNetwork();
        if (network != null) {
            network.clear();
        }
    }

    public boolean acceptsItems() {
        return false;
    }

    public void transferItems(Level level, Container from) {

    }

    public int compare(PipeNetworkEntity other, BlockPos pos) {
        // Consider closer entities before farther entities
        return (int) (pos.distSqr(worldPosition) - pos.distSqr(other.worldPosition));
    }

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
