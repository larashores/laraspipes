package com.larashores.laraspipes.itemextractor;

import com.larashores.laraspipes.network.PipeNetworkDirectedBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;


public class ItemExtractorBlock extends PipeNetworkDirectedBlock implements EntityBlock {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemExtractorBlock() {
        super(
            BlockBehaviour.Properties.of()
            .noOcclusion()
            .strength(1.5F)
            .sound(SoundType.METAL)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemExtractorEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        Level level,
        BlockState state,
        BlockEntityType<T> type
    ) {
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
}
