package com.larashores.laraspipes.itempipe;

import com.larashores.laraspipes.network.PipeNetworkBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;


public class ItemPipeBlock extends PipeNetworkBlock implements EntityBlock {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemPipeBlock() {
        super(
            BlockBehaviour.Properties.of()
            .noOcclusion()
            .strength(0.5F)
            .mapColor(MapColor.STONE)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemPipeEntity(pos, state);
    }
}
