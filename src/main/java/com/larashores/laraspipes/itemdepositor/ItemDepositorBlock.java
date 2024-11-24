package com.larashores.laraspipes.itemdepositor;

import com.larashores.laraspipes.utils.Utils;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.Map;

import static com.larashores.laraspipes.Registration.*;

public class ItemDepositorBlock extends Block implements EntityBlock {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Map<Direction, BooleanProperty> CONNECTED = Map.of(
        Direction.SOUTH, BooleanProperty.create("south"),
        Direction.EAST, BooleanProperty.create("east"),
        Direction.WEST, BooleanProperty.create("west"),
        Direction.NORTH, BooleanProperty.create("north"),
        Direction.UP, BooleanProperty.create("up"),
        Direction.DOWN, BooleanProperty.create("down")
    );

    public ItemDepositorBlock() {
        super(
            BlockBehaviour.Properties.of()
            .noOcclusion()
            .strength(3.5F)
            .sound(SoundType.METAL)
        );
        LOGGER.info("ItemDepositorBlock()");
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        LOGGER.info("newBlockEntity({}, {})", pos, state);
        return new ItemDepositorEntity(pos, state);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult trace
    ) {
        LOGGER.info("use({}, {}, {}, {}, {}, {})", state, level, pos, player, hand, trace);
        if (!level.isClientSide) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity != null) {
                var menuProvider = new ItemDepositorMenuProvider(pos);
                NetworkHooks.openScreen((ServerPlayer) player, menuProvider, entity.getBlockPos());
            }
        }
        return InteractionResult.PASS;
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
        state = state.setValue(BlockStateProperties.FACING, context.getNearestLookingDirection());
        state = setConnectionStates(context.getLevel(), context.getClickedPos(), state);
        return state;
    }

    public static BlockState setConnectionStates(Level level, BlockPos pos, BlockState state) {
        for (var direction: Direction.values()) {
            var property = CONNECTED.get(direction);
            var adjacentPos = pos.relative(direction);
            var adjacentState = level.getBlockState(adjacentPos);
            var connected = direction != state.getValue(BlockStateProperties.FACING) && (
                adjacentState.is(PIPE.get())
                || adjacentState.is(DEPOSITOR.get())
                || adjacentState.is(EXTRACTOR.get())
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
