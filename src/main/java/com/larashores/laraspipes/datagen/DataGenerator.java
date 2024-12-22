package com.larashores.laraspipes.datagen;
import com.mojang.logging.LogUtils;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;


/**
 * Datagen class that takes a list of {@link DataProvider}s and generates all of their data.
 */
public class DataGenerator {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    private final Iterable<DataProvider> providers;

    /**
     * Creates the {@link DataGenerator}.
     *
     * @param providers List of providers to generate data with.
     */
    public DataGenerator(Iterable<DataProvider> providers) {
        this.providers = providers;
    }

    /**
     * Generates data from all the specified {@link DataGenerator} classes.
     *
     * @param event Event that triggers datagen.
     */
    public void generate(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();

        generator.addProvider(
            event.includeClient(),
            new BlockStateCompositeProvider(providers, packOutput, event.getExistingFileHelper())
        );
        generator.addProvider(
            event.includeClient(),
            new ItemModelCompositeProvider(providers, packOutput, event.getExistingFileHelper())
        );
        generator.addProvider(
            event.includeClient(),
            new LanguageCompositeProvider(providers, packOutput, "en_us")
        );
        generator.addProvider(
            event.includeClient(),
            new RecipeCompositeProvider(providers, packOutput)
        );
        generator.addProvider(
            event.includeServer(),
            new LootTableProvider(
                packOutput,
                Collections.emptySet(),
                List.of(
                    new LootTableProvider.SubProviderEntry(
                        new LootTableCompositeProvider(providers),
                        LootContextParamSets.BLOCK
                    )
                )
            )
        );
    }
}
