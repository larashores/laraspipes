package com.larashores.laraspipes.itempipe;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

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
        var level = context.getLevel();
        var pos = context.getClickedPos();
        for (var direction: Direction.values()) {
            var property = CONNECTED.get(direction);
            var adjacentPos = pos.relative(direction);
            var adjacentState = level.getBlockState(adjacentPos);
            var connected = (
                adjacentState.is(PIPE.get())
                || adjacentState.is(DEPOSITOR.get())
                || adjacentState.is(EXTRACTOR.get())
            );
            state = state.setValue(property, connected);
        }
        return state;
    }
}
