package com.larashores.laraspipes.itemdepositor;

import com.larashores.laraspipes.itempipe.ItemPipeBlock;
import com.larashores.laraspipes.network.PipeNetworkDirectedBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.slf4j.Logger;

import javax.annotation.Nonnull;


/**
 * Block that can connect to accept items transferred through {@link ItemPipeBlock}s. See {@link ItemDepositorEntity}
 * for more details on its behavior.
 */
public class ItemDepositorBlock extends PipeNetworkDirectedBlock<ItemDepositorEntity> implements EntityBlock {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Creates the {@link ItemDepositorBlock}.
     */
    public ItemDepositorBlock() {
        super(
            BlockBehaviour.Properties.of()
            .noOcclusion()
            .strength(1.5F)
            .sound(SoundType.METAL),
            ItemDepositorEntity::new,
            "item"
        );
    }

    /**
     * Called when the user clicks on the entity. Opens a {@link ItemDepositorMenu} if on the client side.
     *
     * @param state The state of the block when clicked on.
     * @param level The level the block belongs to.
     * @param pos The position of the block.
     * @param player The player who clicked on the block.
     * @param hand The hand the player clicked on the block with.
     * @param trace ??
     *
     * @return Always returns PASS.
     */
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
        if (!level.isClientSide) {
            var entity = level.getBlockEntity(pos);
            if (entity instanceof ItemDepositorEntity) {
                var menuProvider = new ItemDepositorMenuProvider(pos);
                NetworkHooks.openScreen((ServerPlayer) player, menuProvider, entity.getBlockPos());
            }
        }
        return InteractionResult.PASS;
    }
}
