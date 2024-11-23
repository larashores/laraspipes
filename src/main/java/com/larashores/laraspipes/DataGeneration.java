package com.larashores.laraspipes;

import com.larashores.laraspipes.datagen.BlockStatesProvider;
import com.mojang.logging.LogUtils;
import net.minecraftforge.data.event.GatherDataEvent;
import org.slf4j.Logger;


public class DataGeneration {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void generate(GatherDataEvent event) {
        LOGGER.info("Generating data for {}", Main.MOD_ID);
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();

        generator.addProvider(
            event.includeClient(),
            new BlockStatesProvider(packOutput, event.getExistingFileHelper())
        );
    }
}
