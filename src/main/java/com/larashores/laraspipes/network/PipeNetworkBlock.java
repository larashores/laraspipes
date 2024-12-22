package com.larashores.laraspipes.network;

import com.larashores.laraspipes.utils.Utils;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


/**
 * Block that belongs to a {@link PipeNetwork}.
 */
public class PipeNetworkBlock<T extends PipeNetworkEntity> extends Block implements EntityBlock {
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
     */
    public PipeNetworkBlock(BlockBehaviour.Properties properties, PipeNetworkEntityProvider<T> provider) {
        super(properties);
        this.provider = provider;
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
     * Called whenever a {@link PipeNetworkBlock} is added. Sets the block states of all adjacent
     * {@link PipeNetworkBlock}s.
     *
     * @param state The block state of the block being added.
     * @param level The level the block belongs to.
     * @param pos The position of the block.
     * @param old ???
     * @param isMoving Whether the block is being moved from one position to another (e.g. by a piston).
     */
    @Override
    @SuppressWarnings("deprecation")
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState old, boolean isMoving) {
        super.onPlace(state, level, pos, old, isMoving);
        Utils.setAdjacentBlockConnections(level, pos);
    }

    /**
     * Called whenever a {@link PipeNetworkBlock} is removed. Sets the block states of all adjacent
     * {@link PipeNetworkBlock}s.
     *
     * @param state The block state of the block being removed.
     * @param level The level the block belongs to.
     * @param pos The position of the block.
     * @param old ???
     * @param isMoving Whether the block is being moved from one position to another (e.g. by a piston).
     */
    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState old, boolean isMoving) {
        super.onRemove(state, level, pos, old, isMoving);
        Utils.setAdjacentBlockConnections(level, pos);
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
        var facing = state.getOptionalValue(BlockStateProperties.FACING);
        for (var direction: Direction.values()) {
            var property = CONNECTED.get(direction);
            var adjacentPos = pos.relative(direction);
            var adjacentState = level.getBlockState(adjacentPos);
            var adjacentEntity = level.getBlockEntity(adjacentPos);
            var adjacentFacing = adjacentState.getOptionalValue(BlockStateProperties.FACING);
            var connected = (
                (facing.isEmpty() || direction != facing.get())
                    && (adjacentFacing.isEmpty() || direction.getOpposite() != adjacentFacing.get())
                    && adjacentEntity instanceof PipeNetworkEntity
            );
            state = state.setValue(property, connected);
        }
        return state;
    }
}
