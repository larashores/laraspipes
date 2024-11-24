package com.larashores.laraspipes.itempipe;

import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Map;

import static com.larashores.laraspipes.Registration.*;


public class ItemPipe extends Block {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Map<Direction, BooleanProperty> CONNECTED = Map.of(
        Direction.SOUTH, BooleanProperty.create("south"),
        Direction.EAST, BooleanProperty.create("east"),
        Direction.WEST, BooleanProperty.create("west"),
        Direction.NORTH, BooleanProperty.create("north"),
        Direction.UP, BooleanProperty.create("up"),
        Direction.DOWN, BooleanProperty.create("down")
    );

    public ItemPipe() {
        super(
            BlockBehaviour.Properties.of()
            .noOcclusion()
            .mapColor(MapColor.STONE)
        );
        LOGGER.info("ItemPipe()");
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        for (var property : CONNECTED.values()) {
            builder.add(property);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var state = defaultBlockState();
        state = setConnectionStates(context.getLevel(), context.getClickedPos(), state);
        return state;
    }

    @Override
    public void playerDestroy(Level p_49827_, Player p_49828_, BlockPos p_49829_, BlockState p_49830_, @Nullable BlockEntity p_49831_, ItemStack p_49832_) {
        super.playerDestroy(p_49827_, p_49828_, p_49829_, p_49830_, p_49831_, p_49832_);
    }

    public static BlockState setConnectionStates(Level level, BlockPos pos, BlockState state) {
        for (var direction: Direction.values()) {
            var property = CONNECTED.get(direction);
            var adjacentPos = pos.relative(direction);
            var adjacentState = level.getBlockState(adjacentPos);
            var connected = (
                adjacentState.is(PIPE.get())
                || (
                    adjacentState.is(DEPOSITOR.get())
                    && direction.getOpposite() != adjacentState.getValue(BlockStateProperties.FACING)
                ) || (
                    adjacentState.is(EXTRACTOR.get())
                    && direction.getOpposite() != adjacentState.getValue(BlockStateProperties.FACING)
                )
            );
            state = state.setValue(property, connected);
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
