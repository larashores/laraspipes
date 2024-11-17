package com.larashores.laraspipes.itempipe;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;


public class ItemPipe extends Block {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemPipe() {
        super(
            BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
        );
        LOGGER.info("ItemPipe()");
    }
}
