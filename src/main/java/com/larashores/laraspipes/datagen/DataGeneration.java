package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Main;
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
        generator.addProvider(
            event.includeClient(),
            new ItemModelsProvider(packOutput, event.getExistingFileHelper())
        );
        generator.addProvider(
            event.includeClient(),
            new LanguagesProvider(packOutput, "en_us")
        );
    }
}
