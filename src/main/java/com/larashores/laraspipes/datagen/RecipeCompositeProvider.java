package com.larashores.laraspipes.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;


/**
 * Provider that allows defining recipes in multiple {@link DataProvider}s instead of a single monolithic provider. Each
 * {@link DataProvider} should implement the {@link DataProvider#register(Consumer)} method to register its component
 * recipes in the composite provider.
 */
public class RecipeCompositeProvider extends RecipeProvider {
    private final Iterable<DataProvider> providers;

    /**
     * Creates the composite provider.
     *
     * @param providers Component providers to register with the composite provider.
     * @param packOutput Used by the provider superclass.
     */
    public RecipeCompositeProvider(Iterable<DataProvider> providers, PackOutput packOutput) {
        super(packOutput);
        this.providers = providers;
    }

    /**
     * Builds the recipes defined by the component providers.
     *
     * @param consumer Consumer to register component recipes with.
     */
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        for (var provider: providers) {
            provider.register(consumer);
        }
    }
}
