package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.itemdepositor.ItemDepositorModels;
import com.larashores.laraspipes.itemextractor.ItemExtractorModels;
import com.larashores.laraspipes.itempipe.ItemPipeModels;
import com.mojang.logging.LogUtils;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;


public class DataGeneration {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void generate(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();

        var providers = new DataGenerationProvider[]{
            new ItemDepositorModels(),
            new ItemExtractorModels(),
            new ItemPipeModels(),
        };

        generator.addProvider(
            event.includeClient(),
            new BlockStatesProvider(providers, packOutput, event.getExistingFileHelper())
        );
        generator.addProvider(
            event.includeClient(),
            new ItemModelsProvider(providers, packOutput, event.getExistingFileHelper())
        );
        generator.addProvider(
            event.includeClient(),
            new LanguagesProvider(providers, packOutput, "en_us")
        );
        generator.addProvider(
            event.includeClient(),
            new RecipesProvider(packOutput)
        );
        generator.addProvider(
            event.includeServer(),
            new LootTableProvider(
                packOutput,
                Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(LootTables::new, LootContextParamSets.BLOCK))
            )
        );
    }
}
