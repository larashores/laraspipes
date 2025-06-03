package com.larashores.laraspipes.network;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;


/**
 * Block that belongs to a {@link PipeNetwork}.
 */
public class PipeNetworkBlock<T extends PipeNetworkEntity> extends Block implements EntityBlock {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();
    private final String type;

    private final PipeNetworkEntityProvider<T> provider;

    /**
     * Block states that determine if the {@link PipeNetworkBlock} is connected to another {@link PipeNetworkBlock}
     * along a given direction.
     */
    public static final Map<Direction, BooleanProperty> CONNECTED = Map.of(
        Direction.SOUTH, BooleanProperty.create("south"),
        Direction.EAST, BooleanProperty.create("east"),
        Direction.WEST, BooleanProperty.create("west"),
        Direction.NORTH, BooleanProperty.create("north"),
        Direction.UP, BooleanProperty.create("up"),
        Direction.DOWN, BooleanProperty.create("down")
    );

    /**
     * Creates a new {@link PipeNetworkBlock}.
     *
     * @param properties Properties to associate with the block.
     * @param provider Provider used for creating associated {@link PipeNetworkEntity}s.
     * @param type Type of block (e.g. item, fluid). Only {@link PipeNetworkBlock}s of the same type are connectable.
     */
    public PipeNetworkBlock(BlockBehaviour.Properties properties, PipeNetworkEntityProvider<T> provider, String type) {
        super(properties);
        this.provider = provider;
        this.type = type;
    }

    /**
     * Creates a new {@link PipeNetworkEntity} to associate with the block.
     *
     * @param pos The position to create the entity at.
     * @param state The state of the block associated with the entity.
     *
     * @return The newly created entity.
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return provider.create(pos, state);
    }

    /**
     * Defines all block states associated with the block. Adds the CONNECTED block states.
     *
     * @param builder Builder used to register block states.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        for (var property : CONNECTED.values()) {
            builder.add(property);
        }
    }

    /**
     * Sets the initial state of the block as it is placed. Sets the CONNECTED states along any directions in which
     * there is another {@link PipeNetworkBlock} to connect to.
     *
     * @param context Details on how the block was placed.
     *
     * @return The initial block state.
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var state = super.getStateForPlacement(context);
        if (state != null) {
            state = setConnectionStates(context.getLevel(), context.getClickedPos(), state);
        }
        return state;
    }

    /**
     * Called whenever a {@link PipeNetworkBlock} is added or its state is updated. If the block is being added to a
     * position for the first time (rather than just having its state updated), the states of adjacent
     * {@link PipeNetworkBlock}s will be updated to set the correct connections.
     *
     * @param state The block state of the block being added.
     * @param level The level the block belongs to.
     * @param pos The position of the block.
     * @param oldState State of the block at the specified pos before the current block was placed.
     * @param isMoving Whether the block is being moved from one position to another (e.g. by a piston).
     */
    @Override
    @SuppressWarnings("deprecation")
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (oldState.getBlock() instanceof PipeNetworkBlock<?>) {
            // Don't update adjacent states if the block is being updated rather than being placed for the first time.
            return;
        }
        for (var direction : Direction.values()) {
            var adjacentPos = pos.relative(direction);
            var adjacentState = level.getBlockState(adjacentPos);
            var adjacentBlock = adjacentState.getBlock();
            if (adjacentBlock instanceof PipeNetworkBlock<?>) {
                var connected = state.getValue(CONNECTED.get(direction));
                var newAdjacentState = adjacentState.setValue(CONNECTED.get(direction.getOpposite()), connected);
                if (newAdjacentState != adjacentState) {
                    level.setBlock(adjacentPos, newAdjacentState, Block.UPDATE_ALL);
                }
            }
        }
    }

    /**
     * Called whenever a {@link PipeNetworkBlock} is removed.  If the block is being fully removed from a position
     * (rather than just having its state updated), the states of adjacent {@link PipeNetworkBlock}s will be updated to
     * unset the correct connections.
     *
     * @param state The block state of the block being removed.
     * @param level The level the block belongs to.
     * @param pos The position of the block.
     * @param newState State that will replace the block at the specified position after this block is removed.
     * @param isMoving Whether the block is being moved from one position to another (e.g. by a piston).
     */
    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);
        if (newState.getBlock() instanceof PipeNetworkBlock<?>) {
            // Don't update adjacent states if the block is being updated rather than fully removed.
            return;
        }
        for (var direction : Direction.values()) {
            var adjacentPos = pos.relative(direction);
            var adjacentState = level.getBlockState(adjacentPos);
            var adjacentBlock = adjacentState.getBlock();
            if (adjacentBlock instanceof PipeNetworkBlock<?>) {
                var newAdjacentState = adjacentState.setValue(CONNECTED.get(direction.getOpposite()),false);
                if (newAdjacentState != adjacentState) {
                    level.setBlock(adjacentPos, newAdjacentState, Block.UPDATE_ALL);
                }
            }
        }
    }

    /**
     * Sets the CONNECTED block state of a corresponding to each Direction.
     *
     * @param level The level the block belongs to.
     * @param pos The position of the block to set the states for.
     * @param state The current state of the block.
     *
     * @return The new state of the block.
     */
    public BlockState setConnectionStates(Level level, BlockPos pos, BlockState state) {
        for (var direction: Direction.values()) {
            var property = CONNECTED.get(direction);
            var adjacentPos = pos.relative(direction);
            var adjacentState = level.getBlockState(adjacentPos);
            var adjacentBlock = adjacentState.getBlock();
            var connected = (
                adjacentBlock instanceof PipeNetworkBlock<?> block
                && block.canConnect(this, adjacentState, direction.getOpposite())
            );
            state = state.setValue(property, connected);
        }
        return state;
    }

    /**
     * Determines whether another {@link PipeNetworkBlock} can connect to this block along the specified direction.
     *
     * @param block Block to test if this block can conect to.
     * @param state The state of the block.
     * @param direction The direction to connect along.
     *
     * @return Whether a connection can be made.
     */
    public boolean canConnect(PipeNetworkBlock<?> block, BlockState state, Direction direction) {
        return block.type.equals(type);
    }
}
