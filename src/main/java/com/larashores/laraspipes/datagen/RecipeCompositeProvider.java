package com.larashores.laraspipes.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class RecipeCompositeProvider extends RecipeProvider {
    private final Iterable<DataProvider> providers;

    public RecipeCompositeProvider(
        Iterable<DataProvider> providers,
        PackOutput packOutput
    ) {
        super(packOutput);
        this.providers = providers;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        for (var provider: providers) {
            provider.register(consumer);
        }
    }
}
