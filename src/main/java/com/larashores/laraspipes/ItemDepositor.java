package com.larashores.laraspipes;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class ItemDepositor extends Block implements EntityBlock {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemDepositor() {
        super(
            BlockBehaviour.Properties.of()
            .strength(3.5F)
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL)
        );
        LOGGER.info("ItemDepositor()");
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        LOGGER.info("newBlockEntity({}, {})", pos, state);
        return new ItemDepositorEntity(pos, state);
    }

    @Override
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
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ItemDepositorEntity) {
                var menuProvider = new ItemDepositorMenuProvider(pos);
                NetworkHooks.openScreen((ServerPlayer) player, menuProvider, be.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.PASS;
    }

}
