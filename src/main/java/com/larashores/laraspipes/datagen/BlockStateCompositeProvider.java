package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Main;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * Provider that allows defining block states in multiple {@link DataProvider}s instead of a single monolithic
 * provider. Each {@link DataProvider} should implement the {@link DataProvider#register(ItemModelProvider)}
 * method to register its block state in the composite provider.
 */
public class BlockStateCompositeProvider extends BlockStateProvider {
    private final Iterable<DataProvider> providers;

    /**
     * Creates the composite provider.
     *
     * @param providers The component providers.
     * @param output Output to register item models in.
     * @param helper ??
     */
    public BlockStateCompositeProvider(Iterable<DataProvider> providers, PackOutput output, ExistingFileHelper helper) {
        super(output, Main.MOD_ID, helper);
        this.providers = providers;
    }

    @Override
    protected void registerStatesAndModels() {
        for (var provider: providers) {
            provider.register(this);
        }
    }
}
