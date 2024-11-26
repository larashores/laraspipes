package com.larashores.laraspipes.network;

import com.larashores.laraspipes.utils.Utils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;


public class PipeNetworkDirectedBlock extends PipeNetworkBlock {
    public PipeNetworkDirectedBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.FACING);
    }

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
                var adjEntity = level.getBlockEntity(adjPos);
                if (adjEntity instanceof PipeNetworkEntity) {
                    facing = direction.getOpposite();
                    break;
                }
            }
            for (var direction : Utils.getDirections(facing)) {
                var adjPos = pos.relative(direction);
                var adjState = level.getBlockState(adjPos);
                if (adjState.is(Blocks.CHEST)) {
                    facing = direction;
                    break;
                }
            }
        }
        var state = super.getStateForPlacement(context);
        if (state != null) {
            state = state.setValue(BlockStateProperties.FACING, facing);
        }
        return state;
    }
}
