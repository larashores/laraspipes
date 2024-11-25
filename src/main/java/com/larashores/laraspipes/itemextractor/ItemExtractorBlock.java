package com.larashores.laraspipes.itemextractor;

import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;

import static com.larashores.laraspipes.Registration.*;

public class ItemExtractorBlock extends Block implements EntityBlock {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Map<Direction, BooleanProperty> CONNECTED = Map.of(
        Direction.SOUTH, BooleanProperty.create("south"),
        Direction.EAST, BooleanProperty.create("east"),
        Direction.WEST, BooleanProperty.create("west"),
        Direction.NORTH, BooleanProperty.create("north"),
        Direction.UP, BooleanProperty.create("up"),
        Direction.DOWN, BooleanProperty.create("down")
    );

    public ItemExtractorBlock() {
        super(
            BlockBehaviour.Properties.of()
            .noOcclusion()
            .strength(3.5F)
            .sound(SoundType.METAL)
        );
        LOGGER.info("ItemExtractor()");
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        LOGGER.info("newBlockEntity({}, {})", pos, state);
        return new ItemExtractorEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        Level level,
        BlockState state,
        BlockEntityType<T> type
    ) {
        LOGGER.info("getTicker({}, {}, {})", level, state, type);
        if (level.isClientSide) {
            return null;
        } else {
            return (lvl, pos, st, blockEntity) -> {
                if (blockEntity instanceof ItemExtractorEntity be) {
                    be.handleTick(lvl, pos);
                }
            };
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
        for (var property : CONNECTED.values()) {
            builder.add(property);
        }
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
                var adjState = level.getBlockState(adjPos);
                if (adjState.is(PIPE.get()) || adjState.is(EXTRACTOR.get()) || adjState.is(DEPOSITOR.get())) {
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
        var state = defaultBlockState();
        state = state.setValue(BlockStateProperties.FACING, facing);
        state = setConnectionStates(context.getLevel(), context.getClickedPos(), state);
        return state;
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
