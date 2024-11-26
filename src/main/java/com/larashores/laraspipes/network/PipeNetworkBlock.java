package com.larashores.laraspipes.network;

import com.larashores.laraspipes.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


public class PipeNetworkBlock extends Block {
    public static final Map<Direction, BooleanProperty> CONNECTED = Map.of(
        Direction.SOUTH, BooleanProperty.create("south"),
        Direction.EAST, BooleanProperty.create("east"),
        Direction.WEST, BooleanProperty.create("west"),
        Direction.NORTH, BooleanProperty.create("north"),
        Direction.UP, BooleanProperty.create("up"),
        Direction.DOWN, BooleanProperty.create("down")
    );

    public PipeNetworkBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        for (var property : CONNECTED.values()) {
            builder.add(property);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var state = super.getStateForPlacement(context);
        if (state != null) {
            state = setConnectionStates(context.getLevel(), context.getClickedPos(), state);
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState old, boolean isMoving) {
        super.onPlace(state, level, pos, old, isMoving);
        Utils.setAdjacentBlockStates(level, pos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState old, boolean isMoving) {
        super.onRemove(state, level, pos, old, isMoving);
        Utils.setAdjacentBlockStates(level, pos);
    }
}
