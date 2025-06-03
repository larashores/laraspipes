package com.larashores.laraspipes.network;

import com.larashores.laraspipes.utils.Utils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;


/**
 * Block that belongs to a {@link PipeNetwork} that has a FACING property.
 */
public class PipeNetworkDirectedBlock<T extends PipeNetworkEntity> extends PipeNetworkBlock<T> {
    /**
     * Creates a new {@link PipeNetworkDirectedBlock}.
     *
     * @param properties Properties to associate with the block.
     * @param provider Provider used for creating associated {@link PipeNetworkEntity}s.
     * @param type Type of block (e.g. item, fluid). Only {@link PipeNetworkBlock}s of the same type are connectable.
     */
    public PipeNetworkDirectedBlock(BlockBehaviour.Properties properties, PipeNetworkEntityProvider<T> provider, String type
    ) {
        super(properties, provider, type);
    }

    /**
     * Defines all block states associated with the block. Adds the CONNECTED and FACING block states.
     *
     * @param builder Builder used to register block states.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.FACING);
    }

    /**
     * Sets the initial state of the block as it is placed. Sets the FACING block state based on the following rules:
     * If the player is holding the shift key, the block will face the player's looking direction. Otherwise, if there
     * is a chest adjacent to the block being placed, the block will face the direction of the chest. Otherwise, if
     * there is an adjacent {@link PipeNetworkBlock}, the block will face it. Otherwise, the block will face the
     * player's looking direction. Additionally, sets the CONNECTED block states. See
     * {@link PipeNetworkBlock#getStateForPlacement} for details.
     *
     * @param context Details on how the block was placed.
     *
     * @return The initial block state.
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var player = context.getPlayer();
        var pos = context.getClickedPos();
        var facing = context.getNearestLookingDirection();
        if (player != null && !player.isShiftKeyDown()) {
            for (var direction : Utils.getDirections(facing)) {
                var adjPos = pos.relative(direction);
                var adjState = level.getBlockState(adjPos);
                var adjBlock = adjState.getBlock();
                var opposite = direction.getOpposite();
                if (
                        adjBlock instanceof PipeNetworkBlock<?> block
                        && block.canConnect(this, adjState, opposite)
                ) {
                    facing = opposite;
                    break;
                }
            }
            for (var direction : Utils.getDirections(facing)) {
                var adjPos = pos.relative(direction);
                var adjEntity = level.getBlockEntity(adjPos);
                if (
                    adjEntity != null
                    && adjEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction.getOpposite()).isPresent()
                ) {
                    facing = direction;
                }
            }
        }
        var state = super.getStateForPlacement(context);
        if (state != null) {
            state = state.setValue(BlockStateProperties.FACING, facing);
            state = state.setValue(CONNECTED.get(facing), false);
        }
        return state;
    }

    /**
     * Determines whether another {@link PipeNetworkBlock} can connect to this block along the specified direction. A
     * connection can always be made as long as the block is not facing that direction.
     *
     * @param state The state of the block.
     * @param direction The direction to connect along.
     *
     * @return Whether a connection can be made.
     */
    public boolean canConnect(PipeNetworkBlock<?> block, BlockState state, Direction direction) {
        if (super.canConnect(block, state, direction)) {
            var facing = state.getValue(BlockStateProperties.FACING);
            return facing != direction;
        }
        return false;
    }
}
