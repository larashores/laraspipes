package com.larashores.laraspipes.itempipe;

import com.larashores.laraspipes.network.PipeNetworkBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;


public class ItemPipeBlock extends PipeNetworkBlock<ItemPipeEntity> implements EntityBlock {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemPipeBlock() {
        super(
            BlockBehaviour.Properties.of()
            .noOcclusion()
            .strength(0.5F)
            .mapColor(MapColor.STONE),
            ItemPipeEntity::new
        );
    }
}
