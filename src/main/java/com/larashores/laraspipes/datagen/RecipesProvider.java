package com.larashores.laraspipes.datagen;

import com.larashores.laraspipes.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class RecipesProvider extends RecipeProvider {

    public RecipesProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Registration.EXTRACTOR_ITEM.get())
            .pattern("i i")
            .pattern("iri")
            .pattern(" h ")
            .define('i', Items.IRON_INGOT)
            .define('h', Items.HOPPER)
            .define('r', Items.REDSTONE_BLOCK)
            .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
            .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Registration.DEPOSITOR_ITEM.get())
            .pattern(" h ")
            .pattern("iri")
            .pattern("i i")
            .define('i', Items.IRON_INGOT)
            .define('h', Items.HOPPER)
            .define('r', Items.REDSTONE_BLOCK)
            .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
            .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Registration.PIPE_ITEM.get(), 4)
            .pattern("iri")
            .define('i', Items.IRON_INGOT)
            .define('r', Items.REDSTONE)
            .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
            .save(consumer);

    }
}
