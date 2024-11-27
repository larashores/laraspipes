package com.larashores.laraspipes.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class RecipesProvider extends RecipeProvider {
    private final DataGenerationProvider[] providers;

    public RecipesProvider(
        DataGenerationProvider[] providers,
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
