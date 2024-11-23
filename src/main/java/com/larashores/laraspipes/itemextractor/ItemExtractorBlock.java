package com.larashores.laraspipes.itemextractor;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
        var state = defaultBlockState();
        state.setValue(BlockStateProperties.FACING, context.getNearestLookingDirection());
        for (var property : CONNECTED.values()) {
            state.setValue(property, true);
        }
        return state;
    }
}
