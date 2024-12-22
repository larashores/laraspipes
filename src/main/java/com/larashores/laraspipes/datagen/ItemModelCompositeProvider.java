package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Main;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;


/**
 * Provider that allows defining item models in multiple {@link DataProvider}s instead of a single monolithic
 * provider. Each {@link DataProvider} should implement the {@link DataProvider#register(ItemModelProvider)}
 * method to register its item models in the composite provider.
 */
public class ItemModelCompositeProvider extends ItemModelProvider {
    private final Iterable<DataProvider> providers;

    /**
     * Creates the composite provider.
     *
     * @param providers The component providers.
     * @param output Output to register item models in.
     * @param helper ??
     */
    public ItemModelCompositeProvider(Iterable<DataProvider> providers, PackOutput output, ExistingFileHelper helper) {
        super(output, Main.MOD_ID, helper);
        this.providers = providers;
    }

    /**
     * Registers item models of component providers.
     */
    @Override
    protected void registerModels() {
        for (var provider: providers) {
            provider.register(this);
        }
    }
}
